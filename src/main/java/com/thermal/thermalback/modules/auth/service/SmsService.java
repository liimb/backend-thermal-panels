package com.thermal.thermalback.modules.auth.service;

import com.thermal.thermalback.common.exception.AuthErrorCodeEnum;
import com.thermal.thermalback.common.exception.AuthException;
import com.thermal.thermalback.external.sms.Smsc;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    public void sendSms(String phone, String smsCode) throws AuthException {
//        Smsc smsc = new Smsc();
//        String[] response = smsc.send_sms(phone, "Ваш код подтверждения: " + smsCode, 1, "", "", 0, "Thermal Panel", "");
//
//        if(!(response.length > 1)){
//            throw new AuthException(AuthErrorCodeEnum.SMS_DONT_SEND);
//        }
    }

    public String generateSmsCode() {
        return "0000";
    }
}
