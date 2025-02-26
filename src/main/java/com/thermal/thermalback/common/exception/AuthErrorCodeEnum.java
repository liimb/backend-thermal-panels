package com.thermal.thermalback.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public enum AuthErrorCodeEnum {

    INTERNAL_SERVER_ERROR("001", "Внутренняя ошибка сервиса аутентификации", HttpStatus.INTERNAL_SERVER_ERROR),
    WRONG_OBJECT_PARAMS("002", "Неверные параметры объекта", HttpStatus.BAD_REQUEST),
    NOT_FOUND_ACCOUNT_BY_PHONE("003", "Пользователь с таким телефоном не найден", HttpStatus.NOT_FOUND),
    NOT_FOUND_SMS_BY_ACCOUNT("004", "СМС код для данного телефона не найден", HttpStatus.NOT_FOUND),
    SMS_IS_EXPIRED("005", "СМС код истек", HttpStatus.BAD_REQUEST),
    SMS_DONT_SEND("006", "Ошибка отправки СМС", HttpStatus.INTERNAL_SERVER_ERROR),
    REGISTER_ERROR("007", "Ошибка регистрации. Подтвердите номер заново", HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_EXISTS("008", "Аккаунт с таким номером уже существует", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String description;
    private final HttpStatus status;
}
