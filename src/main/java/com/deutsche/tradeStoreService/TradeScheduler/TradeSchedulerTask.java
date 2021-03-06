package com.deutsche.tradeStoreService.TradeScheduler;

import com.deutsche.tradeStoreService.tradestore.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class TradeSchedulerTask {

    Logger logger = LoggerFactory.getLogger(TradeSchedulerTask.class);

    @Autowired
    private TradeService tradeService;

    @Scheduled(cron = "${trade.scheduler.cron.refresh}")
    public void tradeScheduler() throws ParseException {
        tradeService.updateTradeExpiry();
    }
}
