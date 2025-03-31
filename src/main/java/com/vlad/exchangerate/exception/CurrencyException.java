package com.vlad.exchangerate.exception;

import lombok.Getter;

@Getter
public class CurrencyException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String type;

    public CurrencyException(ErrorCode errorCode, String type) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.type = type;
    }

    public CurrencyException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.type = null;
    }

    public String getErrorCode() {
        return errorCode.getCode();
    }
}
