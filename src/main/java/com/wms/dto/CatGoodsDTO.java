
package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.persistents.model.CatGoods;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 12/9/2016.
 */
public class CatGoodsDTO extends BaseDTO{
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
    private String hight;
    private String weight;
    private String volume;

    public CatGoodsDTO() {
    }

    public CatGoodsDTO(String id, String code, String name, String status, String createdDate, String custId, String unitType, String goodsGroupId, String isSerial, String description, String inPrice, String outPrice, String length, String width, String hight, String weight, String volume) {
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
        this.hight = hight;
        this.weight = weight;
        this.volume = volume;
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

    public String getHight() {
        return hight;
    }

    public void setHight(String hight) {
        this.hight = hight;
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

    @Override
    public BaseModel toModel() {
        return new CatGoods(
                !StringUtils.validString(id) ? null:Long.valueOf(id),
                !StringUtils.validString(custId) ? null:Long.valueOf(custId),
                !StringUtils.validString(goodsGroupId) ? null:Long.valueOf(goodsGroupId),
                code, name, !StringUtils.validString(status) ? null:Byte.parseByte(status),
                !StringUtils.validString(createdDate) ? null: DateTimeUtils.convertStringToDate(createdDate),
                 unitType,
                !StringUtils.validString(isSerial) ? null:Byte.parseByte(isSerial), description,
                !StringUtils.validString(inPrice) ? null:Double.valueOf(inPrice), !StringUtils.validString(outPrice) ? null:Double.valueOf(outPrice),
                !StringUtils.validString(length) ? null:Double.valueOf(length),
                !StringUtils.validString(width) ? null:Double.valueOf(width),
                !StringUtils.validString(hight) ? null:Double.valueOf(hight),
                !StringUtils.validString(weight) ? null:Double.valueOf(weight),
                !StringUtils.validString(volume) ? null:Double.valueOf(volume)
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
                ", hight='" + hight + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}

