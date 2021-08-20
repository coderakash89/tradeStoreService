package com.deutsche.tradeStoreService.tradestore.beans;

import java.util.HashMap;
import java.util.Map;

public enum ResponsesStatus {
    SUCCESS("SUCCESS"),
    FAILURE("FAILURE");

    private final String value;

    ResponsesStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ResponsesStatus{" +
                "value='" + value + '\'' +
                '}';
    }
}
