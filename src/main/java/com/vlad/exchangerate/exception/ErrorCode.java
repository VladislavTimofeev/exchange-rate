package com.vlad.exchangerate.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    CURRENCY_ALREADY_EXISTS("CURRENCY_ALREADY_EXISTS", "Currency already exists in the system."),
    EXCHANGE_RATE_FETCH_FAILED("EXCHANGE_RATE_FETCH_FAILED", "Failed to fetch exchange rate."),
    INVALID_CURRENCY_RATE("INVALID_CURRENCY_RATE", "Invalid currency rate provided."),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "Requested resource not found.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
