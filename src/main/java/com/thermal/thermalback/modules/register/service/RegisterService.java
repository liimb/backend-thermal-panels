package com.thermal.thermalback.modules.register.service;

import com.thermal.thermalback.common.exception.auth.AuthErrorCodeEnum;
import com.thermal.thermalback.common.exception.auth.AuthException;
import com.thermal.thermalback.modules.account.entity.Account;
import com.thermal.thermalback.modules.account.entity.Role;
import com.thermal.thermalback.modules.account.repository.AccountRepository;
import com.thermal.thermalback.modules.register.api.controller.AccountDto;
import com.thermal.thermalback.modules.register.api.controller.RegisterRequest;
import com.thermal.thermalback.modules.register.api.controller.RegisterResponse;
import com.thermal.thermalback.modules.temporary.account.entity.TempAccount;
import com.thermal.thermalback.modules.temporary.account.repository.TempAccountRepository;
import com.thermal.thermalback.util.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterService {
    private final JwtHelper jwtHelper;

    private final TempAccountRepository tempAccountRepository;
    private final AccountRepository accountRepository;

    public RegisterResponse createAccount(RegisterRequest request) throws AuthException {

        Optional<Account> acc = accountRepository.findByPhone(request.accountRegisterRequest().phone());

        if(acc.isPresent()) {
            throw new AuthException(AuthErrorCodeEnum.ACCOUNT_ALREADY_EXISTS);
        }

        TempAccount tempAccountUUID = tempAccountRepository.findByRegisterUUID(request.registerUUID()).orElseThrow(() -> new AuthException(AuthErrorCodeEnum.REGISTER_ERROR));
        TempAccount tempAccountPhone = tempAccountRepository.findByPhone(request.accountRegisterRequest().phone()).orElseThrow(() -> new AuthException(AuthErrorCodeEnum.REGISTER_ERROR));

        if (Objects.equals(tempAccountUUID, tempAccountPhone)){
            Account account = new Account();
            account.id(UUID.randomUUID());
            account.phone(request.accountRegisterRequest().phone());
            account.email(request.accountRegisterRequest().email());
            account.firstName(request.accountRegisterRequest().firstName());
            account.lastName(request.accountRegisterRequest().lastName());
            account.patronymic(request.accountRegisterRequest().patronymic());
            account.role(Role.USER);

            accountRepository.saveAndFlush(account);

            AccountDto accountDto = new AccountDto();
            accountDto.id(account.id());
            accountDto.email(account.email());
            accountDto.firstName(account.firstName());
            accountDto.lastName(account.lastName());
            accountDto.patronymic(account.patronymic());
            accountDto.phone(account.phone());

            tempAccountRepository.delete(tempAccountUUID);

            return new RegisterResponse(accountDto, jwtHelper.createJwt(account.id(), account.role()));
        } else {
            throw new AuthException(AuthErrorCodeEnum.REGISTER_ERROR);
        }
    }
}
