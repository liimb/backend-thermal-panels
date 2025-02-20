package com.thermal.thermalback.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    protected ResponseEntity<ErrorResponse> handlerException(AuthException ex) {
        ex.printStackTrace();

        ErrorResponse response = new ErrorResponse(
                ex.description(),
                ex.errorCode(),
                ex.status()
        );

        return ResponseEntity.status(response.status()).body(response);
    }

}
