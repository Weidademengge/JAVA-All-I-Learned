package com.ebanma.cloud.demo.exception;

public class IntegrateException extends  RuntimeException {

    public IntegrateException(String message, Object... params) {
        super(String.format(message, params));
    }
}