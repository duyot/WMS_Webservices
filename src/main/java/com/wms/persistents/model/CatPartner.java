package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.CatPartnerDTO;
import javax.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Created by doanlv4 on 2/17/2017.
 */
@Entity
@DynamicUpdate
@Table(name = "CAT_PARTNER")
public class CatPartner extends BaseModel {
    private Long id;
    private String code;
    private String name;
    private byte status;
    private Long custId;
    private String address;
    private String telNumber;
    private Long parentId;
    private Long userManagerId;

    public CatPartner(Long id, String code, String name, String address, String telNumber, byte status, Long custId, Long parentId, Long userManagerId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
        this.custId = custId;
        this.address = address;
        this.telNumber = telNumber;
        this.parentId = parentId;
        this.userManagerId = userManagerId;
    }

    public CatPartner() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CAT_PARTNER")
    @SequenceGenerator(
            name = "SEQ_CAT_PARTNER",
            sequenceName = "SEQ_CAT_PARTNER",
            allocationSize = 1,
            initialValue = 1000
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "STATUS")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Column(name = "CUST_ID")
    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "TEL_NUMBER")
    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    @Column(name = "PARENT_ID")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Column(name = "USER_MANAGER_ID")
    public Long getUserManagerId() {
        return userManagerId;
    }

    public void setUserManagerId(Long userManagerId) {
        this.userManagerId = userManagerId;
    }

    @Override
    public BaseDTO toDTO() {
        return new CatPartnerDTO(id == null ? "" : id + "", code, name, address, telNumber, status + "", custId == null ? "" : String.valueOf(custId), parentId == null ? "" : String.valueOf(parentId), userManagerId == null ? "" : String.valueOf(userManagerId));
    }
}
