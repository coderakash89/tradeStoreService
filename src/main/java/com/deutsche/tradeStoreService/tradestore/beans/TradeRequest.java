package com.deutsche.tradeStoreService.tradestore.beans;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TradeRequest {
    private Integer tradeId;

    private Integer version;

    private String counterPartyId;

    private String bookId;

    private LocalDate maturityDate;

    private LocalDate createdDate;

    private boolean expired;
}
