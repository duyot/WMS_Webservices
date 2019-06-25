package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.MjrStockGoodsDTO;
import com.wms.dto.MjrStockGoodsSerialDTO;
import com.wms.utils.DateTimeUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by duyot on 1/3/2017.
 */
@Entity
@Table(name = "MJR_STOCK_GOODS_SERIAL")
public class MjrStockGoodsSerial extends BaseModel{
    private Long id;
    private Long custId;
    private Long stockId;
    private Long goodsId;
    private String goodsState;
    private String cellCode;
    private Float amount;
    private String serial;
    private Date importDate;
    private Date changeDate;
    private byte status;
    private Long partnerId;
    private Long importStockTransId;
    private Float inputPrice;
    private Float outputPrice;
    private Date exportDate;
    private Long exportStockTransId;

    private Float volume;
    private Float weight;

    public MjrStockGoodsSerial(Long id, Long custId, Long stockId, Long goodsId, String goodsState, String cellCode, Float amount, String serial, Date importDate, Date changeDate, byte status, Long partnerId, Long importStockTransId, Float inputPrice, Float outputPrice, Date exportDate, Long exportStockTransId, Float volume, Float weight) {
        this.id = id;
        this.custId = custId;
        this.stockId = stockId;
        this.goodsId = goodsId;
        this.goodsState = goodsState;
        this.cellCode = cellCode;
        this.amount = amount;
        this.serial = serial;
        this.importDate = importDate;
        this.changeDate = changeDate;
        this.status = status;
        this.partnerId = partnerId;
        this.importStockTransId = importStockTransId;
        this.inputPrice = inputPrice;
        this.outputPrice = outputPrice;
        this.exportDate = exportDate;
        this.exportStockTransId = exportStockTransId;
        this.volume = volume;
        this.weight = weight;
    }

    public MjrStockGoodsSerial() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_MJR_STOCK_GOODS_SERIAL")
    @SequenceGenerator(
            name="SEQ_MJR_STOCK_GOODS_SERIAL",
            sequenceName="SEQ_MJR_STOCK_GOODS_SERIAL",
            allocationSize = 1,
            initialValue= 1000
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CUST_ID",nullable = false)
    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    @Column(name = "STOCK_ID",nullable = false)
    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    @Column(name = "GOODS_ID",nullable = false)
    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    @Column(name = "GOODS_STATE",nullable = false)
    public String getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    @Column(name = "CELL_CODE")
    public String getCellCode() {
        return cellCode;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
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

    @Column(name = "IMPORT_DATE")
    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    @Column(name = "CHANGED_DATE")
    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    @Column(name = "STATUS")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Column(name = "PARTNER_ID")
    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    @Column(name = "IMPORT_STOCK_TRANS_ID")
    public Long getImportStockTransId() {
        return importStockTransId;
    }

    public void setImportStockTransId(Long importStockTransId) {
        this.importStockTransId = importStockTransId;
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

    @Column(name = "EXPORT_DATE")
    public Date getExportDate() {
        return exportDate;
    }

    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
    }

    @Column(name = "EXPORT_STOCK_TRANS_ID")
    public Long getExportStockTransId() {
        return exportStockTransId;
    }

    public void setExportStockTransId(Long exportStockTransId) {
        this.exportStockTransId = exportStockTransId;
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
    public MjrStockGoodsSerialDTO toDTO() {
        return new MjrStockGoodsSerialDTO(id==null?"":id+"",custId==null?"":custId+"",stockId==null?"":stockId+"",
                goodsId==null?"":goodsId+"",goodsState,cellCode,amount==null?"":amount+"",serial,importDate==null?"": DateTimeUtils.convertDateTimeToString(importDate),
                changeDate==null?"": DateTimeUtils.convertDateTimeToString(changeDate),status +"",partnerId==null?"":partnerId+"",
                importStockTransId==null?"":importStockTransId+"",inputPrice==null?"":inputPrice+"",outputPrice==null?"":outputPrice+"",
                exportDate==null?"": DateTimeUtils.convertDateTimeToString(exportDate),exportStockTransId==null?"":exportStockTransId+"",
                volume==null?"":volume+"",weight==null?"":weight+""
        );
    }
}
