package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.persistents.model.CatGoods;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 12/9/2016.
 */
public class CatGoodsDTO extends BaseDTO {
    private String id;
    private String code;
    private String name;
    private String status;
    private String createdDate;
    private String custId;
    private String unitType;
    private String goodsGroupId;
    private String isSerial;
    private String description;
    private String inPrice;
    private String outPrice;
    private String length;
    private String width;
    private String high;
    private String weight;
    private String volume;
    private String amountStorageQuota;

    public CatGoodsDTO() {
    }

    public CatGoodsDTO(String id, String code, String name, String status, String createdDate, String custId, String unitType, String goodsGroupId, String isSerial, String description, String inPrice, String outPrice, String length, String width, String high, String weight, String volume, String amountStorageQuota) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
        this.createdDate = createdDate;
        this.custId = custId;
        this.unitType = unitType;
        this.goodsGroupId = goodsGroupId;
        this.isSerial = isSerial;
        this.description = description;
        this.inPrice = inPrice;
        this.outPrice = outPrice;
        this.length = length;
        this.width = width;
        this.high = high;
        this.weight = weight;
        this.volume = volume;
        this.amountStorageQuota = amountStorageQuota;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getGoodsGroupId() {
        return goodsGroupId;
    }

    public void setGoodsGroupId(String goodsGroupId) {
        this.goodsGroupId = goodsGroupId;
    }

    public String getIsSerial() {
        return isSerial;
    }

    public void setIsSerial(String isSerial) {
        this.isSerial = isSerial;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInPrice() {
        return inPrice;
    }

    public void setInPrice(String inPrice) {
        this.inPrice = inPrice;
    }

    public String getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(String outPrice) {
        this.outPrice = outPrice;
    }


    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getAmountStorageQuota() {
        return amountStorageQuota;
    }

    public void setAmountStorageQuota(String amountStorageQuota) {
        this.amountStorageQuota = amountStorageQuota;
    }

    @Override
    public BaseModel toModel() {
        return new CatGoods(
                !StringUtils.validString(id) ? null : Long.valueOf(id), code, name, !StringUtils.validString(status) ? null : Byte.parseByte(status),
                !StringUtils.validString(createdDate) ? null : DateTimeUtils.convertStringToDate(createdDate),
                Long.valueOf(custId), unitType, !StringUtils.validString(goodsGroupId) ? null : Long.valueOf(goodsGroupId),
                !StringUtils.validString(isSerial) ? null : Byte.parseByte(isSerial), description,
                !StringUtils.validString(inPrice) ? null : Double.valueOf(inPrice), !StringUtils.validString(outPrice) ? null : Double.valueOf(outPrice),
                !StringUtils.validString(length) ? null : Double.valueOf(length),
                !StringUtils.validString(width) ? null : Double.valueOf(width),
                !StringUtils.validString(high) ? null : Double.valueOf(high),
                !StringUtils.validString(weight) ? null : Double.valueOf(weight),
                !StringUtils.validString(volume) ? null : Double.valueOf(volume),
                !StringUtils.validString(amountStorageQuota) ? null : Double.valueOf(amountStorageQuota)
        );
    }

    @Override
    public String toString() {
        return "CatGoodsDTO{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", custId='" + custId + '\'' +
                ", unitType='" + unitType + '\'' +
                ", goodsGroupId='" + goodsGroupId + '\'' +
                ", isSerial='" + isSerial + '\'' +
                ", description='" + description + '\'' +
                ", inPrice='" + inPrice + '\'' +
                ", outPrice='" + outPrice + '\'' +
                ", length='" + length + '\'' +
                ", width='" + width + '\'' +
                ", high='" + high + '\'' +
                ", weight='" + weight + '\'' +
                ", volume='" + volume + '\'' +
                ", amountStorageQuota='" + amountStorageQuota + '\'' +
                '}';
    }
}

