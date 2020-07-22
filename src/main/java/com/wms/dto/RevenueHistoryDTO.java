package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.persistents.model.RevenueHistory;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

public class RevenueHistoryDTO extends BaseDTO {
    private String id;
    private String custId;
    private String amount;
    private String vat;
    private String charge;
    private String stockTransId;
    private String stockTransCode;
    private String description;
    private String type;
    private String createdDate;
    private String createdUser;

    public RevenueHistoryDTO(String id, String custId, String amount, String vat, String charge, String stockTransId, String stockTransCode, String description, String type, String createdDate, String createdUser) {
        this.id = id;
        this.custId = custId;
        this.amount = amount;
        this.vat = vat;
        this.charge = charge;
        this.stockTransId = stockTransId;
        this.stockTransCode = stockTransCode;
        this.description = description;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(String stockTransId) {
        this.stockTransId = stockTransId;
    }

    public String getStockTransCode() {
        return stockTransCode;
    }

    public void setStockTransCode(String stockTransCode) {
        this.stockTransCode = stockTransCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public RevenueHistoryDTO() {
    }

    @Override
    public BaseModel toModel() {
        return new RevenueHistory(
                !StringUtils.validString(id) ? null : Long.valueOf(id),
                !StringUtils.validString(custId) ? null : Long.valueOf(custId),
                !StringUtils.validString(amount) ? null : Double.valueOf(amount),
                !StringUtils.validString(vat) ? null : Double.valueOf(vat),
                !StringUtils.validString(charge) ? null : Double.valueOf(charge),
                !StringUtils.validString(stockTransId) ? 0 : Long.parseLong(type),
                stockTransCode, description,
                !StringUtils.validString(type) ? 0 : Byte.parseByte(type),
                !StringUtils.validString(createdDate) ? null : DateTimeUtils.convertStringToDate(createdDate),
                createdUser
        );
    }
}
