package com.thermal.thermalback.modules.account.api.controller;

import com.thermal.thermalback.common.exception.global.GlobalException;
import com.thermal.thermalback.modules.account.service.AccountService;
import com.thermal.thermalback.modules.register.api.controller.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/get-by-id/{id}")
    public AccountDto getAccountById(@PathVariable UUID id) throws GlobalException {
        return accountService.getAccountById(id);
    }
}
