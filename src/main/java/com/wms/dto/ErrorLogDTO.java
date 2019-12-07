package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.persistents.model.ErrorLog;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 12/23/2016.
 */
public class ErrorLogDTO extends BaseDTO {
    private String id;
    private String function;
    private String className;
    private String parameter;
    private String createDate;
    private String errorInfo;

    public ErrorLogDTO(String id, String function, String className, String parameter, String createDate, String errorInfo) {
        this.id = id;
        this.function = function;
        this.className = className;
        this.parameter = parameter;
        this.createDate = createDate;
        this.errorInfo = errorInfo;
    }

    public ErrorLogDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    @Override
    public ErrorLog toModel() {
        return new ErrorLog(!StringUtils.validString(id) ? null : Long.valueOf(id),
                function, className, parameter, !StringUtils.validString(createDate) ? null : DateTimeUtils.convertStringToDate(createDate), errorInfo);
    }
}
