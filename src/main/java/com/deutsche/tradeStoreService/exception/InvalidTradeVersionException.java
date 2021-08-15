package com.deutsche.tradeStoreService.exception;

public class InvalidTradeVersionException extends RuntimeException {
    String errorCode;

    public InvalidTradeVersionException(String message) {
        super(message);
    }

    public InvalidTradeVersionException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
