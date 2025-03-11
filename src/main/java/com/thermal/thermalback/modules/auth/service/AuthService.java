package com.thermal.thermalback.modules.auth.service;

import com.thermal.thermalback.common.exception.auth.AuthErrorCodeEnum;
import com.thermal.thermalback.common.exception.auth.AuthException;
import com.thermal.thermalback.modules.account.entity.Account;
import com.thermal.thermalback.modules.account.repository.AccountRepository;
import com.thermal.thermalback.modules.auth.api.controller.*;
import com.thermal.thermalback.modules.auth.entity.Jwt;
import com.thermal.thermalback.modules.auth.entity.SmsCode;
import com.thermal.thermalback.modules.auth.repository.JwtRepository;
import com.thermal.thermalback.modules.auth.repository.SmsCodeRepository;
import com.thermal.thermalback.modules.temporary.account.entity.TempAccount;
import com.thermal.thermalback.modules.temporary.account.repository.TempAccountRepository;
import com.thermal.thermalback.util.JwtHelper;
import com.thermal.thermalback.util.UtilTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final TempAccountRepository tempAccountRepository;
    private final SmsCodeRepository smsCodeRepository;
    private final JwtHelper jwtHelper;

    private final SmsService smsService;

    private static final int secondsLifeTimeSmsCode = 180;
    private final JwtRepository jwtRepository;

    public JwtDto refresh(RefreshJwtRequest request) throws AuthException {

        Jwt jwt = jwtRepository.findFirstByJwtAndRefreshToken(request.jwt(), request.refreshToken())
                .orElseThrow(() -> new AuthException(AuthErrorCodeEnum.INTERNAL_SERVER_ERROR));

        if(jwt.refreshExp().isBefore(UtilTimeService.getLocalDateNow())){
            throw new AuthException(AuthErrorCodeEnum.INTERNAL_SERVER_ERROR);
        }

        jwtRepository.delete(jwt);

        Account account = accountRepository.findById(jwt.accountId())
                .orElseThrow(() -> new AuthException(AuthErrorCodeEnum.INTERNAL_SERVER_ERROR));

        return jwtHelper.createJwt(account.id(), account.role());
    }

    public void askSmsCode(AuthRequest request) throws AuthException {
        smsCodeRepository.deleteAllByPhone(request.phone());

        String code = smsService.generateSmsCode();

        SmsCode smsCode = new SmsCode();
        smsCode.id(UUID.randomUUID());
        smsCode.phone(request.phone());
        smsCode.code(code);
        smsCode.expires(UtilTimeService.getLocalDateNow().plusSeconds(secondsLifeTimeSmsCode));

        smsCodeRepository.saveAndFlush(smsCode);

        smsService.sendSms(request.phone(), code);
    }

    public AuthResponse loginBySms(AuthRequestBySms request) throws AuthException {

        Optional<Account> optAccount = accountRepository.findByPhone(request.phone());

        if (optAccount.isPresent()) { //если уже есть аккаунт, то входим

            Account account = optAccount.get();
            SmsCode smsCode = checkSmsCodeByPhone(request, account.phone());

            smsCodeRepository.delete(smsCode);

            return new AuthResponse(true, null, jwtHelper.createJwt(account.id(), account.role()));

        } else { //если аккаунта нет, то во временный аккаунт с этим номером записываем UUID для продолжения регистрации
            Optional<TempAccount> optTempAccount = tempAccountRepository.findByPhone(request.phone());

            SmsCode smsCode = checkSmsCodeByPhone(request, request.phone());

            TempAccount tempAccount;
            UUID registerUUID = UUID.randomUUID();

            if (optTempAccount.isPresent()) {
                tempAccount = optTempAccount.get();
                tempAccount.registerUUID(registerUUID);
            } else {
                tempAccount = new TempAccount();
                tempAccount.id(UUID.randomUUID());
                tempAccount.phone(request.phone());
                tempAccount.registerUUID(registerUUID);
            }

            tempAccountRepository.saveAndFlush(tempAccount);
            smsCodeRepository.delete(smsCode);

            return new AuthResponse(false, registerUUID, null);
        }
    }

    private SmsCode checkSmsCodeByPhone(AuthRequestBySms request, String phone) throws AuthException {
        SmsCode smsCode = smsCodeRepository.findByPhone(phone)
                .orElseThrow(() -> new AuthException(AuthErrorCodeEnum.NOT_FOUND_SMS_BY_ACCOUNT));

        boolean isSmsCodeExpired = UtilTimeService.getLocalDateNow().isAfter(smsCode.expires());
        boolean isValidRequestCode = Objects.equals(smsCode.code(), request.code());

        if (isSmsCodeExpired) {
            smsCodeRepository.delete(smsCode);
            throw new AuthException(AuthErrorCodeEnum.SMS_IS_EXPIRED);
        }

        if (!isValidRequestCode) {
            throw new AuthException(AuthErrorCodeEnum.SMS_NOT_VALID);
        }

        return smsCode;
    }
}
