package com.deutsche.tradeStoreService.tradestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeIdCompositeKey implements Serializable {
    private String tradeId;
    private Integer version;
}
