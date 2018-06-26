package com.wms.persistents.model;

import com.wms.base.BaseModel;
import com.wms.dto.SysRoleDTO;

import javax.persistence.*;

/**
 * Created by duyot on 11/1/2016.
 */
@Entity
@Table(name = "SYS_ROLE")
@javax.persistence.SequenceGenerator(
        name="sequence",
        sequenceName="SEQ_SYS_ROLE"
)
public class SysRole extends BaseModel{
    private Long id;
    private String code;
    private String name;
    private String status;
    private Long custId;
    private Long type;

    public SysRole(Long id, String code, String name, String status,Long custId ,Long type) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
        this.custId = custId;
        this.type = type;
    }

    public SysRole() {
    }

    @Id
    @GeneratedValue(generator = "sequence")
    @Column(name = "ID", unique = true, nullable = false)
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

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "STATUS",nullable = false)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Column(name = "CUST_ID",nullable = false )
    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }
    @Column(name = "TYPE",nullable = false )
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Override
    public SysRoleDTO toDTO() {
        return new SysRoleDTO(id==null?"":id+"",code, name, status,custId != null?String.valueOf(custId):"",type!=null ? type + "":"");
    }
}
