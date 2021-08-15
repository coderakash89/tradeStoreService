package com.deutsche.tradeStoreService.tradestore.beans;

import java.util.HashMap;
import java.util.Map;

public enum ResponsesStatus {
    SUCCESS("SUCCESS"),
    FAILURE("FAILURE");

    private final String value;
    private static final Map<String, ResponsesStatus> CONSTANTS = new HashMap<>();

    ResponsesStatus(String value) {
        this.value = value;
    }

    static {
        for(ResponsesStatus responsesStatus: values()) {
            CONSTANTS.put(responsesStatus.value, responsesStatus);
        }
    }

    public static ResponsesStatus fromValue(String value) {
        ResponsesStatus responsesStatus =CONSTANTS.get(value);
        if(responsesStatus == null){
            throw new IllegalArgumentException(value);
        } else {
            return responsesStatus;
        }
    }

    @Override
    public String toString() {
        return "ResponsesStatus{" +
                "value='" + value + '\'' +
                '}';
    }
}
