package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.persistents.model.SysMailReport;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 5/17/2017.
 */
public class SysMailReportDTO extends BaseDTO {
    private String id;
    private String custId;
    private String totalImportMoney;
    private String totalExportMoney;
    private String totalMoney;
    private String createdDate;

    public SysMailReportDTO() {
    }

    public SysMailReportDTO(String id, String custId, String totalImportMoney, String totalExportMoney, String totalMoney, String createdDate) {
        this.id = id;
        this.custId = custId;
        this.totalImportMoney = totalImportMoney;
        this.totalExportMoney = totalExportMoney;
        this.totalMoney = totalMoney;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getTotalImportMoney() {
        return totalImportMoney;
    }

    public void setTotalImportMoney(String totalImportMoney) {
        this.totalImportMoney = totalImportMoney;
    }

    public String getTotalExportMoney() {
        return totalExportMoney;
    }

    public void setTotalExportMoney(String totalExportMoney) {
        this.totalExportMoney = totalExportMoney;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public BaseModel toModel() {
        try {
            return new SysMailReport(!StringUtils.validString(id) ? null : Long.valueOf(id),
                    !StringUtils.validString(custId) ? null : Long.valueOf(custId),
                    !StringUtils.validString(totalImportMoney) ? null : Long.valueOf(totalImportMoney),
                    !StringUtils.validString(totalExportMoney) ? null : Long.valueOf(totalExportMoney),
                    !StringUtils.validString(totalMoney) ? null : Long.valueOf(totalMoney),
                    !StringUtils.validString(createdDate) ? null : DateTimeUtils.convertStringToDate(createdDate)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
