package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.persistents.model.CatCustomer;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 12/6/2016.
 */
public class CatCustomerDTO extends BaseDTO{
    private String id;
    private String code;
    private String name;
    private String status;
    private String email;
    private String telNumber;
    private String address;
    private String bankName;
    private String bankAccountCode;
    private String createdDate;
    private String mailReport;
    //DoanLV4 doi tac co bat buoc chon khong khi nhap/xuat kho
    private String partnerRequire;
    private String trial;
    private String exportMethod;

    public CatCustomerDTO(String id, String code, String name, String status, String email,
                          String telNumber, String address, String bankName, String bankAccountCode,
                          String createdDate, String mailReport , String partnerRequire,String trial, String exportMethod) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
        this.email = email;
        this.telNumber = telNumber;
        this.address = address;
        this.bankName = bankName;
        this.bankAccountCode = bankAccountCode;
        this.createdDate = createdDate;
        this.mailReport = mailReport;
        this.partnerRequire = partnerRequire;
        this.trial = trial;
        this.exportMethod = exportMethod;
    }

    public CatCustomerDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountCode() {
        return bankAccountCode;
    }

    public void setBankAccountCode(String bankAccountCode) {
        this.bankAccountCode = bankAccountCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getMailReport() {
        return mailReport;
    }

    public void setMailReport(String mailReport) {
        this.mailReport = mailReport;
    }

    public String getPartnerRequire() {
        return partnerRequire;
    }

    public void setPartnerRequire(String partnerRequire) {
        this.partnerRequire = partnerRequire;
    }

    public String getTrial() {
        return trial;
    }

    public void setTrial(String trial) {
        this.trial = trial;
    }

    public String getExportMethod() {
        return exportMethod;
    }

    public void setExportMethod(String exportMethod) {
        this.exportMethod = exportMethod;
    }

    @Override
    public CatCustomer toModel() {
        return new CatCustomer(!StringUtils.validString(id) ? null:Long.valueOf(id),code,name,!StringUtils.validString(status) ? 0:Byte.parseByte(status),email,telNumber,
                address,bankName,bankAccountCode,!StringUtils.validString(createdDate) ? null: DateTimeUtils.convertStringToDate(createdDate),
                mailReport==null?0:Integer.parseInt(mailReport), partnerRequire==null?0:Integer.parseInt(partnerRequire), trial==null?0:Integer.parseInt(trial),exportMethod==null?0:Integer.parseInt(exportMethod)
                );
    }

    @Override
    public String toString() {
        return "CatCustomerDTO{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", email='" + email + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccountCode='" + bankAccountCode + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", createDate='" + createdDate + '\'' +
                ", exportMethod='" + exportMethod + '\'' +
                '}';
    }
}
