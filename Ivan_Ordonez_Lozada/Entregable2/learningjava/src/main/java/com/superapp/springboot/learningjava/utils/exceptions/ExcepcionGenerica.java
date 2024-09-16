package com.superapp.springboot.learningjava.utils.exceptions;

public class ExcepcionGenerica extends RuntimeException {

    public ExcepcionGenerica(String mensajeError) {
        super(mensajeError);
    }

}
