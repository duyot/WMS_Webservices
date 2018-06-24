package com.wms.persistents.model;

import com.wms.base.BaseModel;
import com.wms.dto.SysRoleMenuDTO;

import javax.persistence.*;

/**
 * Created by duyot on 11/2/2016.
 */
@Entity
@Table(name = "SYS_ROLE_MENU")
@javax.persistence.SequenceGenerator(
        name="sequence",
        sequenceName="SEQ_SYS_ROLE_MENU"
)
public class SysRoleMenu extends BaseModel{
    private Long id;
    private String roleCode;
    private Long menuId;
    private Long custId;

    public SysRoleMenu(Long id, String roleCode, Long actionId ,Long custId) {
        this.id = id;
        this.roleCode = roleCode;
        this.menuId = actionId;
        this.custId = custId;
    }

    public SysRoleMenu() {
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

    @Column(name = "MENU_ID")
    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
    @Column(name = "CUST_ID",nullable = false)
    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    @Override
    public SysRoleMenuDTO toDTO() {
        return new SysRoleMenuDTO(id==null?"":id+"", roleCode , menuId ==null?"": menuId +"",custId+"");
    }
}
