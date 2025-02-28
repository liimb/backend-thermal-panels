package com.thermal.thermalback.modules.auth.api.controller;

import com.thermal.thermalback.common.exception.AuthException;
import com.thermal.thermalback.modules.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public AuthResponse loginBySms(@RequestBody AuthRequestBySms request) throws AuthException {
        return authService.loginBySms(request);
    }

    @PostMapping("/refresh")
    public JwtDto refresh(@RequestBody RefreshJwtRequest request) throws AuthException {
        return authService.refresh(request);
    }

    @GetMapping("/check")
    public String check() {
        return "OK";
    }
}
