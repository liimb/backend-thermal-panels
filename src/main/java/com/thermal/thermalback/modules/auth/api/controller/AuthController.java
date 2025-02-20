package com.thermal.thermalback.modules.auth.api.controller;

import com.thermal.thermalback.common.exception.AuthException;
import com.thermal.thermalback.modules.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login/ask-sms-code")
    public void askSmsCode(@RequestBody AuthRequest request) throws AuthException {
        authService.askSmsCode(request);
    }

    @PostMapping("/login/by-sms")
    public void loginBySms(@RequestBody AuthRequestBySms request) throws AuthException {
        authService.loginBySms(request);
    }

    //рефреш токены, логаут
}
