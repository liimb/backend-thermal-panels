package com.thermal.thermalback.common.exception;

import com.thermal.thermalback.common.exception.auth.AuthException;
import com.thermal.thermalback.common.exception.material.MaterialException;
import com.thermal.thermalback.common.exception.order.OrderException;
import com.thermal.thermalback.common.exception.work.WorkException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    protected ResponseEntity<ErrorResponse> authHandlerException(AuthException ex) {
        ex.printStackTrace();

        ErrorResponse response = new ErrorResponse(
                ex.description(),
                ex.errorCode(),
                ex.status()
        );

        return ResponseEntity.status(response.status()).body(response);
    }

    @ExceptionHandler(MaterialException.class)
    protected ResponseEntity<ErrorResponse> materialHandlerException(MaterialException ex) {
        ex.printStackTrace();

        ErrorResponse response = new ErrorResponse(
                ex.description(),
                ex.errorCode(),
                ex.status()
        );

        return ResponseEntity.status(response.status()).body(response);
    }

    @ExceptionHandler(WorkException.class)
    protected ResponseEntity<ErrorResponse> workHandlerException(WorkException ex) {
        ex.printStackTrace();

        ErrorResponse response = new ErrorResponse(
                ex.description(),
                ex.errorCode(),
                ex.status()
        );

        return ResponseEntity.status(response.status()).body(response);
    }

    @ExceptionHandler(OrderException.class)
    protected ResponseEntity<ErrorResponse> orderHandlerException(OrderException ex) {
        ex.printStackTrace();

        ErrorResponse response = new ErrorResponse(
                ex.description(),
                ex.errorCode(),
                ex.status()
        );

        return ResponseEntity.status(response.status()).body(response);
    }
}
