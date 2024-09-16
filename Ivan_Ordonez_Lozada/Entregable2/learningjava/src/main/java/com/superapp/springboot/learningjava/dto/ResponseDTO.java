package com.superapp.springboot.learningjava.dto;

import java.util.Optional;

public class ResponseDTO {

    // * status: guarda el resultado del request, posibles valores success o fail.
    private String status;

    // * code: guarda el codigo de la operacion realizada o error.
    private String code;

    // * error: bean que maneja un listado de errores presentados en durante el procesamiento de request.
    private Optional<ErrorDTO> errors;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Optional<ErrorDTO> getErrors() {
        return errors;
    }

    public void setErrors(ErrorDTO errors) {
        this.errors = Optional.of(errors);
    }

}
