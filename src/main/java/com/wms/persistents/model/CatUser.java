package com.wms.persistents.model;


import com.wms.base.BaseModel;
import com.wms.dto.CatUserDTO;
import com.wms.utils.DateTimeUtils;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

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
    private byte status;
    private Date createdDate;
    private String imgUrl;
    private String roleName;
    private String logReason;
    //
    private byte block;
    private Long roleId;
    private Long partnerPermission;
    private Long stockPermission;


    public CatUser(Long id, Long deptId, Long custId, String code, String name, String password, Date birthDate, String email, String telNumber, byte status, Date createdDate, String imgUrl, String roleName, String logReason, Long roleId, byte block, Long partnerPermission, Long stockPermission) {
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
        this.logReason = logReason;
        this.block = block;
        this.roleId = roleId;
        this.partnerPermission = partnerPermission;
        this.stockPermission = stockPermission;
    }

    public CatUser() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CAT_USER")
    @SequenceGenerator(
            name = "SEQ_CAT_USER",
            sequenceName = "SEQ_CAT_USER",
            allocationSize = 1,
            initialValue = 1000
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

    @Column(name = "CUST_ID", nullable = false)
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
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Column(name = "CREATED_DATE", insertable = false)
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


    @Column(name = "LOG_REASON")
    public String getLogReason() {
        return logReason;
    }

    public void setLogReason(String logReason) {
        this.logReason = logReason;
    }

    @Column(name = "BLOCK")
    public byte getBlock() {
        return block;
    }

    public void setBlock(byte block) {
        this.block = block;
    }

    @Column(name = "ROLE_ID")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Column(name = "PARTNER_PERMISSION")
    public Long getPartnerPermission() {
        return partnerPermission;
    }

    public void setPartnerPermission(Long partnerPermission) {
        this.partnerPermission = partnerPermission;
    }

    @Column(name = "STOCK_PERMISSION")
    public Long getStockPermission() {
        return stockPermission;
    }

    public void setStockPermission(Long stockPermission) {
        this.stockPermission = stockPermission;
    }

    @Override
    public CatUserDTO toDTO() {
        return new CatUserDTO(id == null ? "" : id + "", deptId == null ? "" : deptId + "", custId == null ? "" : custId + "",
                code, name, password, birthDate == null ? "" : DateTimeUtils.convertDateTimeToString(birthDate),
                email, telNumber, status + "", createdDate == null ? "" : DateTimeUtils.convertDateTimeToString(createdDate),
                imgUrl, roleName, logReason, roleId == null ? "" : roleId + "", block + "", partnerPermission == null ? "" : partnerPermission + "", stockPermission == null ? "" : stockPermission + ""
        );
    }
}
