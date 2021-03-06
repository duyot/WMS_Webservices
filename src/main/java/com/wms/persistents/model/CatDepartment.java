package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.CatDepartmentDTO;
import javax.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Created by doanlv4 on 2/17/2017.
 */
@Entity
@DynamicUpdate
@Table(name = "CAT_DEPARTMENT")
public class CatDepartment extends BaseModel {
    private Long id;
    private String code;
    private String name;
    private byte status;
    private Long custId;
    private String path;
    private Long parentId;

    public CatDepartment(Long id, String code, String name, byte status, Long custId, String path, Long parentId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
        this.custId = custId;
        this.path = path;
        this.parentId = parentId;
    }

    public CatDepartment() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CAT_DEPARTMENT")
    @SequenceGenerator(
            name = "SEQ_CAT_DEPARTMENT",
            sequenceName = "SEQ_CAT_DEPARTMENT",
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

    @Column(name = "PATH")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column(name = "PARENT_ID")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public BaseDTO toDTO() {
        return new CatDepartmentDTO(id == null ? "" : id + "", code, name, status + "", custId + "", path, parentId + "");
    }
}

