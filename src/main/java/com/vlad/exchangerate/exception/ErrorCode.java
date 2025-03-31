package com.vlad.exchangerate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    CURRENCY_ALREADY_EXISTS("Currency already exists in the system."),
    EXCHANGE_RATE_FETCH_FAILED("Failed to fetch exchange rate."),
    INVALID_CURRENCY_RATE("Invalid currency rate provided."),
    RESOURCE_NOT_FOUND("Requested resource not found.");

    private final String message;

    public String getCode() {
        return name();
    }
}
