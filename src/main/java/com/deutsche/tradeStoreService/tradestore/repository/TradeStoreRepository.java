package com.deutsche.tradeStoreService.tradestore.repository;

import com.deutsche.tradeStoreService.tradestore.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeStoreRepository extends JpaRepository<Trade, String>  {

    @Query(value = "SELECT * FROM TRADE t WHERE t.expired = FALSE" , nativeQuery = true)
    List<Trade> findNonExpiredTrades();


    @Query(value = "SELECT * FROM TRADE t WHERE t.trade_id =?1 AND t.version =?2" , nativeQuery = true)
    Trade findTradeWithSameVersion(String tradeId, Integer tradeVersion);

    @Query(value = "SELECT * FROM TRADE t WHERE t.trade_id =?1 AND t.version >?2", nativeQuery = true)
    Trade findTradeWithHigherVersion(String tradeId,Integer tradeVersion);
}
