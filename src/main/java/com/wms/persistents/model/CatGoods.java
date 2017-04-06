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
    private String status;
    private Date createdDate;
    private String unitType;
    private String isSerial;
    private String description;
    private Double inPrice;
    private Double outPrice;
    private String brand;

    public CatGoods() {
    }

    public CatGoods(Long id, String code, String name, String status, Date createdDate, Long custId, String unitType, Long goodsGroupId, String isSerial, String description, Double inPrice, Double outPrice) {
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
        this.brand = brand;
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
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
    public String getIsSerial() {
        return isSerial;
    }

    public void setIsSerial(String isSerial) {
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

    @Override
    public BaseDTO toDTO() {
<<<<<<< HEAD
        return new CatGoodsDTO(id==null?"":id+"",code,name,status.equals("1")?"Hiệu lực":"Hết hiệu lực", createdDate ==null?"": DateTimeUtils.convertDateTimeToString(createdDate),custId==null?"":custId+"",unitType,goodsGroupId==null?"":goodsGroupId+"", isSerial,description,inPrice==null?"":inPrice+"",outPrice==null?"":outPrice+"");
=======
        return new CatGoodsDTO(id==null?"":id+"",code,name,status, createdDate ==null?"": DateTimeUtils.convertDateTimeToString(createdDate),String.valueOf(custId),unitType,goodsGroupId==null?"":goodsGroupId+"", isSerial,description,inPrice==null?"":inPrice+"",outPrice==null?"":outPrice+"");
>>>>>>> db8d26891d94f6ed3225f7694b3419945bc8dac6
    }

}
