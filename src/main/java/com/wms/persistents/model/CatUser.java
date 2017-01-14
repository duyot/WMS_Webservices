package com.wms.persistents.model;


import com.wms.base.BaseModel;
import com.wms.dto.CatUserDTO;
import com.wms.utils.DateTimeUtils;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by duyot on 8/24/2016.
 */
@Entity
@DynamicUpdate
@Table(name = "CAT_USER")
public class CatUser extends BaseModel {
    private Long id;
    private Long deptId;
    private Long custId;
    private String code;
    private String name;
    private String password;
    private Date birthDate;
    private String email;
    private String telNumber;
    private String status;
    private Date createdDate;
    private String  imgUrl;
    private String  roleName;
    private String  roleCode;
    private String  logReason;

    public CatUser(Long id, Long deptId, Long custId, String code, String name, String password, Date birthDate, String email, String telNumber, String status, Date createdDate, String imgUrl, String roleName, String roleCode, String logReason) {
        this.id = id;
        this.deptId = deptId;
        this.custId = custId;
        this.code = code;
        this.name = name;
        this.password = password;
        this.birthDate = birthDate;
        this.email = email;
        this.telNumber = telNumber;
        this.status = status;
        this.createdDate = createdDate;
        this.imgUrl = imgUrl;
        this.roleName = roleName;
        this.roleCode = roleCode;
        this.logReason = logReason;
    }

    public CatUser() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CAT_USER")
    @SequenceGenerator(
            name="SEQ_CAT_USER",
            sequenceName="SEQ_CAT_USER",
            allocationSize = 1,
            initialValue= 1000
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "DEPT_ID")
    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Column(name = "CUST_ID",nullable = false)
    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "BIRTH_DATE")
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "TEL_NUMBER")
    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    @Column(name = "ROLE_CODE")
    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Column(name = "LOG_REASON")
    public String getLogReason() {
        return logReason;
    }

    public void setLogReason(String logReason) {
        this.logReason = logReason;
    }

    @Override
    public CatUserDTO toDTO() {
        return new CatUserDTO(id==null?"":id+"",deptId==null?"":deptId+"",custId==null?"":custId+"",
                code,name,password,birthDate==null?"": DateTimeUtils.convertDateTimeToString(birthDate),
                email,telNumber,status,createdDate==null?"": DateTimeUtils.convertDateTimeToString(createdDate),
                imgUrl,roleName,roleCode,logReason
        );
    }
}
