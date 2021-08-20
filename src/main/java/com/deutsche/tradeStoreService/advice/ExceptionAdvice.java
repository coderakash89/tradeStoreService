package com.deutsche.tradeStoreService.advice;

import com.deutsche.tradeStoreService.exception.InvalidTradeVersionException;
import com.deutsche.tradeStoreService.tradestore.beans.ResponseBean;
import com.deutsche.tradeStoreService.tradestore.dto.TradeDTO;
import com.deutsche.tradeStoreService.tradestore.util.CommonUtils;
import com.deutsche.tradeStoreService.tradestore.util.ErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(value = {InvalidTradeVersionException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseBean handleInvalidTradeException(InvalidTradeVersionException invalidTradeException) {
        return handleException(invalidTradeException.getErrorCode(), invalidTradeException.getMessage(), invalidTradeException);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBean handleGlobalException(Exception exception) {
        return handleException(ErrorCodes.GENERIC_ERROR_CODE, ErrorCodes.GENERIC_ERROR_MESSAGE, exception);
    }

    private ResponseBean handleException(String errorCode, String errorMessage, Exception exception) {
        ResponseBean<TradeDTO> responseBean = new ResponseBean<>();
        CommonUtils.generateErrorMessage(errorCode, errorMessage, responseBean);
        return responseBean;
    }
}
