package com.wms.dto;


import com.wms.base.BaseDTO;
import com.wms.persistents.model.CatUser;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 8/30/2016.
 */
public class CatUserDTO extends BaseDTO {
    private String id;
    private String deptId;
    private String custId;
    private String code;
    private String name;
    private String password;
    private String birthDate;
    private String email;
    private String telNumber;
    private String status;
    private String createdDate;
    private String imgUrl;
    private String roleName;
    private String logReason;
    //
    private String roleId;
    private String block;
    private String partnerPermission;
    private String stockPermission;

    public CatUserDTO(String id, String deptId, String custId, String code, String name, String password, String birthDate, String email, String telNumber, String status, String createdDate, String imgUrl, String roleName, String logReason, String roleId, String block, String partnerPermission, String stockPermission) {
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
        this.roleId = roleId;
        this.block = block;
        this.partnerPermission = partnerPermission;
        this.stockPermission = stockPermission;
    }

    public String getPartnerPermission() {
        return partnerPermission;
    }

    public void setPartnerPermission(String partnerPermission) {
        this.partnerPermission = partnerPermission;
    }

    public CatUserDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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

    public String getLogReason() {
        return logReason;
    }

    public void setLogReason(String logReason) {
        this.logReason = logReason;
    }


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getStockPermission() {
        return stockPermission;
    }

    public void setStockPermission(String stockPermission) {
        this.stockPermission = stockPermission;
    }

    @Override
    public CatUser toModel() {
        return new CatUser(!StringUtils.validString(id) ? null : Long.valueOf(id), !StringUtils.validString(deptId) ? null : Long.valueOf(deptId), !StringUtils.validString(custId) ? null : Long.valueOf(custId),
                code, name, password, !StringUtils.validString(birthDate) ? null : DateTimeUtils.convertStringToDate(birthDate), email, telNumber,
                !StringUtils.validString(status) ? 0 : Byte.parseByte(status),
                !StringUtils.validString(createdDate) ? null : DateTimeUtils.convertStringToDate(createdDate), imgUrl, roleName, logReason,
                !StringUtils.validString(roleId) ? null : Long.valueOf(roleId), !StringUtils.validString(block) ? 0 : Byte.parseByte(block), !StringUtils.validString(partnerPermission) ? null : Long.valueOf(partnerPermission), !StringUtils.validString(stockPermission) ? null : Long.valueOf(stockPermission)
        );
    }

    @Override
    public String toString() {
        return "CatUserDTO{" +
                "id='" + id + '\'' +
                ", deptId='" + deptId + '\'' +
                ", custId='" + custId + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", email='" + email + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", status='" + status + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", roleName='" + roleName + '\'' +
                ", logReason='" + logReason + '\'' +
                ", roleId='" + roleId + '\'' +
                ", block='" + block + '\'' +
                ", partnerPermission='" + partnerPermission + '\'' +
                ", stockPermission='" + stockPermission + '\'' +
                '}';
    }
}
