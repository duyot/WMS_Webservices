package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.Err$MjrStockGoodsSerialDTO;
import com.wms.dto.MjrStockGoodsSerialDTO;
import com.wms.utils.DateTimeUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by duyot on 3/6/2017.
 */
@Entity
@Table(name = "err$_mjr_stock_goods_serial")
public class Err$MjrStockGoodsSerial extends BaseModel{
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
    private String status;
    private Long partnerId;
    private Long importStockTransId;
    private Float inputPrice;
    private Float outputPrice;
    //
    private String oraErrorMessage;

    public Err$MjrStockGoodsSerial(Long id, Long custId, Long stockId, Long goodsId, String goodsState, String cellCode,
                                   Float amount, String serial, Date importDate, Date changeDate, String status,
                                   Long partnerId, Long importStockTransId, Float inputPrice, Float outputPrice, String oraErrorMessage) {
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
        this.oraErrorMessage = oraErrorMessage;
    }

    public Err$MjrStockGoodsSerial() {
    }


    @Id
    @Column(name = "ID",nullable = false)
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
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    @Column(name = "ora_err_mesg$")
    public String getOraErrorMessage() {
        return oraErrorMessage;
    }

    public void setOraErrorMessage(String oraErrorMessage) {
        this.oraErrorMessage = oraErrorMessage;
    }

    @Override
    public BaseDTO toDTO() {
        return new Err$MjrStockGoodsSerialDTO(id==null?"":id+"",custId==null?"":custId+"",stockId==null?"":stockId+"",
                goodsId==null?"":goodsId+"",goodsState,cellCode,amount==null?"":amount+"",serial,importDate==null?"": DateTimeUtils.convertDateTimeToString(importDate),
                changeDate==null?"": DateTimeUtils.convertDateTimeToString(changeDate),status,partnerId==null?"":partnerId+"",
                importStockTransId==null?"":importStockTransId+"",inputPrice==null?"":inputPrice+"",outputPrice==null?"":outputPrice+"",oraErrorMessage
        );
    }
}
