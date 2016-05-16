package com.mysimplework.core.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dzhao on 22/02/2016.
 */
public class MyStringUtil {

    private static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    private MyStringUtil(){}

    public static BigDecimal toDecimal(String input){
        return BigDecimal.valueOf(Float.valueOf(input).floatValue());
    }

    public static Date toDate(String dateStr){
        return toDate(dateStr, DATE_FORMAT);
    }

    /**
     * To convert string to date by given date format
     * @param dateStr
     * @param datePattern
     * @return
     */
    public static Date toDate(String dateStr, String datePattern){
        SimpleDateFormat df = new SimpleDateFormat( datePattern!=null ? datePattern : DATE_FORMAT );
        try {
            return df.parse(dateStr);
        } catch (Exception e) {
            throw new UnsupportedOperationException("unsupported date format [" + dateStr + "]");
        }
    }

    /**
     * To convert string to date by default simple date format {dd/MM/yyyy HH:mm}
     * @param dateStr
     * @return
     */
    public static Date toDateTime(String dateStr){
        return toDateTime(dateStr, null);
    }

    /**
     * To convert string to date by given date format
     * @param dateStr
     * @return
     */
    public static Date toDateTime(String dateStr, String datePattern){
        SimpleDateFormat df = new SimpleDateFormat( datePattern!=null ? datePattern : DATETIME_FORMAT );
        try {
            return df.parse(dateStr);
        } catch (Exception e) {
            throw new UnsupportedOperationException("unsupported date format [" + dateStr + "]");
        }
    }

}
