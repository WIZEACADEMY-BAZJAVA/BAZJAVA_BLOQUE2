package com.superapp.springboot.learningjava.utils.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExcepcionUnicaHandler {

    @ExceptionHandler(ExcepcionUnica.class)
    public ResponseEntity<String> handlerApiErrorException(ExcepcionUnica ex) {
        String message = "Ocurrio un error " + ex.getCodigoError()
                + ", con el estatus " + ex.getHttpStatus();
        String detail = ex.getDetalles() != null && !ex.getDetalles().isEmpty()
                ? " mensaje " + ex.getDetalles().get(0) : "";
        return new ResponseEntity<>(message + detail, ex.getHttpStatus());
    }
}
