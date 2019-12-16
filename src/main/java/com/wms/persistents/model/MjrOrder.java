package com.wms.persistents.model;

import com.wms.base.BaseModel;
import com.wms.dto.MjrOrderDTO;
import com.wms.utils.DateTimeUtils;
import java.util.Date;
import javax.persistence.*;

/**
 * Created by truongbx.
 */
@Entity
@Table(name = "MJR_ORDER")
public class MjrOrder extends BaseModel {
    private Long id;
    private String code;
    private Long custId;
    private Long stockId;
    private Long type;
    private Long exportMethod;
    private byte status;
    private Date createdDate;
    private String createdUser;
    private String description;
    private Long partnerId;
    private String partnerName;
    private String receiveName;
    private Long receiveId;

    public MjrOrder() {
    }

    public MjrOrder(Long id, String code, Long custId, Long stockId, Long type, Long exportMethod, byte status, Date createdDate, String createdUser, String description, Long partnerId, String partnerName, String receiveName, Long receiveId) {
        this.id = id;
        this.code = code;
        this.custId = custId;
        this.stockId = stockId;
        this.type = type;
        this.exportMethod = exportMethod;
        this.status = status;
        this.createdDate = createdDate;
        this.createdUser = createdUser;
        this.description = description;
        this.partnerId = partnerId;
        this.partnerName = partnerName;
        this.receiveName = receiveName;
        this.receiveId = receiveId;
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

    @Column(name = "TYPE")
    public Long getType() {
        return type;
    }

    @Column(name = "EXPORT_METHOD")
    public Long getExportMethod() {
        return exportMethod;
    }

    public void setExportMethod(Long exportMethod) {
        this.exportMethod = exportMethod;
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

    @Column(name = "CREATED_DATE", insertable = false, updatable = false)
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

    @Override
    public MjrOrderDTO toDTO() {
        return new MjrOrderDTO(id == null ? "" : id + "", code, custId == null ? "" : custId + "", stockId == null ? "" : stockId + "", type == null ? "" : type + "", exportMethod == null ? "" : exportMethod + "", status + "",
                createdDate == null ? "" : DateTimeUtils.convertDateTimeToString(createdDate), createdUser, description,
                partnerId == null ? "" : partnerId + "", partnerName, receiveName, receiveId == null ? "" : receiveId + "");
    }
}
