package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.persistents.model.SysMenu;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 11/2/2016.
 */
public class SysMenuDTO extends BaseDTO {
    private String id;
    private String name;
    private String code;
    private String parentId;
    private String url;
    private String status;
    private String levels;
    private String orders;
    private String imgClass;

    public SysMenuDTO(String id, String actionName, String code, String parentActionId, String url, String status, String levels, String orders, String imgClass) {
        this.id = id;
        this.name = actionName;
        this.code = code;
        this.parentId = parentActionId;
        this.url = url;
        this.status = status;
        this.levels = levels;
        this.orders = orders;
        this.imgClass = imgClass;
    }

    public SysMenuDTO() {
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }


    public String getImgClass() {
        return imgClass;
    }

    public void setImgClass(String imgClass) {
        this.imgClass = imgClass;
    }

    @Override
    public BaseModel toModel() {
        try {
            return new SysMenu(!StringUtils.validString(id) ? null : Long.valueOf(id), name, code, parentId, url,
                    !StringUtils.validString(status) ? 0 : Byte.parseByte(status), levels, orders, imgClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
