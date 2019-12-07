/*
 * Copyright (C) 2012 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.wms.utils;

import com.wms.dto.Condition;
import java.util.ArrayList;
import java.util.List;

/**
 * @author binhnt22@viettel.com.vn
 * @version 1.1
 * @since May 2012
 */
public final class StringUtils {

    /**
     * alphabeUpCaseNumber.
     */
    private static String alphabeUpCaseNumber = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static String mask = "0123456789_aAá�?àÀảẢạẠãÃâÂấẤầẦẩẨậẬẫẪăĂắẮằẰẳẲặẶẵẴbBcCdDđ�?eEéÉèÈẻẺẹẸẽẼêÊếẾ�?ỀểỂệỆễỄfFgGhHiIí�?ìÌỉỈịIHĩĨjJkKlLmMnNoOóÓòÒ�?Ỏ�?ỌõÕôÔố�?ồỒổỔộỘỗỖơƠớỚ�?ỜởỞợỢỡỠpPqQrRsStTuUúÚùÙủỦụỤũŨưƯứỨừỪửỬựỰữỮvVwWxXyYý�?ỳỲỷỶỵỴỹỸzZ";
    private static String maskEN = "0123456789_aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
    /**
     * ZERO.
     */
    private static final String ZERO = "0";
    private static final String[] c = {"<", ">", "'", "\""};
    private static final String[] expansion = {"&lt;", "&gt;", "&apos;", "&quot;"};

    public static String HTMLEncode(String s) {
        for (int j = 0; j < c.length; j++) {
            if (s != null) {
                s = s.replace(c[j], expansion[j]);
            }
        }
        return s;
    }

    public static String HTMLDecode(String s) {
        String mine = s;
        for (int i = 0; i < c.length; i++) {
            if (s != null) {
                mine = mine.replaceAll(expansion[i], (c[i] + ""));
            }
        }
        return mine;
    }

    public static void main(String[] args) {
        String s = "Rack Huawei 2,2m < T? rack thi?t b? Huawei 2,2m >";
        System.out.println(s);
        System.out.println(HTMLEncode(s));
    }

    /**
     * method compare two string
     *
     * @param str1 String
     * @param str2 String
     * @return boolean
     */
    public static boolean compareString(String str1, String str2) {
        if (str1 == null) {
            str1 = "";
        }
        if (str2 == null) {
            str2 = "";
        }

        return str1.equals(str2);
    }

    /**
     * method convert long to string
     *
     * @param lng Long
     * @return String
     * @throws Exception
     */
    public static String convertFromLongToString(Long lng) throws Exception {
        return Long.toString(lng);
    }

    /*
     * @todo: convert from Long array to String array
     */
    public static String[] convertFromLongToString(Long[] arrLong) throws Exception {
        String[] arrResult = new String[arrLong.length];
        for (int i = 0; i < arrLong.length; i++) {
            arrResult[i] = convertFromLongToString(arrLong[i]);
        }
        return arrResult;
    }

    /*
     * @todo: convert from String array to Long array
     */
    public static long[] convertFromStringToLong(String[] arrStr) throws Exception {
        long[] arrResult = new long[arrStr.length];
        for (int i = 0; i < arrStr.length; i++) {
            arrResult[i] = Long.parseLong(arrStr[i]);
        }
        return arrResult;
    }

    /*
     * @todo: convert from String value to Long value
     */
    public static long convertFromStringToLong(String value) throws Exception {
        return Long.parseLong(value);
    }


