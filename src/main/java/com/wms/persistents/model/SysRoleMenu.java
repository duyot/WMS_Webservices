package com.wms.persistents.model;

import com.wms.base.BaseModel;
import com.wms.dto.SysRoleMenuDTO;

import javax.persistence.*;

/**
 * Created by duyot on 11/2/2016.
 */
@Entity
@Table(name = "SYS_ROLE_MENU")
public class SysRoleMenu extends BaseModel{
    private Long id;
    private Long menuId;
    private Long roleId;

    public SysRoleMenu(Long id, Long menuId, Long roleId) {
        this.id = id;
        this.menuId = menuId;
        this.roleId = roleId;
    }

    public SysRoleMenu() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_SYS_ROLE_MENU")
    @SequenceGenerator(
            name="SEQ_SYS_ROLE_MENU",
            sequenceName="SEQ_SYS_ROLE_MENU",
            allocationSize = 1,
            initialValue= 1000
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "MENU_ID")
    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    @Column(name = "ROLE_ID",nullable = false)
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }






    @Override
    public SysRoleMenuDTO toDTO() {
        return new SysRoleMenuDTO(id==null?"":id+"", menuId ==null?"": menuId +"", roleId ==null?"": roleId +"");
    }
}
