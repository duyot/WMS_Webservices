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
    private String totalMoney;
    //unmap db field
    private String goodsName;
    //
    private String stockId;
    private String importDate;
    private String exportDate;
    private String status;
    private String stockTransCode;
    private String stockName;
    private String stockTransType;
    private String unitName;
    private String stockTransCreatedDate;
    private String stockTransCreatedUser;
    private String partnerId;
    private String partnerName;

    private String volume;
    private String weight;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getStockTransCode() {
        return stockTransCode;
    }

    public void setStockTransCode(String stockTransCode) {
        this.stockTransCode = stockTransCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockTransType() {
        return stockTransType;
    }

    public void setStockTransType(String stockTransType) {
        this.stockTransType = stockTransType;
    }


    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getStockTransCreatedDate() {
        return stockTransCreatedDate;
    }

    public void setStockTransCreatedDate(String stockTransCreatedDate) {
        this.stockTransCreatedDate = stockTransCreatedDate;
    }

    public String getStockTransCreatedUser() {
        return stockTransCreatedUser;
    }

    public void setStockTransCreatedUser(String stockTransCreatedUser) {
        this.stockTransCreatedUser = stockTransCreatedUser;
    }


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

    public MjrStockTransDetailDTO(String id, String stockTransId, String goodsId, String goodsCode, String goodsState, String isSerial, String amount, String serial, String inputPrice, String outputPrice, String cellCode, String partnerId) {
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
        this.partnerId = partnerId;
    }

    public MjrStockTransDetailDTO(String id, String stockTransId, String goodsId, String goodsCode, String goodsState, String isSerial, String amount, String serial, String inputPrice, String outputPrice, String cellCode, String partnerId,String volume, String weight ) {
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
        this.partnerId = partnerId;
        this.volume = volume;
        this.weight = weight;
    }

    public MjrStockTransDetailDTO(String id, String stockTransId, String goodsId, String goodsCode, String goodsState, String isSerial, String amount, String serial, String inputPrice, String outputPrice, String cellCode, String totalMoney, String goodsName, String stockId, String importDate, String exportDate, String status, String stockTransCode, String stockName, String stockTransType, String unitName, String stockTransCreatedDate, String stockTransCreatedUser, String partnerId, String partnerName, String volume, String weight) {
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
        this.totalMoney = totalMoney;
        this.goodsName = goodsName;
        this.stockId = stockId;
        this.importDate = importDate;
        this.exportDate = exportDate;
        this.status = status;
        this.stockTransCode = stockTransCode;
        this.stockName = stockName;
        this.stockTransType = stockTransType;
        this.unitName = unitName;
        this.stockTransCreatedDate = stockTransCreatedDate;
        this.stockTransCreatedUser = stockTransCreatedUser;
        this.partnerId = partnerId;
        this.partnerName = partnerName;
        this.volume = volume;
        this.weight = weight;
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
                !StringUtils.validString(goodsId) ? null:Long.valueOf(goodsId),goodsCode,goodsState,
                !StringUtils.validString(isSerial) ? null:Long.valueOf(isSerial),
                !StringUtils.validString(amount) ? null:Float.valueOf(amount),serial,
                !StringUtils.validString(inputPrice) ? null:Float.valueOf(inputPrice),
                !StringUtils.validString(outputPrice) ? null:Float.valueOf(outputPrice),
                cellCode,unitName,
                !StringUtils.validString(totalMoney) ? null:Float.valueOf(totalMoney),
                !StringUtils.validString(partnerId) ? null:Long.valueOf(partnerId),
                !StringUtils.validString(weight) ? null:Float.valueOf(weight),
                !StringUtils.validString(volume) ? null:Float.valueOf(volume)
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

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
