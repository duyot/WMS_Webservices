package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.persistents.model.MjrStockGoodsTotal;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

import java.util.Date;

/**
 * Created by duyot on 12/19/2016.
 */
public class MjrStockGoodsTotalDTO extends BaseDTO {
    private String id;
    private String custId;
    private String custName;
    private String goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsState;
    private String stockId;
    private String stockCode;
    private String amount;
    private String changeDate;

    public MjrStockGoodsTotalDTO(String id, String custId, String custName, String goodsId, String goodsCode, String goodsName, String goodsState, String stockId, String stockCode, String amount, String changeDate) {
        this.id = id;
        this.custId = custId;
        this.custName = custName;
        this.goodsId = goodsId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsState = goodsState;
        this.stockId = stockId;
        this.stockCode = stockCode;
        this.amount = amount;
        this.changeDate = changeDate;
    }

    public MjrStockGoodsTotalDTO() {
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

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    @Override
    public MjrStockGoodsTotal toModel() {
        return new MjrStockGoodsTotal(!StringUtils.validString(id) ? null:Long.valueOf(id),
                !StringUtils.validString(custId) ? null:Long.valueOf(custId),custName,
                !StringUtils.validString(goodsId) ? null:Long.valueOf(goodsId),goodsCode,goodsName,goodsState,
                !StringUtils.validString(stockId) ? null:Long.valueOf(stockId),stockCode,
                !StringUtils.validString(amount) ? null:Double.valueOf(amount),!StringUtils.validString(changeDate) ? null: DateTimeUtils.convertStringToDate(changeDate)
        );
    }

    @Override
    public String toString() {
        return "MjrStockGoodsTotalDTO{" +
                "id='" + id + '\'' +
                ", custId='" + custId + '\'' +
                ", custName='" + custName + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsState='" + goodsState + '\'' +
                ", stockId='" + stockId + '\'' +
                ", stockCode='" + stockCode + '\'' +
                ", amount='" + amount + '\'' +
                ", changeDate='" + changeDate + '\'' +
                '}';
    }
}
