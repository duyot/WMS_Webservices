package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.RevenueTotalDTO;
import com.wms.utils.DateTimeUtils;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "REVENUE_TOTAL")
public class RevenueTotal extends BaseModel {
    private Long id;
    private Long custId;
    private Long partnerId;
    private Double revenueAmountTotal;
    private Double receivableAmountTotal;
    private byte type;
    private Date createdDate;
    private String createdUser;

    public RevenueTotal() {
    }

    public RevenueTotal(Long id, Long custId, Long partnerId, Double revenueAmountTotal, Double receivableAmountTotal, byte type, Date createdDate, String createdUser) {
        this.id = id;
        this.custId = custId;
        this.partnerId = partnerId;
        this.revenueAmountTotal = revenueAmountTotal;
        this.receivableAmountTotal = receivableAmountTotal;
        this.type = type;
        this.createdDate = createdDate;
        this.createdUser = createdUser;
    }


    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REVENUE_TOTAL")
    @SequenceGenerator(
            name = "SEQ_REVENUE_TOTAL",
            sequenceName = "SEQ_REVENUE_TOTAL",
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
    @Column(name = "PARTNER_ID")
    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }
    @Column(name = "REVENUE_AMOUNT_TOTAL")
    public Double getRevenueAmountTotal() {
        return revenueAmountTotal;
    }

    public void setRevenueAmountTotal(Double revenueAmountTotal) {
        this.revenueAmountTotal = revenueAmountTotal;
    }
    @Column(name = "RECEIVABLE_AMOUNT_TOTAL")
    public Double getReceiveableAmountTotal() {
        return receivableAmountTotal;
    }

    public void setReceiveableAmountTotal(Double receiveableAmountTotal) {
        this.receivableAmountTotal = receiveableAmountTotal;
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
        return new RevenueTotalDTO(
                id == null ? "" : id + "",
                custId == null ? "" : custId + "",
                partnerId == null ? "" : partnerId + "",
                revenueAmountTotal == null ? "" : revenueAmountTotal + "",
                receivableAmountTotal == null ? "" : receivableAmountTotal + "",
                type + "",
                createdDate == null ? "" : DateTimeUtils.convertDateTimeToString(createdDate),
                createdUser
        );
    }
}
