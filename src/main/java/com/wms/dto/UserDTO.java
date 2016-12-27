package com.wms.dto;


import com.wms.base.BaseDTO;
import com.wms.persistents.model.User;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 8/30/2016.
 */
public class UserDTO extends BaseDTO {
    public String userId;
    public String username;
    public String password;
    public String status;
    public String createDate;
    public String email;
    public String imgUrl;
    public String roleName;
    private String  roleId;
    private String  customerId;
    private String  roleCode;

    public UserDTO() {
    }

    public UserDTO(String userId, String username, String password, String status,
                   String createDate, String email, String imgUrl,
                   String roleName,String  roleId,String customerId,String roleCode) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.status = status;
        this.createDate = createDate;
        this.email = email;
        this.imgUrl = imgUrl;
        this.roleName = roleName;
        this.roleId = roleId;
        this.customerId = customerId;
        this.roleCode = roleCode;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Override
    public com.wms.persistents.model.User toModel() {
        try {
            return new com.wms.persistents.model.User(!StringUtils.validString(userId) ? null:Long.valueOf(userId),
                                 username,password,status,
                                 !StringUtils.validString(createDate) ? null: DateTimeUtils.convertStringToDate(createDate),
                                email,imgUrl,roleName,roleId,customerId,roleCode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", createDate='" + createDate + '\'' +
                ", email='" + email + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleId='" + roleId + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
