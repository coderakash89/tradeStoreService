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
import com.deutsche.tradeStoreService.tradestore.util.CommonUtils;
import com.deutsche.tradeStoreService.tradestore.util.Constant;
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
             Trade trade = tradeStoreRepository.findTradeWithSameVersion(tradeDTO.getTradeId(), tradeDTO.getVersion());

                if(trade != null) {
                    //Existing with same version then override
                    Trade newTrade = tradeStoreMapper.convertDTOToEntity(tradeDTO);
                    responseTrade = tradeStoreRepository.save(newTrade);
                    logger.info("Trade Overridden successfully " + trade);
                } else {
                    // Check if Trade With higher version available
                       Trade higherVTrade = tradeStoreRepository.findTradeWithHigherVersion(tradeDTO.getTradeId(), tradeDTO.getVersion());
                       if(higherVTrade ==null) {
                        //Means current Trade is of higher version and add it in database
                           Trade newTrade = tradeStoreMapper.convertDTOToEntity(tradeDTO);
                           newTrade.setCreatedDate(CommonUtils.convertDateToTimeZone(Constant.TRADE_TIME_ZONE, Constant.DATE_FORMAT, new Date()));
                           responseTrade = tradeStoreRepository.save(newTrade);
                       } else {
                            String errorMessage = "Trade- "+tradeDTO.getTradeId() + " has invalid version "+tradeDTO.getVersion() +
                                    " Higher version "+higherVTrade.getVersion()+" already available";
                            logger.error(errorMessage);
                            throw  new InvalidTradeVersionException(errorMessage, ErrorCodes.INVALID_TRADE_VERSION_ERROR_CODE);
                       }
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
