package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.CatPartnerDTO;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

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
    private String status;
    private String custId;
    private String address;

    public CatPartner(Long id,String code, String name, String status, String custId, String address) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
        this.custId = custId;
        this.address = address;
    }

    public CatPartner() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CAT_PARTNER")
    @SequenceGenerator(
            name="SEQ_CAT_PARTNER",
            sequenceName="SEQ_CAT_PARTNER",
            allocationSize = 1,
            initialValue= 1000
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
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "CUST_ID")
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public BaseDTO toDTO() {
        return new CatPartnerDTO(id==null?"":id+"",code,name,status,custId, address);
    }
}
