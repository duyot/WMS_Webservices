package com.wms.persistents.model;


import com.wms.base.BaseModel;
import com.wms.dto.AdminUserDTO;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by duyot on 8/24/2016.
 */
@Entity
@Table(name = "ADMIN_USER")
@javax.persistence.SequenceGenerator(
        name="sequence",
        sequenceName="SEQ_ADMIN_USER"
)
public class AdminUser extends BaseModel {
    private Long userId;
    private String username;
    private String password;
    private String status;
    private Date createDate;


    public AdminUser(Long userId, String userName, String password, String status, Date createDate) {
        this.userId = userId;
        this.username = userName;
        this.password = password;
        this.status = status;
        this.createDate = createDate;
    }

    public AdminUser() {
    }

    @Id
    @GeneratedValue(generator = "sequence")
    @Column(name = "USER_ID", unique = true, nullable = false)
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

    @Column(name = "PASSWORD", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "STATUS", nullable = false)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "CREATED_DATE", nullable = false)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public AdminUserDTO toDTO() {
        return new AdminUserDTO(userId+"",username,password,status,createDate.toString());
    }
}
