package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.MjrStockTransDTO;
import com.wms.utils.DateTimeUtils;

import javax.persistence.*;
import java.util.Date;

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
    private Long status;
    private Date createdDate;
    private String createdUser;
    private Float transMoneyTotal;
    private Float transMoneyDiscount;
    private Float discountAmount;
    private Float transMoneyRequire;
    private Float transMoneyReceive;
    private Float transMoneyResponse;
    private String description;
    private Long partnerId;
    private String partnerName;

    public MjrStockTrans() {
    }

    public MjrStockTrans(Long id, String code, Long custId, Long stockId, String contractNumber, String invoicetNumber, Long type, Long status, Date createdDate, String createdUser, Float transMoneyTotal,
                         Float transMoneyDiscount, Float discountAmount, Float transMoneyRequire, Float transMoneyReceive, Float transMoneyResponse, String description, Long partnerId, String partnerName) {
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
        this.transMoneyReceive = transMoneyReceive;
        this.transMoneyResponse = transMoneyResponse;
        this.description = description;
        this.partnerId = partnerId;
        this.partnerName = partnerName;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_MJR_STOCK_TRANS")
    @SequenceGenerator(
            name="SEQ_MJR_STOCK_TRANS",
            sequenceName="SEQ_MJR_STOCK_TRANS",
            allocationSize = 1,
            initialValue= 1000
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE",nullable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "CUST_ID",nullable = false)
    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    @Column(name = "STOCK_ID",nullable = false)
    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    @Column(name = "PARTNER_ID")
    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
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
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Column(name = "CREATED_DATE" )
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

    @Column(name = "TRANS_MONEY_RECEIVE")
    public Float getTransMoneyReceive() {
        return transMoneyReceive;
    }

    public void setTransMoneyReceive(Float transMoneyReceive) {
        this.transMoneyReceive = transMoneyReceive;
    }

    @Column(name = "TRANS_MONEY_RESPONSE")
    public Float getTransMoneyResponse() {
        return transMoneyResponse;
    }

    public void setTransMoneyResponse(Float transMoneyResponse) {
        this.transMoneyResponse = transMoneyResponse;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "PARTNER_NAME")
    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    @Override
    public MjrStockTransDTO toDTO() {
        return new MjrStockTransDTO(id==null?"":id+"",code,custId==null?"":custId+"",stockId==null?"":stockId+"",
                contractNumber,invoicetNumber,type==null?"":type+"",status==null?"":status+"",createdDate==null?"": DateTimeUtils.convertDateTimeToString(createdDate),
                createdUser,transMoneyTotal==null?"":transMoneyTotal+"",transMoneyDiscount==null?"":transMoneyDiscount+"",discountAmount==null?"":discountAmount+"",
                transMoneyRequire==null?"":transMoneyRequire+"",transMoneyReceive==null?"":transMoneyReceive+"",transMoneyResponse==null?"":transMoneyResponse+"",description,
                partnerId==null?"":partnerId+"", partnerName
        );
    }
}
