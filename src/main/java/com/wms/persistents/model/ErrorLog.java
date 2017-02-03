package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.ErrorLogDTO;
import com.wms.utils.DateTimeUtils;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by duyot on 12/23/2016.
 */
@Entity
@Table(name = "ERROR_LOG")
@DynamicUpdate
public class ErrorLog extends BaseModel{
    private Long id;
    private String function;
    private String className;
    private String parameter;
    private Date createDate;
    private String errorInfo;

    public ErrorLog() {
    }

    public ErrorLog(Long id, String function, String className, String parameter, Date createDate, String errorInfo) {
        this.id = id;
        this.function = function;
        this.className = className;
        this.parameter = parameter;
        this.createDate = createDate;
        this.errorInfo = errorInfo;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ERROR_LOG")
    @SequenceGenerator(
            name="SEQ_ERROR_LOG",
            sequenceName="SEQ_ERROR_LOG",
            allocationSize = 1,
            initialValue= 1000
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "FUNCTION")
    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    @Column(name = "CLASS_NAME")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Column(name = "PARAMETER")
    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Column(name = "CREATED_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "ERROR_INFO")
    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    @Override
    public ErrorLogDTO toDTO() {
        return new ErrorLogDTO(id==null?"":id+"",function,className,parameter,createDate==null?"": DateTimeUtils.convertDateTimeToString(createDate),errorInfo);
    }
}
