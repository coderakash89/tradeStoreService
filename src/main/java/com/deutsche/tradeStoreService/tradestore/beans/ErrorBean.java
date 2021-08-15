package com.deutsche.tradeStoreService.tradestore.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorBean implements Serializable {
    private String errorCode;
    private String errorMessage;
}
