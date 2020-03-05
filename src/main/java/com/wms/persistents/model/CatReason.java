package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.CatReasonDTO;
import javax.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Created by doanlv4 on 2/17/2017.
 */
@Entity
@DynamicUpdate
@Table(name = "CAT_REASON")
public class CatReason extends BaseModel {
    private Long id;
    private String name;
    private byte status;
    private Long custId;
    private Long type;

    public CatReason(Long id, String name, byte status, Long custId, Long type) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.custId = custId;
        this.type = type;
    }

    public CatReason() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CAT_REASON")
    @SequenceGenerator(
            name = "SEQ_CAT_REASON",
            sequenceName = "SEQ_CAT_REASON",
            allocationSize = 1,
            initialValue = 1000
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Column(name = "TYPE")
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Override
    public BaseDTO toDTO() {
        return new CatReasonDTO(id == null ? "" : id + "", name, status + "", custId == null ? "" : String.valueOf(custId), type == null ? "" : String.valueOf(type));
    }
}
