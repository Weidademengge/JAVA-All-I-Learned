package com.duym.usertestall.exception;


import com.duym.usertestall.domain.common.ErrorCode;

public class BusinessException extends RuntimeException {

    private final String code;

    /**
     * 根据枚举来构建业务异常
     *
     * @param errorCode 错误代码
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getDesc());
        this.code = errorCode.getCode();
    }

    /**
     * 根据自定义消息来构建业务异常
     *
     * @param errorCode 错误代码
     * @param message   消息
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    /**
     * 根据异常来构建业务异常
     *
     * @param errorCode 错误代码
     * @param cause     导致
     */
    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.code = errorCode.getCode();
    }

    public String getCode() {
        return code;
    }
}
