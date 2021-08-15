package com.deutsche.tradeStoreService.tradestore.controller;

import com.deutsche.tradeStoreService.exception.InvalidTradeVersionException;
import com.deutsche.tradeStoreService.tradestore.beans.ResponseBean;
import com.deutsche.tradeStoreService.tradestore.dto.TradeDTO;
import com.deutsche.tradeStoreService.tradestore.service.TradeService;
import com.deutsche.tradeStoreService.tradestore.util.CommonUtils;
import com.deutsche.tradeStoreService.tradestore.util.ErrorCodes;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tradestore")
public class TradeController {

    Logger logger = LoggerFactory.getLogger(TradeController.class);

    @Autowired
    private TradeService tradeService;

    @GetMapping(value="/trades", produces = "application/json")
    public ResponseBean getTrades() {
        logger.info("Fetching call trades");
        return tradeService.getAllTrades();
    }

    @PutMapping(value="create", produces = "application/json")
    public ResponseBean create(@RequestBody @NotNull TradeDTO tradeDTO) {
        ResponseBean<TradeDTO> responseBean = new ResponseBean<>();
        logger.info("Saving Trade:"+tradeDTO.getTradeId());
        try {
            logger.debug("Saving trade: "+tradeDTO);
            responseBean = tradeService.saveTrade(tradeDTO);
            logger.debug("Trade saved successfully "+tradeDTO);
            logger.info("Trade saved successfully: "+tradeDTO.getTradeId());
        } catch (InvalidTradeVersionException invalidTradeException) {
            responseBean.setData(tradeDTO);
            CommonUtils.generateErrorMessage(invalidTradeException.getErrorCode(), invalidTradeException.getMessage(), responseBean);
            logger.error(invalidTradeException.getMessage(), invalidTradeException);
        } catch (Exception exception) {
            responseBean.setData(tradeDTO);
            CommonUtils.generateErrorMessage(ErrorCodes.GENERIC_ERROR_CODE, ErrorCodes.GENERIC_ERROR_MESSAGE, responseBean);
            logger.error(ErrorCodes.GENERIC_ERROR_MESSAGE, exception);
        }

        return responseBean;
    }

}
