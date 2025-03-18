package com.thermal.thermalback.modules.account.service;

import com.thermal.thermalback.common.exception.global.GlobalErrorCodeEnum;
import com.thermal.thermalback.common.exception.global.GlobalException;
import com.thermal.thermalback.modules.account.entity.Account;
import com.thermal.thermalback.modules.account.repository.AccountRepository;
import com.thermal.thermalback.modules.register.api.controller.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountDto getAccountById(UUID id) throws GlobalException {
        Account account = accountRepository.findById(id).orElseThrow(() -> new GlobalException(GlobalErrorCodeEnum.INTERNAL_SERVER_ERROR));

        AccountDto accountDto = new AccountDto();
        accountDto.id(account.id());
        accountDto.firstName(account.firstName());
        accountDto.lastName(account.lastName());
        accountDto.email(account.email());
        accountDto.phone(account.phone());
        accountDto.patronymic(account.patronymic());

        return accountDto;
    }
}
