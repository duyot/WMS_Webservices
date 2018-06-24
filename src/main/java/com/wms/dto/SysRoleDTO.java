package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.persistents.model.SysRole;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 11/2/2016.
 */
public class SysRoleDTO extends BaseDTO{
    private String id;
    private String code;
    private String name;
    private String status;
    private String custId;

    public SysRoleDTO() {
    }

    public SysRoleDTO(String id, String code, String name, String status,String cusId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
        this.custId = cusId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    @Override
    public SysRole toModel() {
        try {
            return new SysRole(!StringUtils.validString(id) ? null:Long.valueOf(id), code, name,status,!StringUtils.validString(custId)? null: Long.valueOf(custId));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
