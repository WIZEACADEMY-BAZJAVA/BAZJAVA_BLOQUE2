package com.superapp.springboot.learningjava.utils.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExpiredHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handlerApiExpired(ExpiredJwtException ex) {
        String mensaje = "Token invalido";
        return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
    }
}
