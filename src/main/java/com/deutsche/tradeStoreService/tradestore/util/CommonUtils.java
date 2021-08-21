package com.deutsche.tradeStoreService.tradestore.util;

import com.deutsche.tradeStoreService.tradestore.beans.ErrorBean;
import com.deutsche.tradeStoreService.tradestore.beans.ResponseBean;
import com.deutsche.tradeStoreService.tradestore.beans.ResponsesStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CommonUtils {
    public static void generateErrorMessage(String errorCode, String message,ResponseBean<?> responseBean) {
        responseBean.setResponsesStatus(ResponsesStatus.FAILURE);
        responseBean.setErrorBean(ErrorBean.builder().errorCode(errorCode).errorMessage(message).build());
    }

    public static Date convertDateToTimeZone(String timeZone, String format, Date date) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat(format);

        // To TimeZone America/New_York
        SimpleDateFormat sdfAmerica = new SimpleDateFormat(format);
        TimeZone tzInAmerica = TimeZone.getTimeZone(timeZone);
        sdfAmerica.setTimeZone(tzInAmerica);

        String sDateInAmerica = sdfAmerica.format(date); // Convert to String first
        Date convertedDate = formatter.parse(sDateInAmerica); // Create a new Date object

        return convertedDate;
    }
}
