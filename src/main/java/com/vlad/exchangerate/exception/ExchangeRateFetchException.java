package com.vlad.exchangerate.exception;

public class ExchangeRateFetchException extends RuntimeException {

    private final ErrorCode errorCode;

    public ExchangeRateFetchException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode.getCode();
    }
}
