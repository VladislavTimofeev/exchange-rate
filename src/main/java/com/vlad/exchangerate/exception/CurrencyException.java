package com.vlad.exchangerate.exception;

public class CurrencyException extends RuntimeException {

    private final ErrorCode errorCode;

    public CurrencyException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode.getCode();
    }
}
