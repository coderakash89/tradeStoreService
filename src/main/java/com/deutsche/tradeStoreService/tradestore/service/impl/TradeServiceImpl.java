package com.deutsche.tradeStoreService.tradestore.service.impl;

import com.deutsche.tradeStoreService.exception.InvalidTradeVersionException;
import com.deutsche.tradeStoreService.mapper.TradeStoreMapper;
import com.deutsche.tradeStoreService.tradestore.beans.ErrorBean;
import com.deutsche.tradeStoreService.tradestore.beans.ResponseBean;
import com.deutsche.tradeStoreService.tradestore.beans.ResponsesStatus;
import com.deutsche.tradeStoreService.tradestore.dto.TradeDTO;
import com.deutsche.tradeStoreService.tradestore.entity.Trade;
import com.deutsche.tradeStoreService.tradestore.repository.TradeStoreRepository;
import com.deutsche.tradeStoreService.tradestore.service.TradeService;
import com.deutsche.tradeStoreService.tradestore.util.ErrorCodes;
import com.deutsche.tradeStoreService.tradestore.util.TradeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {

    Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);

    @Autowired
    private TradeStoreRepository tradeStoreRepository;

    @Autowired
    private TradeStoreMapper tradeStoreMapper;


    @Override
    public ResponseBean<TradeDTO> saveTrade(TradeDTO tradeDTO) throws ParseException {

        logger.debug("executing saveTrade for "+tradeDTO);

        ResponseBean<TradeDTO> responseBean = new ResponseBean<>();
        responseBean.setErrorBean(null);
        Trade responseTrade;

        if(TradeValidator.validMaturityDate(tradeDTO.getMaturityDate())) {
            Optional<Trade> existingTrade = tradeStoreRepository.findById(tradeDTO.getTradeId());

            if(existingTrade.isPresent()) {
                logger.info("Existing Trade"+tradeDTO.getTradeId());

                if(TradeValidator.hasValidVersion(existingTrade.get().getVersion(), tradeDTO.getVersion())) {
                    //For equal(or higher) we are overriding Trade
                    Trade trade = tradeStoreMapper.convertDTOToEntity(tradeDTO);
                    responseTrade = tradeStoreRepository.save(trade);

                    logger.info("Trade Saved successfully "+trade);
                } else {
                    String errorMessage = "Trade- "+tradeDTO.getTradeId() + " has invalid version";
                    logger.error(errorMessage);
                    throw new InvalidTradeVersionException(errorMessage, ErrorCodes.INVALID_TRADE_VERSION_ERROR_CODE);
                }
            } else {
                logger.info("Creating new Trade..");

                Trade trade = tradeStoreMapper.convertDTOToEntity(tradeDTO);
                trade.setCreatedDate(new Date());
                responseTrade = tradeStoreRepository.save(trade);

                logger.info("New Trade Created Successfully "+responseTrade);
            }

            responseBean.setResponsesStatus(ResponsesStatus.SUCCESS);
            TradeDTO responseDTO = tradeStoreMapper.convertEntityToDTO(responseTrade);
            responseBean.setData(responseDTO);

            return responseBean;
        }

        responseBean.setResponsesStatus(ResponsesStatus.FAILURE);
        responseBean.setData(tradeDTO);
        responseBean.setErrorBean(ErrorBean.builder().errorMessage(ErrorCodes.INVALID_MATURITY_DATE_MESSAGE).
                    errorCode(ErrorCodes.INVALID_MATURITY_DATE_ERROR_CODE).build());

        return responseBean;
    }

    @Override
    public ResponseBean<List<TradeDTO>> getAllTrades() {
        logger.info("Get all trades");
        List<TradeDTO> tradeDTOList = new ArrayList<>();
        List<Trade> tradeList = tradeStoreRepository.findAll();

        for(Trade trade: tradeList) {
            tradeDTOList.add(tradeStoreMapper.convertEntityToDTO(trade));
        }

        ResponseBean<List<TradeDTO>> tradeDTOResponseBean = new ResponseBean<>();
        tradeDTOResponseBean.setResponsesStatus(ResponsesStatus.SUCCESS);
        tradeDTOResponseBean.setData(tradeDTOList);

        return tradeDTOResponseBean;
    }

    @Override
    public void updateTradeExpiry() throws ParseException {
        logger.info("Get all non expired trades..");
       List<Trade> tradeList = tradeStoreRepository.findNonExpiredTrades();

       for(Trade trade:tradeList) {
           if(!TradeValidator.validMaturityDate(trade.getMaturityDate())) {
               trade.setExpired(true);
               tradeStoreRepository.save(trade);
           }
       }
    }

}
