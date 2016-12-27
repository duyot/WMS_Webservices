package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.CatGoodsGroupDTO;
import com.wms.dto.GoodsDTO;
import com.wms.utils.DateTimeUtils;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by duyot on 12/9/2016.
 */
@Entity
@DynamicUpdate
@Table(name = "GOODS")
public class Goods  extends BaseModel {
    private Long id;
    private String code;
    private String name;
    private String status;
    private Date createDate;
    private Long custId;
    private String unitType;
    private Long goodsGroupId;
    private String serialType;
    private String description;
    private Double inPrice;
    private Double outPrice;
    private String brand;

    public Goods() {
    }

    public Goods(Long id, String code, String name, String status, Date createDate, Long custId, String unitType, Long goodsGroupId, String serialType, String description, Double inPrice, Double outPrice, String brand) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
        this.createDate = createDate;
        this.custId = custId;
        this.unitType = unitType;
        this.goodsGroupId = goodsGroupId;
        this.serialType = serialType;
        this.description = description;
        this.inPrice = inPrice;
        this.outPrice = outPrice;
        this.brand = brand;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GOODS")
    @SequenceGenerator(
            name="SEQ_GOODS",
            sequenceName="SEQ_GOODS",
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

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    @Column(name = "SERIAL_TYPE")
    public String getSerialType() {
        return serialType;
    }

    public void setSerialType(String serialType) {
        this.serialType = serialType;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "IN_PRICE")
    public Double getInPrice() {
        return inPrice;
    }

    public void setInPrice(Double inPrice) {
        this.inPrice = inPrice;
    }

    @Column(name = "OUT_PRICE")
    public Double getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(Double outPrice) {
        this.outPrice = outPrice;
    }

    @Column(name = "BRAND")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public BaseDTO toDTO() {
        return new  GoodsDTO(id==null?"":id+"",code,name,status.equals("1")?"Hiệu lực":"Hết hiệu lực",createDate==null?"": DateTimeUtils.convertDateTimeToString(createDate),custId==null?"":custId+"",unitType,goodsGroupId==null?"":goodsGroupId+"",serialType,description,inPrice==null?"":inPrice+"",outPrice==null?"":outPrice+"",brand);
    }

}
