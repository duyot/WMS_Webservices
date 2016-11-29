package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.RoleDTO;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by duyot on 11/1/2016.
 */
@Entity
@Table(name = "AUTHEN_ROLES")
@javax.persistence.SequenceGenerator(
        name="sequence",
        sequenceName="SEQ_ROLES"
)
public class Role extends BaseModel{
    private Long id;
    private String roleCode;
    private String roleName;
    private Date createDate;
    private String status;

    public Role(Long id, String roleCode, String roleName, Date createDate, String status) {
        this.id = id;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.createDate = createDate;
        this.status = status;
    }

    public Role() {
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

    @Column(name = "ROLE_CODE")
    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Column(name = "ROLE_NAME")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public BaseDTO toDTO() {
        return new RoleDTO(id==null?"":id+"",roleName,roleName,createDate == null?"":createDate.toString(),status);
    }
}
