package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.RevenueHistoryDTO;
import com.wms.dto.RevenueTotalDTO;
import com.wms.utils.DateTimeUtils;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "REVENUE_HISTORY")
public class RevenueHistory extends BaseModel{
    private Long id;
    private Long custId;
    private Double amount;
    private Double vat;
    private Double charge;
    private Long stockTransId;
    private String stockTransCode;
    private String description;
    private byte type;
    private Date createdDate;
    private String createdUser;

    public RevenueHistory(Long id, Long custId, Double amount, Double vat, Double charge, Long stockTransId, String stockTransCode, String description, byte type, Date createdDate, String createdUser) {
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

    public RevenueHistory() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVENUE_HISTORY")
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
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
    @Column(name = "CREATED_DATE")
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

    @Override
    public BaseDTO toDTO() {
        return new RevenueHistoryDTO(
                id == null ? "" : id + "",
                custId == null ? "" : custId + "",
                amount == null ? "" : amount + "",
                vat == null ? "" : vat + "",
                charge == null ? "" : charge + "",
                stockTransId == null ? "" : stockTransId + "",
                stockTransCode, description, type + "",
                createdDate == null ? "" : DateTimeUtils.convertDateTimeToString(createdDate),
                createdUser
        );
    }
}
