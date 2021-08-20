package com.deutsche.tradeStoreService.tradestore.controller;

import com.deutsche.tradeStoreService.tradestore.beans.ResponseBean;
import com.deutsche.tradeStoreService.tradestore.dto.TradeDTO;
import com.deutsche.tradeStoreService.tradestore.service.TradeService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/tradestore")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @GetMapping(value="/trades", produces = "application/json")
    public ResponseBean getTrades() {
        return tradeService.getAllTrades();
    }

    @PutMapping(value="save", produces = "application/json")
    public ResponseBean save(@RequestBody @NotNull TradeDTO tradeDTO) throws ParseException {
            ResponseBean<TradeDTO>  responseBean = tradeService.saveTrade(tradeDTO);
        return responseBean;
    }

}
