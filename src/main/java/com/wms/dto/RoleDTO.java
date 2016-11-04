package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.persistents.model.Role;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 11/2/2016.
 */
public class RoleDTO extends BaseDTO{
    private String id;
    private String roleCode;
    private String roleName;
    private String createDate;
    private String status;

    public RoleDTO() {
    }

    public RoleDTO(String id, String roleCode, String roleName, String createDate, String status) {
        this.id = id;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.createDate = createDate;
        this.status = status;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Role toModel() {
        try {
            return new Role(!StringUtils.validString(id) ? null:Long.valueOf(id),roleCode,roleName,
                            !StringUtils.validString(createDate) ? null: DateTimeUtils.convertStringToDate(createDate),status);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
