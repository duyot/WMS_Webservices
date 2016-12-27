package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.CustomerDTO;
import com.wms.utils.DateTimeUtils;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by duyot on 12/6/2016.
 */
@Entity
@Table(name = "CUSTOMER")
@DynamicUpdate
public class Customer extends BaseModel{
    private Long id;
    private String code;
    private String name;
    private String type;
    private String telNumber;
    private String email;
    private String bankName;
    private String bankAccountCode;
    private String address;
    private Long status;
    private Date createDate;

    public Customer(Long id, String code, String name, String type, String telNumber, String email, String bankName, String bankAccountCode, String address, Long status, Date createDate) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.type = type;
        this.telNumber = telNumber;
        this.email = email;
        this.bankName = bankName;
        this.bankAccountCode = bankAccountCode;
        this.address = address;
        this.status = status;
        this.createDate = createDate;
    }

    public Customer() {
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

    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public BaseDTO toDTO() {
        return new CustomerDTO(id==null?"":id+"",code,name,type,telNumber,email,bankName,bankAccountCode,address,status==null?"":status+"",createDate==null?"": DateTimeUtils.convertDateTimeToString(createDate));
    }
}
