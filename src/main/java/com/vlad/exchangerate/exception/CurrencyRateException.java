package com.vlad.exchangerate.exception;

public class CurrencyRateException extends RuntimeException {

    private final ErrorCode errorCode;

    public CurrencyRateException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode.getCode();
    }
}
