package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.persistents.model.MjrStockGoodsTotal;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 12/19/2016.
 */
public class MjrStockGoodsTotalDTO extends BaseDTO {
    private String id;
    private String custId;
    private String goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsState;
    private String stockId;
    private String amount;
    private String changeDate;
    private String preAmount;
    private String issueAmount;
    //DoanLV lay cac kho thuoc user dang login vao he thong (join qua bang map_user_stock)
    private String stockName;
    private String userId;
    private String partnerId;

    public MjrStockGoodsTotalDTO(String id, String custId, String goodsId, String goodsCode, String goodsName, String goodsState, String stockId, String amount, String changeDate, String preAmount, String issueAmount) {
        this.id = id;
        this.custId = custId;
        this.goodsId = goodsId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsState = goodsState;
        this.stockId = stockId;
        this.amount = amount;
        this.changeDate = changeDate;
        this.preAmount = preAmount;
        this.issueAmount = issueAmount;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public MjrStockGoodsTotalDTO() {
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
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

    public String getPreAmount() {
        return preAmount;
    }

    public void setPreAmount(String preAmount) {
        this.preAmount = preAmount;
    }

    public String getIssueAmount() {
        return issueAmount;
    }

    public void setIssueAmount(String issueAmount) {
        this.issueAmount = issueAmount;
    }

    @Override
    public MjrStockGoodsTotal toModel() {
        return new MjrStockGoodsTotal(!StringUtils.validString(id) ? null : Long.valueOf(id),
                !StringUtils.validString(custId) ? null : Long.valueOf(custId),
                !StringUtils.validString(goodsId) ? null : Long.valueOf(goodsId), goodsCode, goodsName, goodsState,
                !StringUtils.validString(stockId) ? null : Long.valueOf(stockId),
                !StringUtils.validString(amount) ? null : Double.valueOf(amount), !StringUtils.validString(changeDate) ? null : DateTimeUtils.convertStringToDate(changeDate),
                !StringUtils.validString(preAmount) ? null : Double.valueOf(preAmount),
                !StringUtils.validString(issueAmount) ? null : Double.valueOf(issueAmount)
        );
    }

    @Override
    public String toString() {
        return "MjrStockGoodsTotalDTO{" +
                "id='" + id + '\'' +
                ", custId='" + custId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsState='" + goodsState + '\'' +
                ", stockId='" + stockId + '\'' +
                ", amount='" + amount + '\'' +
                ", changeDate='" + changeDate + '\'' +
                ", preAmount='" + preAmount + '\'' +
                ", issueAmount='" + issueAmount + '\'' +
                '}';
    }
}
