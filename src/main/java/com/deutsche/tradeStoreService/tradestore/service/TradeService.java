package com.deutsche.tradeStoreService.tradestore.service;

import com.deutsche.tradeStoreService.tradestore.beans.ResponseBean;
import com.deutsche.tradeStoreService.tradestore.entity.Trade;
import com.deutsche.tradeStoreService.tradestore.dto.TradeDTO;

import java.text.ParseException;
import java.util.List;

public interface TradeService {
    ResponseBean<TradeDTO> saveTrade(TradeDTO tradeDTO) throws ParseException;
    ResponseBean<List<TradeDTO>> getAllTrades();
    void updateTradeExpiry() throws ParseException;
}
