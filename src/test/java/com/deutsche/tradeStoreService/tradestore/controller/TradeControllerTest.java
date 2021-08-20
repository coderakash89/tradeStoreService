package com.deutsche.tradeStoreService.tradestore.controller;

import com.deutsche.tradeStoreService.exception.InvalidTradeVersionException;
import com.deutsche.tradeStoreService.tradestore.beans.ResponseBean;
import com.deutsche.tradeStoreService.tradestore.beans.ResponsesStatus;
import com.deutsche.tradeStoreService.tradestore.dto.TradeDTO;
import com.deutsche.tradeStoreService.tradestore.service.TradeService;
import com.deutsche.tradeStoreService.tradestore.util.ErrorCodes;
import com.deutsche.tradeStoreService.tradestore.util.ResourceUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;

public class TradeControllerTest {

    @InjectMocks
    TradeController tradeController;

    @Mock
    TradeService tradeService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create_success() throws ParseException {
        TradeDTO tradeDTO = ResourceUtil.readResourceContents("/mock-json/validTradeDTO.json", new TypeReference<TradeDTO>() {
        });

        ResponseBean<TradeDTO> response = new ResponseBean<>();
        response.setResponsesStatus(ResponsesStatus.SUCCESS);
        response.setErrorBean(null);
        response.setData(tradeDTO);

        when(tradeService.saveTrade(any(TradeDTO.class))).thenReturn(response);

        ResponseBean<TradeDTO> responseBean = tradeController.save(tradeDTO);

        Assert.assertEquals(responseBean.getResponsesStatus(), ResponsesStatus.SUCCESS);
        Assert.assertNull(responseBean.getErrorBean());
        Assert.assertEquals(tradeDTO, response.getData());
    }

    @Test(expected = InvalidTradeVersionException.class)
    public void create_InvalidVer_exception() throws ParseException {
        TradeDTO tradeDTO = ResourceUtil.readResourceContents("/mock-json/validTradeDTO.json", new TypeReference<TradeDTO>() {
        });
        String errorMessage = "Trade- "+tradeDTO.getTradeId() + " has invalid version";
        when(tradeService.saveTrade(any(TradeDTO.class))).thenThrow(new InvalidTradeVersionException(errorMessage, ErrorCodes.INVALID_TRADE_VERSION_ERROR_CODE));
        tradeController.save(tradeDTO);
    }

}