    /*
     * Check String that containt only AlphabeUpCase and Number Return True if
     * String was valid, false if String was not valid
     */
    public static boolean checkAlphabeUpCaseNumber(String value) {
        boolean result = true;
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (alphabeUpCaseNumber.indexOf(temp) == -1) {
                result = false;
                return result;
            }
        }
        return result;
    }

    public static boolean validString(Object temp) {
        return temp != null && !temp.toString().trim().equals("");
    }

    public static boolean maskVN(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (mask.indexOf(str.charAt(i)) < 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean maskEN(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (maskEN.indexOf(str.charAt(i)) < 0) {
                return false;
            }
        }
        return str.toLowerCase().charAt(0) >= 'a' && str.toLowerCase().charAt(0) <= 'z';
    }

    public static boolean isInteger(String str) {
        return str != null && str.matches("[0-9]+$");
    }

    public static String formatString(String str) {
        return " '" + str.trim().toLowerCase() + "'";
    }

    public static String formatLike(String str) {
        return "%" + str.trim().toLowerCase().replaceAll("_", "\\\\_") + "%";
    }

    public static String formatOrder(String str, String direction) {
        return " NLSSORT(" + str + ",'NLS_SORT=vietnamese') " + direction;
    }

    public static String formatFunction(String function, String str) {
        return " " + function + "(" + str + ") ";
    }

    public static String formatConstant(String str) {
        String str1 = "";
        int index = 0;
        String alphabe = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 1; i < str.length(); i++) {
            if (alphabe.indexOf(str.charAt(i)) > 0) {
                str1 = str1 + str.substring(index, i).toUpperCase() + "_";
                index = i;
            }
        }
        str1 = str1 + str.substring(index).toUpperCase() + "_";
        return str1;
    }

    public static boolean isLong(String str) {
        try {
            Long.valueOf(str);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean containSpecialCharacteristic(String str) {
        if (str == null) {
            return false;
        }
        List lstSpecialCharacteristic = new ArrayList<String>();
        lstSpecialCharacteristic.add("!");
        lstSpecialCharacteristic.add("@");
        lstSpecialCharacteristic.add("#");
        lstSpecialCharacteristic.add("%");
        lstSpecialCharacteristic.add("^");
        lstSpecialCharacteristic.add("&");
        lstSpecialCharacteristic.add("*");
        lstSpecialCharacteristic.add("(");
        lstSpecialCharacteristic.add(")");
        lstSpecialCharacteristic.add(" ");
        for (int i = 0; i < lstSpecialCharacteristic.size(); i++) {
            if (str.contains(lstSpecialCharacteristic.get(i).toString())) {
                return true;
            }
        }
        return false;
    }

    public static String upperFirstChar(String input) {
        if (isNullOrEmpty(input)) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static String convertTypeOperator(String operator) {
        String opConvert = "";
        if (StringUtils.isNullOrEmpty(operator)) {
            return opConvert;
        }
        switch (operator) {
            case "EQUAL":
                opConvert = Constants.SQL_OP.OP_EQUAL;
                break;
            case "NOT_EQUAL":
                opConvert = Constants.SQL_OP.OP_NOT_EQUAL;
                break;
            case "GREATER":
                opConvert = Constants.SQL_OP.OP_GREATER;
                break;
            case "GREATER_EQUAL":
                opConvert = Constants.SQL_OP.OP_GREATER_EQUAL;
                break;
            case "LOWER":
                opConvert = Constants.SQL_OP.OP_LESS;
                break;
            case "LOWER_EQUAL":
                opConvert = Constants.SQL_OP.OP_LESS_EQUAL;
                break;
            case "IN":
                opConvert = Constants.SQL_OP.OP_IN;
                break;
            case "LIKE":
                opConvert = Constants.SQL_OP.OP_LIKE;
                break;
            default:
                opConvert = "";
                break;
        }
        return opConvert;
    }

    public static List<Condition> convertCondition(List<Condition> lstCondition) {

        for (Condition con : lstCondition) {
            if (con.getPropertyType().equalsIgnoreCase(Constants.SQL_PRO_TYPE.DATE)) {
                con.setProperty(StringUtils.formatFunction("trunc", con.getProperty()));
            }
            con.setOperator(StringUtils.convertTypeOperator(con.getOperator()));
            con.setProperty(" " + con.getProperty() + " ");
            con.setValue(con.getValue());
        }
        return lstCondition;
    }


    public static boolean isNullOrEmpty(String obj1) {
        return (obj1 == null || "".equals(obj1.trim()));
    }

    public static boolean isLongNullOrEmpty(Long obj1) {
        return (obj1 == null || "0L".equals(obj1));
    }

    public static boolean isDoubleNullOrEmpty(Double obj1) {
        return (obj1 == null || "0D".equals(obj1));
    }

    //
    public static boolean isStringNullOrEmpty(Object obj1) {
        return obj1 == null || obj1.toString().trim().equals("");
    }

    //Convert operator type
}
