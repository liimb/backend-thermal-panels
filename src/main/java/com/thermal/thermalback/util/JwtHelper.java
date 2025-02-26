package com.thermal.thermalback.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.thermal.thermalback.common.config.JwtConfig;
import com.thermal.thermalback.common.config.KeyConfig;
import com.thermal.thermalback.common.exception.AuthErrorCodeEnum;
import com.thermal.thermalback.common.exception.AuthException;
import com.thermal.thermalback.modules.account.entity.Role;
import com.thermal.thermalback.modules.auth.api.controller.JwtDto;
import com.thermal.thermalback.modules.auth.entity.Jwt;
import com.thermal.thermalback.modules.auth.repository.JwtRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import java.util.UUID;

@Component
public class JwtHelper {

    private final JwtRepository jwtRepository;
    private final KeyConfig keyConfig;
    private final JwtConfig jwtConfig;

    @Autowired
    public JwtHelper(JwtRepository jwtRepository, KeyConfig keyConfig, JwtConfig jwtConfig) {
        this.jwtRepository = jwtRepository;
        this.keyConfig = keyConfig;
        this.jwtConfig = jwtConfig;
    }

    public JwtDto createJwt(UUID accountId, Role role) throws AuthException {
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
                    .refreshToken(refreshToken)
                    .maxAge(jwtConfig.getRefreshExpiredTime() * 60);

        } catch (JWTCreationException | IOException | GeneralSecurityException exception) {
            throw new AuthException(AuthErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    private ECPrivateKey getPrivateKey(KeyFactory kf, byte[] encoded) throws InvalidKeySpecException {
        return (ECPrivateKey) kf.generatePrivate(new PKCS8EncodedKeySpec(encoded));
    }

    private ECPublicKey getPublicKey(KeyFactory kf, byte[] encoded) throws InvalidKeySpecException {
        return (ECPublicKey) kf.generatePublic(new X509EncodedKeySpec(encoded));
    }

    private byte[] getKeyFromString(String path) throws IOException {
        List<String> file = Files.readAllLines(Paths.get(path));
        String key = String.join("\n", file.subList(1, file.size() - 1));
        return Base64.decodeBase64(key);
    }
}
