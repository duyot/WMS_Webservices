package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.CatGoodsDTO;
import com.wms.utils.DateTimeUtils;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by duyot on 12/9/2016.
 */
@Entity
@DynamicUpdate
@Table(name = "CAT_GOODS")
public class CatGoods extends BaseModel {
    private Long id;
    private Long custId;
    private Long goodsGroupId;
    private String code;
    private String name;
    private byte status;
    private Date createdDate;
    private String unitType;
    private byte isSerial;
    private String description;
    private Double inPrice;
    private Double outPrice;
    private Double length;
    private Double width;
    private Double high;
    private Double weight;
    private Double volume;

    public CatGoods() {
    }

    public CatGoods(Long id, Long custId, Long goodsGroupId, String code, String name, byte status, Date createdDate, String unitType, byte isSerial, String description, Double inPrice, Double outPrice, Double length, Double width, Double high, Double weight, Double volume) {
        this.id = id;
        this.custId = custId;
        this.goodsGroupId = goodsGroupId;
        this.code = code;
        this.name = name;
        this.status = status;
        this.createdDate = createdDate;
        this.unitType = unitType;
        this.isSerial = isSerial;
        this.description = description;
        this.inPrice = inPrice;
        this.outPrice = outPrice;
        this.length = length;
        this.width = width;
        this.high = high;
        this.weight = weight;
        this.volume = volume;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CAT_GOODS")
    @SequenceGenerator(
            name="SEQ_CAT_GOODS",
            sequenceName="SEQ_CAT_GOODS",
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

    @Column(name = "STATUS")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "CUST_ID")
    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    @Column(name = "UNIT_TYPE")
    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    @Column(name = "GOODS_GROUP_ID")
    public Long getGoodsGroupId() {
        return goodsGroupId;
    }

    public void setGoodsGroupId(Long goodsGroupId) {
        this.goodsGroupId = goodsGroupId;
    }

    @Column(name = "IS_SERIAL")
    public byte getIsSerial() {
        return isSerial;
    }

    public void setIsSerial(byte isSerial) {
        this.isSerial = isSerial;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "INPUT_PRICE")
    public Double getInPrice() {
        return inPrice;
    }

    public void setInPrice(Double inPrice) {
        this.inPrice = inPrice;
    }

    @Column(name = "OUTPUT_PRICE")
    public Double getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(Double outPrice) {
        this.outPrice = outPrice;
    }

    @Column(name = "LENGTH")
    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    @Column(name = "WIDTH")
    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    @Column(name = "HIGHT")
    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    @Column(name = "WEIGHT")
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Column(name = "VOLUME")
    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    @Override
    public BaseDTO toDTO() {
        return new CatGoodsDTO(id==null?"":id+"",code,name,status+"",
                createdDate ==null?"": DateTimeUtils.convertDateTimeToString(createdDate),String.valueOf(custId),
                unitType,goodsGroupId==null?"":goodsGroupId+"", isSerial+"",description,inPrice==null?"":inPrice+"",
                outPrice==null?"":outPrice+"",
                length==null?"":length+"",
                width==null?"":width+"",
                high ==null?"": high +"",
                weight==null?"":weight+"",
                volume==null?"":volume+""
                );
    }
}
