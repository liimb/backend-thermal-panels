package com.thermal.thermalback.modules.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.thermal.thermalback.common.config.JwtConfig;
import com.thermal.thermalback.common.config.KeyConfig;
import com.thermal.thermalback.common.exception.AuthErrorCodeEnum;
import com.thermal.thermalback.common.exception.AuthException;
import com.thermal.thermalback.modules.account.entity.Account;
import com.thermal.thermalback.modules.account.entity.Role;
import com.thermal.thermalback.modules.account.repository.AccountRepository;
import com.thermal.thermalback.modules.auth.api.controller.AuthRequest;
import com.thermal.thermalback.modules.auth.api.controller.AuthRequestBySms;
import com.thermal.thermalback.modules.auth.api.controller.JwtDto;
import com.thermal.thermalback.modules.auth.entity.Jwt;
import com.thermal.thermalback.modules.auth.entity.SmsCode;
import com.thermal.thermalback.modules.auth.repository.JwtRepository;
import com.thermal.thermalback.modules.auth.repository.SmsCodeRepository;
import com.thermal.thermalback.util.UtilTimeService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final SmsCodeRepository smsCodeRepository;
    private final JwtRepository jwtRepository;

    private final KeyConfig keyConfig;
    private final JwtConfig jwtConfig;

    private final SmsService smsService;

    private static final int secondsLifeTimeSmsCode = 180;

    public void askSmsCode(AuthRequest request) throws AuthException {

        Optional<Account> optAccount = accountRepository.findByPhone(request.phone());//.orElseThrow(() -> new AuthException(AuthErrorCodeEnum.NOT_FOUND_ACCOUNT_BY_PHONE));

        Account account;

        if (optAccount.isPresent()) {
            account = optAccount.get();
        } else {
            //если не зарегистрирован
            return;
        }

        smsCodeRepository.deleteByAccountId(account.id());

        String code = generateSmsCode();

        SmsCode smsCode = new SmsCode();
        smsCode.id(UUID.randomUUID());
        smsCode.accountId(account.id());
        smsCode.code(code);
        smsCode.expires(UtilTimeService.getLocalDateNow().plusSeconds(secondsLifeTimeSmsCode));

        smsCodeRepository.saveAndFlush(smsCode);

        smsService.sendSms(account.phone(), code);
    }

    public JwtDto loginBySms(AuthRequestBySms request) throws AuthException {

        Account account = accountRepository.findByPhone(request.phone())
                .orElseThrow(() -> new AuthException(AuthErrorCodeEnum.NOT_FOUND_ACCOUNT_BY_PHONE));

        SmsCode smsCode = smsCodeRepository.findByAccountId(account.id())
                .orElseThrow(() -> new AuthException(AuthErrorCodeEnum.NOT_FOUND_SMS_BY_ACCOUNT));

        boolean isSmsCodeExpired = UtilTimeService.getLocalDateNow().isAfter(smsCode.expires());
        boolean isValidRequestCode = Objects.equals(smsCode.code(), request.code());

        smsCodeRepository.delete(smsCode);

        if (!isSmsCodeExpired && isValidRequestCode) {
            return createJwt(account.id(), account.role());
        } else {
            throw new AuthException(AuthErrorCodeEnum.SMS_IS_EXPIRED);
        }
    }

    private String generateSmsCode() {
        return "0000";
    }

    private JwtDto createJwt(UUID accountId, Role role) throws AuthException {
        try {
            KeyFactory kf = KeyFactory.getInstance("EC");
            byte[] publicBytes = getKeyFromString(keyConfig.getPathToPublicKey());
            byte[] privateBytes = getKeyFromString(keyConfig.getPathToPrivateKey());

            ECPublicKey publicKey = getPublicKey(kf, publicBytes);
            ECPrivateKey privateKey = getPrivateKey(kf, privateBytes);
            Algorithm algorithm = Algorithm.ECDSA256(publicKey, privateKey);

            LocalDateTime nowUtc = UtilTimeService.getLocalDateNow();
            LocalDateTime expDateTimeUtc = nowUtc.plusSeconds(jwtConfig.getJwtExpiredTime());
            LocalDateTime expDateTimeRefreshUtc = nowUtc.plusMinutes(jwtConfig.getRefreshExpiredTime());

            Instant nowInstant = UtilTimeService.toInstant(nowUtc);
            Instant expDateTimeInstant = UtilTimeService.toInstant(expDateTimeUtc);

            String jwtIdToken = UUID.randomUUID().toString();
            UUID refreshToken = UUID.randomUUID();

            String jwtToken = JWT.create()
                    .withIssuer("thermal-panels")
                    .withSubject(accountId.toString())
                    .withAudience("back")
                    .withExpiresAt(expDateTimeInstant)
                    .withNotBefore(nowInstant)
                    .withIssuedAt(nowInstant)
                    .withJWTId(jwtIdToken)
                    .withClaim("role", role.name())
                    .sign(algorithm);

            Jwt jwtAccount = new Jwt()
                    .id(UUID.randomUUID())
                    .accountId(accountId)
                    .jwtId(jwtIdToken)
                    .jwt(jwtToken)
                    .exp(expDateTimeUtc)
                    .nbf(nowUtc)
                    .iat(nowUtc)
                    .refreshToken(refreshToken)
                    .refreshExp(expDateTimeRefreshUtc);

            jwtRepository.save(jwtAccount);

            return new JwtDto()
                    .jwt(jwtToken)
                    .refreshToken(refreshToken.toString())
                    .maxAge(jwtConfig.getRefreshExpiredTime() * 60);

        } catch (JWTCreationException | IOException | GeneralSecurityException exception) {
            throw new AuthException(AuthErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    private ECPrivateKey getPrivateKey(KeyFactory kf, byte[] encoded) throws InvalidKeySpecException {
        return (ECPrivateKey) kf.generatePrivate(new PKCS8EncodedKeySpec(encoded));
    }

    private ECPublicKey getPublicKey(KeyFactory kf, byte[] encoded) throws InvalidKeySpecException {
        return (ECPublicKey) kf.generatePrivate(new X509EncodedKeySpec(encoded));
    }

    private byte[] getKeyFromString(String path) throws IOException {
        List<String> file = Files.readAllLines(Paths.get(path));
        String key = String.join("\n", file.subList(1, file.size() - 1));
        return Base64.decodeBase64(key);
    }
}
