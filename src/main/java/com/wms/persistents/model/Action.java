package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.ActionDTO;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by duyot on 11/2/2016.
 */
@Entity
@Table(name = "AUTHEN_ACTIONS")
@javax.persistence.SequenceGenerator(
        name="sequence",
        sequenceName="SEQ_ACTIONS"
)
public class Action extends BaseModel {
    private Long id;
    private String actionName;
    private String actionCode;
    private String parentActionId;
    private String url;
    private String status;
    private Date createDate;
    private String createUser;
    private String levels;
    private String orders;
    private String imgClass;

    public Action(Long id, String actionName, String actionCode,
                  String parentActionId, String url, String status,
                  Date createDate, String createUser, String levels,
                  String orders,String imgClass) {
        this.id = id;
        this.actionName = actionName;
        this.actionCode = actionCode;
        this.parentActionId = parentActionId;
        this.url = url;
        this.status = status;
        this.createDate = createDate;
        this.createUser = createUser;
        this.levels = levels;
        this.orders = orders;
        this.imgClass = imgClass;
    }

    public Action() {
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

    @Column(name = "ACTION_NAME")
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    @Column(name = "ACTION_CODE")
    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    @Column(name = "PARENT_ACTION_ID")
    public String getParentActionId() {
        return parentActionId;
    }

    public void setParentActionId(String parentActionId) {
        this.parentActionId = parentActionId;
    }

    @Column(name = "URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "CREATE_USER")
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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
    public BaseDTO toDTO() {
        return new ActionDTO(id==null?"":id+"",actionName,actionCode,parentActionId,url,status,createDate==null?"":createDate.toString(),createUser,levels,orders,imgClass);
    }
}

