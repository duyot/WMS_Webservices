package com.wms.utils;

/**
 * Created by duyot on 8/18/2016.
 */
public class Constants {
    public static final String STATUS_OK = "200";
    public static final String IS_SERIAL = "1";

    public static class RESULT_NAME{
        public static String ERROR_CREATE_STOCK_TRANS = "ERROR_CREATE_STOCK_TRANS";
        public static String ERROR_CREATE_STOCK_TRANS_DETAIL = "ERROR_CREATE_STOCK_TRANS_DETAIL";
        public static String ERROR_CREATE_STOCK_GOODS = "ERROR_CREATE_STOCK_GOODS";
        public static String ERROR_CREATE_TOTAL = "ERROR_CREATE_TOTAL";
        public static String ERROR_SYSTEM = "ERROR_SYSTEM";
    }

    public static class SQL_OPERATOR{
        public static String EQUAL = "EQUAL";
        public static String NOT_EQUAL = "NOT_EQUAL";
        public static String GREATER = "GREATER";
        public static String GREATER_EQAL = "GREATER_EQUAL";
        public static String LOWER = "LOWER";
        public static String LOWER_EQUAL = "LOWER_EQUAL";
        public static String IN = "IN";
        public static String LIKE = "LIKE";
        public static String ORDER = "ORDER";
    }

    public static class SQL_PRO_TYPE{
        public static String STRING = "string";
        public static String LONG   = "long";
        public static String DATE   = "date";
    }

    public static class STATUS{
        public static String ACTIVE = "1";
        public static String INACTIVE = "0";
    }

}
