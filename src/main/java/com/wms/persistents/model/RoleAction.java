package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.RoleActionDTO;

import javax.persistence.*;

/**
 * Created by duyot on 11/2/2016.
 */
@Entity
@Table(name = "AUTHEN_ROLE_ACTION")
@javax.persistence.SequenceGenerator(
        name="sequence",
        sequenceName="SEQ_ROLE_ACTION"
)
public class RoleAction extends BaseModel{
    private Long id;
    private Long roleId;
    private Long actionId;
    private String status;

    public RoleAction(Long id, Long roleId, Long actionId, String status) {
        this.id = id;
        this.roleId = roleId;
        this.actionId = actionId;
        this.status = status;
    }

    public RoleAction() {
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

    @Column(name = "ROLE_ID")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Column(name = "ACTION_ID")
    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
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
        return new RoleActionDTO(id==null?"":id+"",roleId==null?"":roleId+"",actionId==null?"":actionId+"",status);
    }
}
