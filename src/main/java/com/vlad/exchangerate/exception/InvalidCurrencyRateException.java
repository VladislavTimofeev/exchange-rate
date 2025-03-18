package com.vlad.exchangerate.exception;

public class InvalidCurrencyRateException extends RuntimeException {
    public InvalidCurrencyRateException(String message) {
        super(message);
    }
}
