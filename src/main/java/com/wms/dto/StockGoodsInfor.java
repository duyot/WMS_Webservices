package com.wms.dto;

public class StockGoodsInfor {
    String  custId;
    String  custName;
    String  stockId;
    String  stockName;
    String  goodsId;
    String  goodName;
    String  goodsState;
    String  goodsStateName;
    String  partnerId;
    String  serial;
    String  amount;
    String cellCode;
    String importDate;
    String importStockTransId;
    String changeDate;
    String imputPrice;

    public StockGoodsInfor() {
    }

    public StockGoodsInfor(String custId, String custName, String stockId, String stockName, String goodsId, String goodName, String goodsState, String goodsStateName, String partnerId, String serial, String amount) {
        this.custId = custId;
        this.custName = custName;
        this.stockId = stockId;
        this.stockName = stockName;
        this.goodsId = goodsId;
        this.goodName = goodName;
        this.goodsState = goodsState;
        this.goodsStateName = goodsStateName;
        this.partnerId = partnerId;
        this.serial = serial;
        this.amount = amount;
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

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    public String getGoodsStateName() {
        return goodsStateName;
    }

    public void setGoodsStateName(String goodsStateName) {
        this.goodsStateName = goodsStateName;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCellCode() {
        return cellCode;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
    }

    public String getImportDate() {
        return importDate;
    }

    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }

    public String getImportStockTransId() {
        return importStockTransId;
    }

    public void setImportStockTransId(String importStockTransId) {
        this.importStockTransId = importStockTransId;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getImputPrice() {
        return imputPrice;
    }

    public void setImputPrice(String imputPrice) {
        this.imputPrice = imputPrice;
    }
}
