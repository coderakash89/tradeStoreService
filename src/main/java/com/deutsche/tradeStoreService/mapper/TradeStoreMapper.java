package com.deutsche.tradeStoreService.mapper;

import com.deutsche.tradeStoreService.tradestore.dto.TradeDTO;
import com.deutsche.tradeStoreService.tradestore.entity.Trade;
import org.springframework.stereotype.Component;

@Component
public class TradeStoreMapper {

    public TradeDTO convertEntityToDTO(Trade trade) {
            TradeDTO tradeDTO = new TradeDTO();
        tradeDTO.setTradeId(trade.getTradeId());
        tradeDTO.setBookId(trade.getBookId());
        tradeDTO.setCounterPartyId(trade.getCounterPartyId());
        tradeDTO.setExpired(trade.isExpired());
        tradeDTO.setCreatedDate(trade.getCreatedDate());
        tradeDTO.setVersion(trade.getVersion());
        tradeDTO.setMaturityDate(trade.getMaturityDate());

        return tradeDTO;
    }

    public Trade convertDTOToEntity(TradeDTO tradeDTO) {
        Trade trade = new Trade();
        trade.setTradeId(tradeDTO.getTradeId());
        trade.setBookId(tradeDTO.getBookId());
        trade.setCounterPartyId(tradeDTO.getCounterPartyId());
        trade.setExpired(tradeDTO.isExpired());
        trade.setCreatedDate(tradeDTO.getCreatedDate());
        trade.setVersion(tradeDTO.getVersion());
        trade.setMaturityDate(tradeDTO.getMaturityDate());

        return trade;
    }
}
