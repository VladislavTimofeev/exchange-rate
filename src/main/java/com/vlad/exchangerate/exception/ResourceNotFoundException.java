package com.vlad.exchangerate.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String resourceInfo;

    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.resourceInfo = null;
    }

    public ResourceNotFoundException(ErrorCode errorCode, String errorMessage) {
        super(errorMessage != null ? errorMessage : errorCode.getMessage());
        this.errorCode = errorCode;
        this.resourceInfo = null;
    }

    public ResourceNotFoundException(ErrorCode errorCode, String resourceInfo, String errorMessage) {
        super(errorMessage != null ? errorMessage : errorCode.getMessage());
        this.errorCode = errorCode;
        this.resourceInfo = resourceInfo;
    }
}
