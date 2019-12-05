package com.wms.persistents.model;

import com.wms.base.BaseModel;
import com.wms.dto.MjrStockTransDTO;
import com.wms.utils.DateTimeUtils;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by duyot on 12/28/2016.
 */
@Entity
@Table(name = "MJR_STOCK_TRANS")
public class MjrStockTrans extends BaseModel {
    private Long id;
    private String code;
    private Long custId;
    private Long stockId;
    private String contractNumber;
    private String invoicetNumber;
    private Long type;
    private byte status;
    private Date createdDate;
    private String createdUser;
    private Float transMoneyTotal;
    private Float transMoneyDiscount;
    private Float discountAmount;
    private Float transMoneyRequire;
    private String description;
    private Long partnerId;
    private String partnerName;

    //Khach hang nhan trong cac giao dich xuat
    private String receiveName;
    private Long receiveId;
    private String totalMoney;

    private String orderCode;
    private Long orderId;

    public MjrStockTrans() {
    }

    public MjrStockTrans(Long id, String code, Long custId, Long stockId, String contractNumber, String invoicetNumber, Long type, byte status, Date createdDate, String createdUser, Float transMoneyTotal,
                         Float transMoneyDiscount, Float discountAmount, Float transMoneyRequire, String description, Long partnerId, String partnerName, Long receiveId, String receiveName, Long orderId, String orderCode) {
        this.id = id;
        this.code = code;
        this.custId = custId;
        this.stockId = stockId;
        this.contractNumber = contractNumber;
        this.invoicetNumber = invoicetNumber;
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
    }


    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MJR_STOCK_TRANS")
    @SequenceGenerator(
            name = "SEQ_MJR_STOCK_TRANS",
            sequenceName = "SEQ_MJR_STOCK_TRANS",
            allocationSize = 1,
            initialValue = 1000
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE", nullable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "CUST_ID", nullable = false)
    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    @Column(name = "STOCK_ID", nullable = false)
    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    @Column(name = "CONTRACT_NUMBER")
    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    @Column(name = "INVOICE_NUMBER")
    public String getInvoicetNumber() {
        return invoicetNumber;
    }

    public void setInvoicetNumber(String invoicetNumber) {
        this.invoicetNumber = invoicetNumber;
    }

    @Column(name = "TYPE")
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Column(name = "STATUS")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Column(name = "CREATED_DATE", insertable = false)
    public Date getCreatedDate() {
        return createdDate;
    }


    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "CREATED_USER")
    public String getCreatedUser() {
        return createdUser;
    }


    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    @Column(name = "TRANS_MONEY_TOTAL")
    public Float getTransMoneyTotal() {
        return transMoneyTotal;
    }

    public void setTransMoneyTotal(Float transMoneyTotal) {
        this.transMoneyTotal = transMoneyTotal;
    }

    @Column(name = "TRANS_MONEY_DISCOUNT")
    public Float getTransMoneyDiscount() {
        return transMoneyDiscount;
    }

    public void setTransMoneyDiscount(Float transMoneyDiscount) {
        this.transMoneyDiscount = transMoneyDiscount;
    }

    @Column(name = "DISCOUNT_AMOUNT")
    public Float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Float discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Column(name = "TRANS_MONEY_REQUIRE")
    public Float getTransMoneyRequire() {
        return transMoneyRequire;
    }

    public void setTransMoneyRequire(Float transMoneyRequire) {
        this.transMoneyRequire = transMoneyRequire;
    }


    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "PARTNER_ID")
    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    @Column(name = "PARTNER_NAME")
    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    @Column(name = "RECEIVE_ID")
    public Long getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Long receiveId) {
        this.receiveId = receiveId;
    }

    @Column(name = "RECEIVE_NAME")
    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    @Column(name = "ORDER_ID")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Column(name = "ORDER_CODE")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Override
    public MjrStockTransDTO toDTO() {
        return new MjrStockTransDTO(id == null ? "" : id + "", code, custId == null ? "" : custId + "", stockId == null ? "" : stockId + "",
                contractNumber, invoicetNumber, type == null ? "" : type + "", status + "", createdDate == null ? "" : DateTimeUtils.convertDateTimeToString(createdDate),
                createdUser, transMoneyTotal == null ? "" : transMoneyTotal + "", transMoneyDiscount == null ? "" : transMoneyDiscount + "", discountAmount == null ? "" : discountAmount + "",
                transMoneyRequire == null ? "" : transMoneyRequire + "", description,
                partnerId == null ? "" : partnerId + "", partnerName, receiveId == null ? "" : receiveId + "", receiveName, orderId == null ? "" : orderId + "", orderCode
        );
    }
}
