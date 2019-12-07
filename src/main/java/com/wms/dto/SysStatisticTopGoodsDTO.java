package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.persistents.model.SysStatisticTopGoods;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 5/19/2017.
 */
public class SysStatisticTopGoodsDTO extends BaseDTO {
    private String id;
    private String custId;
    private String statisticInfo;
    private String createdDate;
    private String type;

    public SysStatisticTopGoodsDTO(String id, String custId, String statisticInfo, String createdDate, String type) {
        this.id = id;
        this.custId = custId;
        this.statisticInfo = statisticInfo;
        this.createdDate = createdDate;
        this.type = type;
    }

    public SysStatisticTopGoodsDTO() {
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

    public String getStatisticInfo() {
        return statisticInfo;
    }

    public void setStatisticInfo(String statisticInfo) {
        this.statisticInfo = statisticInfo;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public BaseModel toModel() {
        return new SysStatisticTopGoods(!StringUtils.validString(id) ? null : Long.valueOf(id),
                !StringUtils.validString(custId) ? null : Long.valueOf(custId), statisticInfo,
                !StringUtils.validString(createdDate) ? null : DateTimeUtils.convertStringToDate(createdDate),
                type);

    }
}
