package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.SysMailReportDTO;
import com.wms.utils.DateTimeUtils;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by duyot on 5/17/2017.
 */
@Entity
@Table(name = "SYS_MAIL_REPORT")
public class SysMailReport extends BaseModel {
    private Long id;
    private Long custId;
    private Long totalImportMoney;
    private Long totalExportMoney;
    private Long totalMoney;
    private Date createdDate;

    public SysMailReport() {
    }

    public SysMailReport(Long id, Long custId, Long totalImportMoney, Long totalExportMoney, Long totalMoney, Date createdDate) {
        this.id = id;
        this.custId = custId;
        this.totalImportMoney = totalImportMoney;
        this.totalExportMoney = totalExportMoney;
        this.totalMoney = totalMoney;
        this.createdDate = createdDate;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SYS_MAIL_REPORT")
    @SequenceGenerator(
            name = "SEQ_SYS_MAIL_REPORT",
            sequenceName = "SEQ_SYS_MAIL_REPORT",
            allocationSize = 1,
            initialValue = 1000
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CUST_ID")
    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    @Column(name = "TOTAL_IMPORT_MONEY")
    public Long getTotalImportMoney() {
        return totalImportMoney;
    }

    public void setTotalImportMoney(Long totalImportMoney) {
        this.totalImportMoney = totalImportMoney;
    }

    @Column(name = "TOTAL_EXPORT_MONEY")
    public Long getTotalExportMoney() {
        return totalExportMoney;
    }

    public void setTotalExportMoney(Long totalExportMoney) {
        this.totalExportMoney = totalExportMoney;
    }

    @Column(name = "TOTAL_MONEY")
    public Long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }

    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public BaseDTO toDTO() {
        return new SysMailReportDTO(id == null ? "" : id + "", custId == null ? "" : custId + "",
                totalImportMoney == null ? "" : totalImportMoney + "", totalExportMoney == null ? "" : totalExportMoney + "",
                totalMoney == null ? "" : totalMoney + "", createdDate == null ? "" : DateTimeUtils.convertDateTimeToString(createdDate));
    }
}
