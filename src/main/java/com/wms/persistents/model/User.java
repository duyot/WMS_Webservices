package com.wms.persistents.model;


import com.wms.base.BaseModel;
import com.wms.dto.UserDTO;
import com.wms.utils.BundleUtils;
import com.wms.utils.DataUtil;
import com.wms.utils.DateTimeUtils;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by duyot on 8/24/2016.
 */
@Entity
@DynamicUpdate
@Table(name = "AUTHEN_USERS")
public class User extends BaseModel {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String status;
    private Date createDate;
    private String  imgUrl;
    private String  roleName;
    private String  roleId;

    public User(Long userId, String username, String password, String status,
                Date createDate, String email, String imgUrl,
                String roleName,String roleId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.status = status;
        this.createDate = createDate;
        this.email = email;
        this.imgUrl = imgUrl;
        this.roleName = roleName;
        this.roleId = roleId;
    }

    public User() {
    }

    @Id
    @Column(name = "USER_ID", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_USERS")
    @SequenceGenerator(
            name="SEQ_USERS",
            sequenceName="SEQ_USERS",
            allocationSize = 1,
            initialValue= 1000
    )
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "USER_NAME", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "CREATED_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "IMG_URL")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Column(name = "ROLE_NAME")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Column(name = "ROLE_ID")
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public UserDTO toDTO() {
//        if(!DataUtil.isStringNullOrEmpty(status)){
//            status = status.equals("1")? "Hiệu lực": "Hết hiệu lực";
//        }

        return new UserDTO(userId==null?"":userId+"",username,password,status,createDate==null?"":DateTimeUtils.convertDateTimeToString(createDate),email,imgUrl,roleName,roleId);
    }
}
