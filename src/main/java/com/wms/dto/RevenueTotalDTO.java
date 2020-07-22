package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.persistents.model.RevenueTotal;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

public class RevenueTotalDTO extends BaseDTO {
    private String id;
    private String custId;
    private String partnerId;
    private String revenueAmountTotal;
    private String receivableAmountTotal;
    private String type;
    private String createdDate;
    private String createdUser;

    public RevenueTotalDTO() {
    }

    public RevenueTotalDTO(String id, String custId, String partnerId, String revenueAmountTotal, String receivableAmountTotal, String type, String createdDate, String createdUser) {
        this.id = id;
        this.custId = custId;
        this.partnerId = partnerId;
        this.revenueAmountTotal = revenueAmountTotal;
        this.receivableAmountTotal = receivableAmountTotal;
        this.type = type;
        this.createdDate = createdDate;
        this.createdUser = createdUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getRevenueAmountTotal() {
        return revenueAmountTotal;
    }

    public void setRevenueAmountTotal(String revenueAmountTotal) {
        this.revenueAmountTotal = revenueAmountTotal;
    }

    public String getReceivableAmountTotal() {
        return receivableAmountTotal;
    }

    public void setReceivableAmountTotal(String receivableAmountTotal) {
        this.receivableAmountTotal = receivableAmountTotal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    @Override
    public BaseModel toModel() {
        return new RevenueTotal(
                !StringUtils.validString(id) ? null : Long.valueOf(id),
                !StringUtils.validString(custId) ? null : Long.valueOf(custId),
                !StringUtils.validString(partnerId) ? null : Long.valueOf(partnerId),
                !StringUtils.validString(revenueAmountTotal) ? null : Double.valueOf(revenueAmountTotal),
                !StringUtils.validString(receivableAmountTotal) ? null : Double.valueOf(receivableAmountTotal),
                !StringUtils.validString(type) ? 0 : Byte.parseByte(type),
                !StringUtils.validString(createdDate) ? null : DateTimeUtils.convertStringToDate(createdDate),
                createdUser
        );
    }
}
