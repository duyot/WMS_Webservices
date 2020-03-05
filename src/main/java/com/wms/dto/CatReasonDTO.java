package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.persistents.model.CatReason;
import com.wms.utils.StringUtils;

/**
 * Created by doanlv4 on 2/17/2017.
 */
public class CatReasonDTO extends BaseDTO {
    private String id;
    private String name;
    private String status;
    private String custId;
    private String type;

    public CatReasonDTO(String id, String name, String status, String custId, String type) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.custId = custId;
        this.type = type;
    }

    public CatReasonDTO() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public BaseModel toModel() {
        return new CatReason(!StringUtils.validString(id) ? null : Long.valueOf(id), name,
                !StringUtils.validString(status) ? 0 : Byte.parseByte(status), !StringUtils.validString(custId) ? null : Long.valueOf(custId), !StringUtils.validString(type) ? null : Long.valueOf(type));
    }

    @Override
    public String toString() {
        return "CatReasonDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", custId='" + custId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}