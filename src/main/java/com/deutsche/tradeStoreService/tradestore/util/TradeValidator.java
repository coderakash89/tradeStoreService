package com.deutsche.tradeStoreService.tradestore.util;

import java.text.ParseException;
import java.util.Date;

public class TradeValidator {

    /*
     Case 1: Reject trade if the lower version is being received
   */
    public static boolean hasValidVersion(Integer olderVersion, Integer newVersion) {
        return (olderVersion <= newVersion);
    }

    /*
    Case 2: Reject trade if  maturity date is less than today date.
     */

    public static boolean validMaturityDate(Date tradeMaturityDate) throws ParseException {
        Date timeZonedDate = CommonUtils.convertDateToTimeZone(Constant.TRADE_TIME_ZONE,
                Constant.DATE_FORMAT, new Date());

       return !tradeMaturityDate.before(timeZonedDate);
    }

}
