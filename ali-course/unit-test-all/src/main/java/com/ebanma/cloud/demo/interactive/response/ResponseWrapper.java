package com.ebanma.cloud.demo.interactive.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper implements Serializable {

    private static final long serialVersionUID = -2324296022039148069L;
    private boolean success;
    private String message;

    public ResponseWrapper(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseWrapper() {
    }

    public static ResponseWrapper success() {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.success = true;
        responseWrapper.message = "OK";
        return responseWrapper;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}