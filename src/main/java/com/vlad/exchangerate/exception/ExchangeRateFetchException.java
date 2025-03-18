package com.vlad.exchangerate.exception;

public class ExchangeRateFetchException extends RuntimeException{

    public ExchangeRateFetchException() {
        super("Failed to fetch exchange rate");
    }

    public ExchangeRateFetchException(String message) {
        super(message);
    }

    public ExchangeRateFetchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExchangeRateFetchException(Throwable cause) {
        super(cause);
    }
}
