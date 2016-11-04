package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.persistents.model.RoleAction;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 11/2/2016.
 */
public class RoleActionDTO extends BaseDTO {
    private String id;
    private String roleId;
    private String actionId;
    private String status;

    public RoleActionDTO(String id, String roleId, String actionId, String status) {
        this.id = id;
        this.roleId = roleId;
        this.actionId = actionId;
        this.status = status;
    }

    public RoleActionDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public BaseModel toModel() {
        return new RoleAction(!StringUtils.validString(id) ? null:Long.valueOf(id),
                !StringUtils.validString(roleId) ? null:Long.valueOf(roleId),
                !StringUtils.validString(actionId) ? null:Long.valueOf(actionId),
                status);
    }
}
