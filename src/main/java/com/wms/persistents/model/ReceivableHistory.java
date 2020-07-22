package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.ReceivableHistoryDTO;
import com.wms.utils.DateTimeUtils;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "REVENUE_TOTAL")
public class ReceivableHistory extends BaseModel {
    private Long id;
    private Long custId;
    private Long partnerId;
    private Double amount;
    private byte type;
    private String description;
    private Date createdDate;
    private String createdUser;

    public ReceivableHistory(Long id, Long custId, Long partnerId, Double amount, byte type, String description, Date createdDate, String createdUser) {
        this.id = id;
        this.custId = custId;
        this.partnerId = partnerId;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.createdDate = createdDate;
        this.createdUser = createdUser;
    }

    public ReceivableHistory() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_RECEIVABLE_HISTORY")
    @SequenceGenerator(
            name = "seq_RECEIVABLE_HISTORY",
            sequenceName = "seq_RECEIVABLE_HISTORY",
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

    @Column(name = "AMOUNT")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public BaseDTO toDTO() {
        return new ReceivableHistoryDTO(
                id == null ? "" : id + "",
                custId == null ? "" : custId + "",
                partnerId == null ? "" : partnerId + "",
                amount == null ? "" : amount + "",
                type + "", description,
                createdDate == null ? "" : DateTimeUtils.convertDateTimeToString(createdDate),
                createdUser

        );
    }
}
