package com.wms.dto;


import com.wms.base.BaseDTO;
import com.wms.persistents.model.AdminUser;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 8/30/2016.
 */
public class AdminUserDTO extends BaseDTO {
    private String userId;
    private String username;
    private String password;
    private String status;
    private String createDate;

    public AdminUserDTO() {
    }

    public AdminUserDTO(String userId, String username, String password, String status, String createDate) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.status = status;
        this.createDate = createDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public AdminUser toModel() {
        try {
            return new AdminUser(!StringUtils.validString(userId) ? null:Long.valueOf(userId),
                                 username,password,status,
                                 !StringUtils.validString(createDate) ? null: DateTimeUtils.convertStringToDate(createDate));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
