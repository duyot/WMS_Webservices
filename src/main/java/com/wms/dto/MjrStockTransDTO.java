package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.persistents.model.MjrStockTrans;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 12/28/2016.
 */


public class MjrStockTransDTO extends BaseDTO {

    private String id;
    private String code;
    private String custId;
    private String stockId;
    private String contractNumber;
    private String invoiceNumber;
    private String type;
    private String status;
    private String createdDate;
    private String createdUser;
    private String transMoneyTotal;
    private String transMoneyDiscount;
    private String discountAmount;
    private String transMoneyRequire;
    private String transMoneyReceive;
    private String transMoneyResponse;
    private String description;
    private String partnerId;
    private String partnerName;

    //DoanLV 26/05/2018 bo sung them thong tin de in phieu xuat
    private String customerName;
    private String stockName;
    private String stockCode;
    private String partnerCode;
    private String partnerTelNumber;
    private String partnerAddress;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerTelNumber() {
        return partnerTelNumber;
    }

    public void setPartnerTelNumber(String partnerTelNumber) {
        this.partnerTelNumber = partnerTelNumber;
    }

    public String getPartnerAddress() {
        return partnerAddress;
    }

    public void setPartnerAddress(String partnerAddress) {
        this.partnerAddress = partnerAddress;
    }

    public MjrStockTransDTO() {
    }

    public MjrStockTransDTO(String id, String code, String custId, String stockId, String contractNumber, String invoiceNumber, String type, String status, String createdDate, String createdUser, String transMoneyTotal, String transMoneyDiscount, String discountAmount, String transMoneyRequire, String transMoneyReceive, String transMoneyResponse, String description, String partnerId, String partnerName) {
        this.id = id;
        this.code = code;
        this.custId = custId;
        this.stockId = stockId;
        this.contractNumber = contractNumber;
        this.invoiceNumber = invoiceNumber;
        this.type = type;
        this.status = status;
        this.createdDate = createdDate;
        this.createdUser = createdUser;
        this.transMoneyTotal = transMoneyTotal;
        this.transMoneyDiscount = transMoneyDiscount;
        this.discountAmount = discountAmount;
        this.transMoneyRequire = transMoneyRequire;
        this.transMoneyReceive = transMoneyReceive;
        this.transMoneyResponse = transMoneyResponse;
        this.description = description;
        this.partnerId = partnerId;
        this.partnerName = partnerName;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getTransMoneyTotal() {
        return transMoneyTotal;
    }

    public void setTransMoneyTotal(String transMoneyTotal) {
        this.transMoneyTotal = transMoneyTotal;
    }

    public String getTransMoneyDiscount() {
        return transMoneyDiscount;
    }

    public void setTransMoneyDiscount(String transMoneyDiscount) {
        this.transMoneyDiscount = transMoneyDiscount;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getTransMoneyRequire() {
        return transMoneyRequire;
    }

    public void setTransMoneyRequire(String transMoneyRequire) {
        this.transMoneyRequire = transMoneyRequire;
    }

    public String getTransMoneyReceive() {
        return transMoneyReceive;
    }

    public void setTransMoneyReceive(String transMoneyReceive) {
        this.transMoneyReceive = transMoneyReceive;
    }

    public String getTransMoneyResponse() {
        return transMoneyResponse;
    }

    public void setTransMoneyResponse(String transMoneyResponse) {
        this.transMoneyResponse = transMoneyResponse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public MjrStockTrans toModel() {
        return new MjrStockTrans(!StringUtils.validString(id) ? null:Long.valueOf(id),code,!StringUtils.validString(custId) ? null:Long.valueOf(custId),
                !StringUtils.validString(stockId) ? null:Long.valueOf(stockId),contractNumber, invoiceNumber,!StringUtils.validString(type) ? null:Long.valueOf(type),
                !StringUtils.validString(status) ? null:Long.valueOf(status),!StringUtils.validString(createdDate) ? null: DateTimeUtils.convertStringToDate(createdDate),
                createdUser,!StringUtils.validString(transMoneyTotal) ? null:Float.valueOf(transMoneyTotal),!StringUtils.validString(transMoneyDiscount) ? null:Float.valueOf(transMoneyDiscount),
                !StringUtils.validString(discountAmount) ? null:Float.valueOf(discountAmount),!StringUtils.validString(transMoneyRequire) ? null:Float.valueOf(transMoneyRequire),
                !StringUtils.validString(transMoneyReceive) ? null:Float.valueOf(transMoneyReceive),!StringUtils.validString(transMoneyResponse) ? null:Float.valueOf(transMoneyResponse),description, !StringUtils.validString(partnerId) ? null:Long.valueOf(partnerId), partnerName
                );
    }
}
