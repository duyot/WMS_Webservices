package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.persistents.model.CatPartner;
import com.wms.utils.StringUtils;
/**
 * Created by doanlv4 on 2/17/2017.
 */
public class CatPartnerDTO extends BaseDTO {
    private String id;
    private String name;
    private String code;
    private String status;
    private String custId;
    private String address;
    private String telNumber;



    public CatPartnerDTO(String id, String code, String name,String address,String telNumber, String status, String custId ) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.custId = custId;
        this.address = address;
        this.code = code;
        this.telNumber = telNumber;
    }

    public CatPartnerDTO() {
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    @Override
    public BaseModel toModel() {
        return new CatPartner(!StringUtils.validString(id) ? null:Long.valueOf(id),code,name,address,telNumber,status,!StringUtils.validString(custId) ? null:Long.valueOf(custId));
    }

    @Override
    public String toString() {
        return "CatPartnerDTO{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", status='" + status + '\'' +
                ", custId='" + custId + '\'' +
                '}';
    }
}