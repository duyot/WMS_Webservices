package com.wms.utils;

/**
 * Created by duyot on 8/18/2016.
 */
public class Constants {
    public static final String STATUS_OK = "200";

    public static class SQL_OPERATOR{
        public static String EQUAL = "EQUAL";
        public static String NOT_EQUAL = "NOT_EQUAL";
        public static String GREATER = "GREATER";
        public static String GREATER_EQAL = "GREATER_EQAL";
        public static String LOWER = "LOWER";
        public static String LOWER_EQUAL = "LOWER_EQUAL";
        public static String IN = "IN";
        public static String LIKE = "LIKE";
    }

    public static class RESULT{
        public static String SUCCESS = "SUCCESS";
        public static String FAIL = "FAIL";
    }

    public static class STATUS{
        public static String ACTIVE = "1";
        public static String INACTIVE = "0";
    }

    public static class VIEW{
        public static String HOME = "index";
        public static String CHILD = "child";
        public static String ERROR = "error";

        public static class HOME_SUBS{
            public static String USER_CP = "usermanagerment/usercp";
        }

        public static class USER_CP_MAIN{
            public static String LIST_OF_USER = "usermanagerment/listofuser";
            public static String REGISTER_FORM = "usermanagerment/register_form";
        }

    }

    public static class SEVERLET_MAPPING{

        public static class HOME_SUBS{
            public static final String USERCP = "usercp";
        }

        public static class USER_CP{
            public static String LIST_OF_USER = "usercp/listofuser";
            public static String REGISTER = "usercp/register";
            public static String SUBMIT_REGISTER = "usercp/submitRegister";
        }
    }
}
