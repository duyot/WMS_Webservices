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

    //Khach hang nhan trong cac giao dich xuat
    private String receiveName;
    private String receiveId;

    private String orderCode;
    private String orderId;

    private String exportMethod;
    private String reasonId;
    private String reasonName;
    private String deliverySenderInfo;
    private String deliveryReceiverInfo;
    private String deliveryDescription;
    private String deliveryStatus;
    private String userManagerId;

    public String getDeliverySenderInfo() {
        return deliverySenderInfo;
    }

    public void setDeliverySenderInfo(String deliverySenderInfo) {
        this.deliverySenderInfo = deliverySenderInfo;
    }

    public String getDeliveryReceiverInfo() {
        return deliveryReceiverInfo;
    }

    public void setDeliveryReceiverInfo(String deliveryReceiverInfo) {
        this.deliveryReceiverInfo = deliveryReceiverInfo;
    }

    public String getDeliveryDescription() {
        return deliveryDescription;
    }

    public void setDeliveryDescription(String deliveryDescription) {
        this.deliveryDescription = deliveryDescription;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    //
    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

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

    public String getExportMethod() {
        return exportMethod;
    }

    public void setExportMethod(String exportMethod) {
        this.exportMethod = exportMethod;
    }

    public MjrStockTransDTO() {
    }

    public MjrStockTransDTO(String id, String code, String custId, String stockId, String contractNumber, String invoiceNumber, String type, String status, String createdDate, String createdUser, String transMoneyTotal, String transMoneyDiscount, String discountAmount, String transMoneyRequire, String description, String partnerId, String partnerName, String receiveId, String receiveName, String orderId, String orderCode,String reasonId, String reasonName,
                            String deliverySenderInfo,String deliveryReceiverInfo, String deliveryDescription, String deliveryStatus, String userManagerId ) {
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
        this.description = description;
        this.partnerId = partnerId;
        this.partnerName = partnerName;
        this.receiveId = receiveId;
        this.receiveName = receiveName;
        this.orderId = orderId;
        this.orderCode = orderCode;
        this.reasonId = reasonId;
        this.reasonName = reasonName;
        this.deliverySenderInfo = deliverySenderInfo;
        this.deliveryReceiverInfo = deliveryReceiverInfo;
        this.deliveryDescription = deliveryDescription;
        this.deliveryStatus = deliveryStatus;
        this.userManagerId = userManagerId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserManagerId() {
        return userManagerId;
    }

    public void setUserManagerId(String userManagerId) {
        this.userManagerId = userManagerId;
    }

    @Override
    public MjrStockTrans toModel() {
        return new MjrStockTrans(!StringUtils.validString(id) ? null : Long.valueOf(id), code, !StringUtils.validString(custId) ? null : Long.valueOf(custId),
                !StringUtils.validString(stockId) ? null : Long.valueOf(stockId), contractNumber, invoiceNumber, !StringUtils.validString(type) ? null : Long.valueOf(type),
                !StringUtils.validString(status) ? 0 : Byte.parseByte(status), !StringUtils.validString(createdDate) ? null : DateTimeUtils.convertStringToDate(createdDate),
                createdUser, !StringUtils.validString(transMoneyTotal) ? null : Float.valueOf(transMoneyTotal), !StringUtils.validString(transMoneyDiscount) ? null : Float.valueOf(transMoneyDiscount),
                !StringUtils.validString(discountAmount) ? null : Float.valueOf(discountAmount), !StringUtils.validString(transMoneyRequire) ? null : Float.valueOf(transMoneyRequire),
                description, !StringUtils.validString(partnerId) ? null : Long.valueOf(partnerId), partnerName, !StringUtils.validString(receiveId) ? null : Long.valueOf(receiveId), receiveName
                , !StringUtils.validString(orderId) ? null : Long.valueOf(orderId), orderCode,!StringUtils.validString(reasonId) ? null : Long.valueOf(reasonId), reasonName,
                deliverySenderInfo, deliveryReceiverInfo, deliveryDescription, !StringUtils.validString(deliveryStatus) ? null : Long.valueOf(deliveryStatus), !StringUtils.validString(userManagerId) ? null : Long.valueOf(userManagerId)
        );
    }
}
