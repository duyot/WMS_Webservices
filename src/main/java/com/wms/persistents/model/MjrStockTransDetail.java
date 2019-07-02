package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.MjrStockTransDetailDTO;

import javax.persistence.*;

/**
 * Created by duyot on 12/29/2016.
 */
@Entity
@Table(name = "MJR_STOCK_TRANS_DETAIL")
public class MjrStockTransDetail extends BaseModel{
    private Long id;
    private Long stockTransId;
    private Long goodsId;
    private String goodsCode;
    private String goodsState;
    private Long isSerial;
    private Float amount;
    private String serial;
    private Float inputPrice;
    private Float outputPrice;
    private String cellCode;
    private String unitName;
    private Float totalMoney;
    private Long partnerId;
    private Float volume;
    private Float weight;

    public MjrStockTransDetail() {
    }

    public MjrStockTransDetail(Long id, Long stockTransId, Long goodsId, String goodsCode, String goodsState, Long isSerial, Float amount, String serial, Float inputPrice, Float outputPrice, String cellCode, String unitName, Float totalMoney, Long partnerId, Float volume, Float weight) {
        this.id = id;
        this.stockTransId = stockTransId;
        this.goodsId = goodsId;
        this.goodsCode = goodsCode;
        this.goodsState = goodsState;
        this.isSerial = isSerial;
        this.amount = amount;
        this.serial = serial;
        this.inputPrice = inputPrice;
        this.outputPrice = outputPrice;
        this.cellCode = cellCode;
        this.unitName = unitName;
        this.totalMoney = totalMoney;
        this.partnerId = partnerId;
        this.volume = volume;
        this.weight = weight;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_MJR_STOCK_TRANS_DETAIL")
    @SequenceGenerator(
            name="SEQ_MJR_STOCK_TRANS_DETAIL",
            sequenceName="SEQ_MJR_STOCK_TRANS_DETAIL",
            allocationSize = 1,
            initialValue= 1000
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STOCK_TRANS_ID",nullable = false)
    public Long getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(Long stockTransId) {
        this.stockTransId = stockTransId;
    }

    @Column(name = "GOODS_ID",nullable = false)
    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    @Column(name = "GOODS_CODE")
    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    @Column(name = "GOODS_STATE")
    public String getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    @Column(name = "IS_SERIAL")
    public Long getIsSerial() {
        return isSerial;
    }

    public void setIsSerial(Long isSerial) {
        this.isSerial = isSerial;
    }

    @Column(name = "AMOUNT")
    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    @Column(name = "SERIAL")
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @Column(name = "INPUT_PRICE")
    public Float getInputPrice() {
        return inputPrice;
    }

    public void setInputPrice(Float inputPrice) {
        this.inputPrice = inputPrice;
    }

    @Column(name = "OUTPUT_PRICE")
    public Float getOutputPrice() {
        return outputPrice;
    }

    public void setOutputPrice(Float outputPrice) {
        this.outputPrice = outputPrice;
    }

    @Column(name = "CELL_CODE")
    public String getCellCode() {
        return cellCode;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
    }
    @Column(name = "UNIT_NAME")
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    @Column(name = "TOTAL_MONEY")
    public Float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Float totalMoney) {
        this.totalMoney = totalMoney;
    }

    @Column(name = "PARTNER_ID",nullable = false)
    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    @Column(name = "VOLUME")
    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    @Column(name = "WEIGHT")
    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    @Override
    public MjrStockTransDetailDTO toDTO() {
        return new MjrStockTransDetailDTO(id==null?"":id+"",stockTransId==null?"":stockTransId+"",goodsId==null?"":goodsId+"",
                goodsCode,goodsState,isSerial==null?"":isSerial+"",amount==null?"":amount+"",serial,inputPrice==null?"":inputPrice+"",outputPrice==null?"":outputPrice+"",
                cellCode, partnerId==null?"":partnerId+"",
                volume==null?"":volume+"", weight==null?"":weight+""
                );
    }
}
