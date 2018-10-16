package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.CatCustomerDTO;
import com.wms.utils.DateTimeUtils;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by duyot on 12/6/2016.
 */
@Entity
@Table(name = "CAT_CUSTOMER")
@DynamicUpdate
public class CatCustomer extends BaseModel{
    private Long id;
    private String code;
    private String name;
    private byte status;
    private String email;
    private String telNumber;
    private String address;
    private String bankName;
    private String bankAccountCode;
    private Date  createdDate;
    private int   mailReport;
    private int   partnerRequire;
    private int   trial;
    public CatCustomer(Long id, String code, String name, byte status, String email,
                       String telNumber, String address, String bankName, String bankAccountCode,
                       Date createdDate, int mailReport, int partnerRequire,int trial) {
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
        this.mailReport  = mailReport;
        this.partnerRequire = partnerRequire;
        this.trial=trial;
    }

    public CatCustomer() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CUSTOMER")
    @SequenceGenerator(
            name="SEQ_CUSTOMER",
            sequenceName="SEQ_CUSTOMER",
            allocationSize = 1,
            initialValue= 1000
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE",nullable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "NAME",nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "TEL_NUMBER")
    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "BANK_NAME")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name = "BANK_ACCOUNT_CODE")
    public String getBankAccountCode() {
        return bankAccountCode;
    }

    public void setBankAccountCode(String bankAccountCode) {
        this.bankAccountCode = bankAccountCode;
    }

    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "STATUS")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Column(name = "CREATED_DATE",insertable = false)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "MAIL_REPORT")
    public int getMailReport() {
        return mailReport;
    }

    public void setMailReport(int mailReport) {
        this.mailReport = mailReport;
    }

    @Column(name = "PARTNER_REQUIRE")
    public int getPartnerRequire() {
        return partnerRequire;
    }

    public void setPartnerRequire(int partnerRequire) {
        this.partnerRequire = partnerRequire;
    }
    @Column(name = "TRIAL")
    public int getTrial() {
        return trial;
    }

    public void setTrial(int trial) {
        this.trial = trial;
    }

    @Override
    public BaseDTO toDTO() {
        return new CatCustomerDTO(id==null?"":id+"",code,name,status+"",email,telNumber,address,bankName,
                bankAccountCode,createdDate==null?"": DateTimeUtils.convertDateTimeToString(createdDate),mailReport+"", partnerRequire+"",trial+"");
    }
}
