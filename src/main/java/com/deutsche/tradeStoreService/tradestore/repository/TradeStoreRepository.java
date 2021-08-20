package com.deutsche.tradeStoreService.tradestore.repository;

import com.deutsche.tradeStoreService.tradestore.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TradeStoreRepository extends JpaRepository<Trade, String>  {

    @Query(value = "SELECT * FROM TRADE t WHERE t.expired = FALSE" , nativeQuery = true)
    public List<Trade> findNonExpiredTrades();

    //List<Trade> findByExpiredFalse();
}
