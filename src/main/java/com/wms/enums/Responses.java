package com.wms.enums;

/**
 * Created by duyot on 10/4/2016.
 */
public enum Responses {
    SUCCESS("SUCCESS","200"),ERROR("FAIL","-1"),NOT_FOUND("NOT_FOUND","404"),
    ERROR_CONSTRAINT("FAIL_CONSTRAINT","ORA-00001"),SUCCESS_WITH_ERROR("SUCCESS_WITH_ERROR","ORA-00002");
    public String statusName;
    public String statusCode;
    Responses(String statusName,String statusCode){
        this.statusCode = statusCode;
        this.statusName = statusName;
    }
    public String getName(){
        return this.statusName;
    }
    public String getCode(){
        return this.statusCode;
    }

    public static String getCodeByName(String name) {
        for(Responses e : values()) {
            if(e.statusName.equals(name)) return e.getCode();
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(Responses.getCodeByName("SUCCESS"));
    }

}
