package com.wms.persistents.model;

import com.wms.base.BaseModel;
import com.wms.dto.SysMenuDTO;

import javax.persistence.*;

/**
 * Created by duyot on 11/2/2016.
 */
@Entity
@Table(name = "SYS_MENU")
@javax.persistence.SequenceGenerator(
        name="sequence",
        sequenceName="SEQ_SYS_MENU"
)
public class SysMenu extends BaseModel {
    private Long id;
    private String name;
    private String code;
    private String parentId;
    private String url;
    private byte status;
    private String levels;
    private String orders;
    private String imgClass;

    public SysMenu(Long id, String name, String code,
                   String parentActionId, String url, byte status,
                   String levels,String orders, String imgClass) {

        this.id = id;
        this.name = name;
        this.code = code;
        this.parentId = parentActionId;
        this.url = url;
        this.status = status;
        this.levels = levels;
        this.orders = orders;
        this.imgClass = imgClass;
    }

    public SysMenu() {
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

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "PARENT_ID")
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Column(name = "URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "STATUS")
    public byte getStatus() {
        return status;
    }


    public void setStatus(byte status) {
        this.status = status;
    }

    @Column(name = "LEVELS")
    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    @Column(name = "ORDERS")
    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    @Column(name = "IMG_CLASS")
    public String getImgClass() {
        return imgClass;
    }

    public void setImgClass(String imgClass) {
        this.imgClass = imgClass;
    }

    @Override
    public SysMenuDTO toDTO() {
        return new SysMenuDTO(id==null?"":id+"", name, code, parentId,url,status+"",levels,orders,imgClass);
    }
}

