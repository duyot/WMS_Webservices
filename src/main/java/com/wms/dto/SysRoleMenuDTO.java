package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 11/2/2016.
 */
public class SysRoleMenuDTO extends BaseDTO {
    private String id;
    private String menuId;
    private String roleId;

    public SysRoleMenuDTO() {
    }

    public SysRoleMenuDTO(String id, String menuId, String roleId) {
        this.id = id;
        this.menuId = menuId;
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public BaseModel toModel() {
        return new com.wms.persistents.model.SysRoleMenu(!StringUtils.validString(id) ? null:Long.valueOf(id),
                !StringUtils.validString(menuId) ? null:Long.valueOf(menuId),!StringUtils.validString(roleId) ? null:Long.valueOf(roleId)
                );
    }
}
