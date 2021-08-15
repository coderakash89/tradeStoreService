package com.deutsche.tradeStoreService.service.impl;

import com.deutsche.tradeStoreService.mapper.TradeStoreMapper;
import com.deutsche.tradeStoreService.tradestore.beans.ResponseBean;
import com.deutsche.tradeStoreService.tradestore.beans.ResponsesStatus;
import com.deutsche.tradeStoreService.tradestore.dto.TradeDTO;
import com.deutsche.tradeStoreService.tradestore.entity.Trade;
import com.deutsche.tradeStoreService.tradestore.repository.TradeStoreRepository;
import com.deutsche.tradeStoreService.tradestore.service.impl.TradeServiceImpl;
import com.deutsche.tradeStoreService.tradestore.util.ErrorCodes;
import com.deutsche.tradeStoreService.tradestore.util.ResourceUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TradeServiceImplTest {

    @InjectMocks
    TradeServiceImpl tradeService;

    @Mock
    TradeStoreRepository tradeStoreRepository;

    TradeStoreMapper tradeStoreMapper;


    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        tradeStoreMapper = new TradeStoreMapper();
        ReflectionTestUtils.setField(tradeService, "tradeStoreMapper", tradeStoreMapper);
    }

    @Test
    public void saveTrade_validMaturity_test() throws ParseException {
        TradeDTO tradeDTO = ResourceUtil.readResourceContents("/mock-json/validTradeDTO.json", new TypeReference<TradeDTO>() {
        });

        Trade trade = ResourceUtil.readResourceContents("/mock-json/savedTrade.json", new TypeReference<Trade>() {
        });


        ResponseBean<TradeDTO> response = new ResponseBean<>();
        response.setResponsesStatus(ResponsesStatus.SUCCESS);
        response.setErrorBean(null);
        response.setData(tradeDTO);

        Optional<Trade> existingTrade = Optional.of(trade);
        when(tradeStoreRepository.findById(tradeDTO.getTradeId())).thenReturn(existingTrade);
        when(tradeStoreRepository.save(any(Trade.class))).thenReturn(trade);

        ResponseBean<TradeDTO> responseBean = tradeService.saveTrade(tradeDTO);

        Assert.assertEquals(ResponsesStatus.SUCCESS, responseBean.getResponsesStatus());
        Assert.assertNull(responseBean.getErrorBean());
        Assert.assertEquals(response.getData(), tradeDTO);
    }

    @Test
    public void saveTrade_newTrade_test() throws ParseException {
        TradeDTO tradeDTO = ResourceUtil.readResourceContents("/mock-json/validTradeDTO.json", new TypeReference<TradeDTO>() {
        });

        Trade trade = ResourceUtil.readResourceContents("/mock-json/savedTrade.json", new TypeReference<Trade>() {
        });

        ResponseBean<TradeDTO> response = new ResponseBean<>();
        response.setResponsesStatus(ResponsesStatus.SUCCESS);
        response.setErrorBean(null);
        response.setData(tradeDTO);

        Optional<Trade> existingTrade = Optional.empty();
        when(tradeStoreRepository.findById(tradeDTO.getTradeId())).thenReturn(existingTrade);
        when(tradeStoreRepository.save(any(Trade.class))).thenReturn(trade);

        ResponseBean<TradeDTO> responseBean = tradeService.saveTrade(tradeDTO);

        Assert.assertEquals(ResponsesStatus.SUCCESS, responseBean.getResponsesStatus());
        Assert.assertNull(responseBean.getErrorBean());
        Assert.assertEquals(response.getData(), tradeDTO);
    }

    @Test
    public void saveTrade_InvalidMaturity_test() throws ParseException {
        TradeDTO tradeDTO = ResourceUtil.readResourceContents("/mock-json/inValidTradeDTO.json", new TypeReference<TradeDTO>() {
        });

        ResponseBean<TradeDTO> responseBean = tradeService.saveTrade(tradeDTO);

        Assert.assertEquals(ResponsesStatus.FAILURE, responseBean.getResponsesStatus());
        Assert.assertEquals(responseBean.getData(), tradeDTO);
        Assert.assertNotNull(responseBean.getErrorBean());
        Assert.assertEquals(ErrorCodes.INVALID_MATURITY_DATE_MESSAGE, responseBean.getErrorBean().getErrorMessage());
        Assert.assertEquals(ErrorCodes.INVALID_MATURITY_DATE_ERROR_CODE, responseBean.getErrorBean().getErrorCode());
    }

    @Test
    public void getAllTrade_test() {
        List<Trade> tradeEntityList = ResourceUtil.readResourceContents("/mock-json/savedTrades.json", new TypeReference< List<Trade>>() {
        });

        List<TradeDTO> expectedList = ResourceUtil.readResourceContents("/mock-json/savedTradesDTO.json", new TypeReference< List<TradeDTO>>() {
        });

        when(tradeStoreRepository.findAll()).thenReturn(tradeEntityList);

        ResponseBean<List<TradeDTO>> responseBean = tradeService.getAllTrades();

        Assert.assertEquals(ResponsesStatus.SUCCESS, responseBean.getResponsesStatus());
        Assert.assertEquals(responseBean.getData(), expectedList);
        Assert.assertNull(responseBean.getErrorBean());
    }

}
