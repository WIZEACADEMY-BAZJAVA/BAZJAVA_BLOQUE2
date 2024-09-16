package com.superapp.springboot.learningjava.utils.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.util.List;

import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
public class ExcepcionUnica extends RuntimeException {

    private int codigoError;
    private List<String> detalles;
    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public ExcepcionUnica() {
    }

    public ExcepcionUnica(int codigoError) {
        this.codigoError = codigoError;
    }

    public ExcepcionUnica(int codigoError, @Nullable List<String> detalles) {
        this.codigoError = codigoError;
        this.detalles = detalles;
    }

    public ExcepcionUnica(int codigoError, @NotNull HttpStatus httpStatus) {
        this.codigoError = codigoError;
        if (httpStatus != null) {
            this.httpStatus = httpStatus;
        }
    }

    public ExcepcionUnica(int codigoError, @Nullable List<String> detalles, @NotNull HttpStatus httpStatus) {
        this.codigoError = codigoError;
        this.detalles = detalles;
        if (httpStatus != null) {
            this.httpStatus = httpStatus;
        }
    }

    public int getCodigoError() {
        return codigoError;
    }

    public List<String> getDetalles() {
        return detalles;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

