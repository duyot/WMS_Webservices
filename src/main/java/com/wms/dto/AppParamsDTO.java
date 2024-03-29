package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.persistents.model.AppParams;
import com.wms.utils.StringUtils;

/**
 * Created by doanlv4 on 25/03/2017.
 */
public class AppParamsDTO extends BaseDTO {
    private String id;
    private String name;
    private String code;
    private String status;
    private String type;
    private String parOrder;


    public AppParamsDTO(String id, String code, String name, String status, String type, String parOrder) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.type = type;
        this.parOrder = parOrder;
        this.code = code;
    }

    public AppParamsDTO() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParOrder() {
        return parOrder;
    }

    public void setParOrder(String parOrder) {
        this.parOrder = parOrder;
    }

    @Override
    public BaseModel toModel() {
        return new AppParams(!StringUtils.validString(id) ? null : Long.valueOf(id), code, name, !StringUtils.validString(status) ? 0 : Byte.parseByte(status), type, parOrder);
    }

    @Override
    public String toString() {
        return "AppParamsDTO{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", parOrder='" + parOrder + '\'' +
                '}';
    }
}
