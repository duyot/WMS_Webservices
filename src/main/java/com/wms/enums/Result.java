package com.wms.enums;

/**
 * Created by duyot on 10/4/2016.
 */
public enum Result {
    SUCCESS("SUCCESS","200"),ERROR("FAIL","-1");
    public String resultName;
    public String resultCode;
    Result(String resultName,String resultCode){
        this.resultCode = resultCode;
        this.resultName = resultName;
    }
    public String getName(){
        return this.resultName;
    }
    public String getCode(){
        return this.resultCode;
    }

}
