package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.persistents.model.ReceivableHistory;
import com.wms.persistents.model.RevenueHistory;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;
import java.util.Date;

public class ReceivableHistoryDTO extends BaseDTO {
    private String id;
    private String custId;
    private String partnerId;
    private String amount;
    private String type;
    private String description;
    private String createdDate;
    private String createdUser;

    public ReceivableHistoryDTO(String id, String custId, String partnerId, String amount, String type, String description, String createdDate, String createdUser) {
        this.id = id;
        this.custId = custId;
        this.partnerId = partnerId;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.createdDate = createdDate;
        this.createdUser = createdUser;
    }

    public ReceivableHistoryDTO() {
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return new ReceivableHistory(
                !StringUtils.validString(id) ? null : Long.valueOf(id),
                !StringUtils.validString(custId) ? null : Long.valueOf(custId),
                !StringUtils.validString(partnerId) ? null : Long.valueOf(partnerId),
                !StringUtils.validString(amount) ? null : Double.valueOf(amount),
                !StringUtils.validString(type) ? 0 : Byte.parseByte(type), description,
                !StringUtils.validString(createdDate) ? null : DateTimeUtils.convertStringToDate(createdDate),
                createdUser
        );
    }
}
