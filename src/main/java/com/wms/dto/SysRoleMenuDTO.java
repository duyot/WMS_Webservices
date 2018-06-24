package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 11/2/2016.
 */
public class SysRoleMenuDTO extends BaseDTO {
    private String id;
    private String roleCode;
    private String menuId;
    private String custId;

    public SysRoleMenuDTO(String id, String roleId, String menuId,String custId) {
        this.id = id;
        this.roleCode = roleId;
        this.menuId = menuId;
        this.custId = custId;
    }

    public SysRoleMenuDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String cusId) {
        this.custId = cusId;
    }

    @Override
    public BaseModel toModel() {
        return new com.wms.persistents.model.SysRoleMenu(!StringUtils.validString(id) ? null:Long.valueOf(id),
                roleCode,!StringUtils.validString(menuId) ? null:Long.valueOf(menuId),!StringUtils.validString(custId) ? null:Long.valueOf(custId)
                );
    }
}
