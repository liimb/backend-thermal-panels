package com.thermal.thermalback.modules.register.api.controller;

import com.thermal.thermalback.common.exception.AuthException;
import com.thermal.thermalback.modules.register.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/register")
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/create")
    public RegisterResponse createAccount(RegisterRequest request) throws AuthException {
        return registerService.createAccount(request);
    }

}
