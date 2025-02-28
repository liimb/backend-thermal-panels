package com.thermal.thermalback.modules.auth.service;

import com.thermal.thermalback.common.exception.AuthErrorCodeEnum;
import com.thermal.thermalback.common.exception.AuthException;
import com.thermal.thermalback.external.sms.Smsc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@Component
public class SmsService {

    @Value("${sms.service.login}")
    String SMSC_LOGIN;

    @Value("${sms.service.password}")
    String SMSC_PASSWORD = "";

    private static final SecureRandom random = new SecureRandom();

    public void sendSms(String phone, String smsCode) throws AuthException {
//        Smsc smsc = new Smsc(SMSC_LOGIN, SMSC_PASSWORD);
//        String[] response = smsc.send_sms(phone, "Ваш код подтверждения: " + smsCode, 0, "", "", 0, "Thermal Panel", "");
//
//        if(!(response.length > 1)){
//            throw new AuthException(AuthErrorCodeEnum.SMS_DONT_SEND);
//        }
    }

    public String generateSmsCode() {
        int code = 100_000 + random.nextInt(900_000);
        //return String.valueOf(code);
        return "000000";
    }
}
