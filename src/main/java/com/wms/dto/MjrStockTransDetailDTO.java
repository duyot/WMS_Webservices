package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.persistents.model.MjrStockTransDetail;
import com.wms.utils.Constants;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 12/29/2016.
 */
public class MjrStockTransDetailDTO  extends BaseDTO{
    private String id;
    private String stockTransId;
    private String goodsId;
    private String goodsCode;
    private String goodsState;
    private String isSerial;
    private String amount;
    private String serial;
    private String inputPrice;
    private String outputPrice;
    private String cellCode;
    //unmap db field
    private String goodsName;
    //
    private String stockId;
    private String importDate;
    private String exportDate;
    private String status;

    public MjrStockTransDetailDTO(String id, String stockTransId, String goodsId, String goodsCode, String goodsState, String isSerial, String amount, String serial, String inputPrice, String outputPrice, String cellCode, String goodsName, String stockId, String importDate, String exportDate, String status) {
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
        this.goodsName = goodsName;
        this.stockId = stockId;
        this.importDate = importDate;
        this.exportDate = exportDate;
        this.status = status;
    }

    public MjrStockTransDetailDTO(String id, String stockTransId, String goodsId, String goodsCode, String goodsState, String isSerial, String amount, String serial, String inputPrice, String outputPrice, String cellCode) {
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
    }

    public MjrStockTransDetailDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(String stockTransId) {
        this.stockTransId = stockTransId;
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

    public String getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    public String getIsSerial() {
        return isSerial;
    }

    public void setIsSerial(String isSerial) {
        this.isSerial = isSerial;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSerial() {
        return serial;
    }

    public boolean isSerial() {
        return Constants.IS_SERIAL.equalsIgnoreCase(isSerial);
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getInputPrice() {
        return inputPrice;
    }

    public void setInputPrice(String inputPrice) {
        this.inputPrice = inputPrice;
    }

    public String getOutputPrice() {
        return outputPrice;
    }

    public void setOutputPrice(String outputPrice) {
        this.outputPrice = outputPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getCellCode() {
        return cellCode;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
    }

    @Override
    public MjrStockTransDetail toModel() {
        return new MjrStockTransDetail(!StringUtils.validString(id) ? null:Long.valueOf(id),!StringUtils.validString(stockTransId) ? null:Long.valueOf(stockTransId),
                !StringUtils.validString(goodsId) ? null:Long.valueOf(goodsId),goodsCode,goodsState,!StringUtils.validString(isSerial) ? null:Long.valueOf(isSerial),
                !StringUtils.validString(amount) ? null:Float.valueOf(amount),serial,
                !StringUtils.validString(inputPrice) ? null:Float.valueOf(inputPrice),!StringUtils.validString(outputPrice) ? null:Float.valueOf(outputPrice),cellCode
        );
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getImportDate() {
        return importDate;
    }

    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }

    public String getExportDate() {
        return exportDate;
    }

    public void setExportDate(String exportDate) {
        this.exportDate = exportDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
