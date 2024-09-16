package com.superapp.springboot.learningjava.dto;

public class ErrorDTO {

    String errorCode;

    String message;

    public ErrorDTO() {
        super();
    }

    public ErrorDTO(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
