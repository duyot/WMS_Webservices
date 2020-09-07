package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.RevenueDTO;
import javax.persistence.*;

import com.wms.utils.Constants;
import com.wms.utils.DateTimeUtils;
import org.hibernate.annotations.DynamicUpdate;
import java.util.Date;


/**
 * Created by doanlv4 on 2/17/2017.
 */
@Entity
@DynamicUpdate
@Table(name = "REVENUE_HISTORY")
public class Revenue extends BaseModel {

    private Long id;
    private Long custId;
    private Long partnerId;
    private Double amount;
    private Double vat;
    private Double charge;
    private Long stockTransId;
    private String stockTransCode;
    private String description;
    private Long type;
    private String createdUser;
    private Date createdDate;
    private Long paymentStatus;
    private Double paymentAmount;
    private String paymentDescription;
    private Date paymentDate;

    public Revenue(Long id, Long custId, Long partnerId, Double amount, Double vat, Double charge, Long stockTransId,
                      String stockTransCode, String description, Long type, String createdUser, Date createdDate,
                   Long paymentStatus, Double paymentAmount, String paymentDescription, Date paymentDate
                   ) {
        this.id = id;
        this.custId = custId;
        this.partnerId = partnerId;
        this.amount = amount;
        this.vat = vat;
        this.charge = charge;
        this.stockTransId = stockTransId;
        this.stockTransCode = stockTransCode;
        this.description = description;
        this.type = type;
        this.createdUser = createdUser;
        this.createdDate = createdDate;
        this.paymentStatus = paymentStatus;
        this.paymentAmount = paymentAmount;
        this.paymentDescription = paymentDescription;
        this.paymentDate = paymentDate;
    }

    public Revenue() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REVENUE_HISTORY")
    @SequenceGenerator(
            name = "SEQ_REVENUE_HISTORY",
            sequenceName = "SEQ_REVENUE_HISTORY",
            allocationSize = 1,
            initialValue = 1000
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "PARTNER_ID")
    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    @Column(name = "CUST_ID")
    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    @Column(name = "AMOUNT")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    @Column(name = "VAT")
    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }
    @Column(name = "CHARGE")
    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }
    @Column(name = "STOCK_TRANS_ID")
    public Long getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(Long stockTransId) {
        this.stockTransId = stockTransId;
    }
    @Column(name = "STOCK_TRANS_CODE")
    public String getStockTransCode() {
        return stockTransCode;
    }

    public void setStockTransCode(String stockTransCode) {
        this.stockTransCode = stockTransCode;
    }
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column(name = "TYPE")
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
    @Column(name = "CREATED_USER")
    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }
    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "PAYMENT_STATUS")
    public Long getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Long paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    @Column(name = "PAYMENT_AMOUNT")
    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Column(name = "PAYMENT_DESCRIPTION")
    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    @Column(name = "PAYMENT_DATE")
    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public BaseDTO toDTO() {
        return new RevenueDTO(id == null ? "" : id + "",
                custId == null ? "" : String.valueOf(custId),
                partnerId == null ? "" : String.valueOf(partnerId),
                amount == null ? "" : String.valueOf(amount),
                vat == null ? "" : String.valueOf(vat),
                charge == null ? "" : String.valueOf(charge),
                stockTransId == null ? "" : String.valueOf(stockTransId),
                stockTransCode,description,
                type == null ? "" : String.valueOf(type),
                createdUser,
                createdDate == null ? "" : DateTimeUtils.convertDateTimeToString(createdDate, Constants.DATETIME_FORMAT.ddMMyyyy),
                paymentStatus == null ? "" : String.valueOf(paymentStatus),
                paymentAmount == null ? "" : String.valueOf(paymentAmount),
                paymentDescription,
                paymentDate == null ? "" : DateTimeUtils.convertDateTimeToString(paymentDate, Constants.DATETIME_FORMAT.ddMMyyyy)
                );
    }
}
