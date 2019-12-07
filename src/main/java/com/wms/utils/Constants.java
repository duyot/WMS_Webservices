package com.wms.utils;

/**
 * Created by duyot on 8/18/2016.
 */
public class Constants {
    public static final String STATUS_OK = "200";
    public static final String STATS_ALL = "-1";
    public static final String IS_SERIAL = "1";
    public static final int COMMIT_NUMBER = 200;

//    public static class RESULT_NAME{
//        public static String ERROR_NOT_VALID_GOODS_IN_REQUEST = "ERROR_NOT_VALID_GOODS_IN_REQUEST";
//        public static String ERROR_CREATE_STOCK_TRANS = "ERROR_CREATE_STOCK_TRANS";
//        public static String ERROR_CREATE_STOCK_TRANS_DETAIL = "ERROR_CREATE_STOCK_TRANS_DETAIL";
//        public static String ERROR_CREATE_TOTAL = "ERROR_CREATE_TOTAL";
//        public static String ERROR_SYSTEM = "ERROR_SYSTEM";
//        public static String ERROR_EXPORT_GOODS = "ERROR_EXPORT_GOODS";
//        public static String ERROR_NOT_FOUND_TOTAL = "ERROR_NOT_FOUND_TOTAL";
//        public static String ERROR_NOT_FOUND_SERIAL = "ERROR_NOT_FOUND_SERIAL";
//        public static String ERROR_NOT_FOUND_STOCK_GOODS = "ERROR_NOT_FOUND_STOCK_GOODS";
//        public static String ERROR_TOTAL_NOT_ENOUGH = "ERROR_TOTAL_NOT_ENOUGH";
//        public static String ERROR_UPDATE_TOTAL = "ERROR_UPDATE_TOTAL";
//        public static String ERROR_NOT_FOUND_GOODS = "ERROR_NOT_FOUND_GOODS";
//        public static String ERROR_AMOUNT_NOT_VALID = "ERROR_AMOUNT_NOT_VALID";
//        public static String ERROR_OVER_GOODS_NUMBER = "ERROR_OVER_GOODS_NUMBER";
//    }

    public static class SQL_OPERATOR {
        public static String EQUAL = "EQUAL";
        public static String NOT_EQUAL = "NOT_EQUAL";
        public static String GREATER = "GREATER";
        public static String GREATER_EQAL = "GREATER_EQUAL";
        public static String LOWER = "LOWER";
        public static String LOWER_EQUAL = "LOWER_EQUAL";
        public static String IN = "IN";
        public static String LIKE = "LIKE";
        public static String ORDER = "ORDER";
        public static String BETWEEN = "BETWEEN";
        public static String LIMIT = "LIMIT";
        public static String OFFSET = "OFFSET";

    }

    public static class SQL_PRO_TYPE {
        public static String STRING = "string";
        public static String LONG = "long";
        public static String DATE = "date";
        public static String BYTE = "byte";
        public static String INT = "int";
    }

    public static class SQL_LOGIC {
        public static String OR = " or ";
        public static String AND = " and ";
    }

    public static class SQL_OP {
        public static final String OP_EQUAL = " = ";
        public static final String OP_NOT_EQUAL = " != ";
        public static final String OP_GREATER = " > ";
        public static final String OP_GREATER_EQUAL = " >= ";
        public static final String OP_LESS = " < ";
        public static final String OP_LESS_EQUAL = " <= ";
        public static final String OP_LIKE = " like ";
        public static final String OP_IN = " in ";
    }

    public static class TRANSACTION_TYPE {
        public static String IMPORT = "1";
        public static String EXPORT = "2";
    }

    public static class STATUS {
        public static String ACTIVE = "1";
        public static String INACTIVE = "0";
        public static String EXPORTED = "2";
        public static byte BYTE_EXPORTED = 2;

    }

    public static class DATETIME_FORMAT {
        public static final String ddMMyyyy = "dd/MM/yyyy";
        public static final String ddMMyyyyHHmmss = "dd/MM/yyyy HH:mm:ss";
    }
}
