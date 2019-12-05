package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.SysStatisticTopGoodsDTO;
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
 * Created by duyot on 5/19/2017.
 */
@Entity
@Table(name = "SYS_STATISTIC_TOP_GOODS")
public class SysStatisticTopGoods extends BaseModel {
    private Long id;
    private Long custId;
    private String statisticInfo;
    private Date createdDate;
    private String type;

    public SysStatisticTopGoods(long id, long custId, String statisticInfo, Date createdDate, String type) {
        this.id = id;
        this.custId = custId;
        this.statisticInfo = statisticInfo;
        this.createdDate = createdDate;
        this.type = type;
    }

    public SysStatisticTopGoods() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SYS_STATISTIC_TOP_GOODS")
    @SequenceGenerator(
            name = "SEQ_SYS_STATISTIC_TOP_GOODS",
            sequenceName = "SEQ_SYS_STATISTIC_TOP_GOODS",
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

    @Column(name = "STATISTIC_INFO")
    public String getStatisticInfo() {
        return statisticInfo;
    }

    public void setStatisticInfo(String statisticInfo) {
        this.statisticInfo = statisticInfo;
    }

    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public BaseDTO toDTO() {
        return new SysStatisticTopGoodsDTO(id == null ? "" : id + "", custId == null ? "" : custId + "", statisticInfo,
                createdDate == null ? "" : DateTimeUtils.convertDateTimeToString(createdDate), type
        );
    }
}
