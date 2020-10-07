package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.persistents.model.CatStockCell;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 4/19/2017.
 */
public class CatStockCellDTO extends BaseDTO {
    private String id;
    private String code;
    private String stockId;
    private String maxWeight;
    private String maxVolume;
    private String manyCodes;

    public CatStockCellDTO(String id, String code, String stockId, String maxWeight, String maxVolume, String manyCodes) {
        this.id = id;
        this.code = code;
        this.stockId = stockId;
        this.maxWeight = maxWeight;
        this.maxVolume = maxVolume;
        this.manyCodes = manyCodes;
    }

    public CatStockCellDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(String maxWeight) {
        this.maxWeight = maxWeight;
    }

    public String getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(String maxVolume) {
        this.maxVolume = maxVolume;
    }

    public String getManyCodes() {
        return manyCodes;
    }

    public void setManyCodes(String manyCodes) {
        this.manyCodes = manyCodes;
    }

    @Override
    public CatStockCell toModel() {
        return new CatStockCell(!StringUtils.validString(id) ? null : Long.valueOf(id), code, !StringUtils.validString(stockId) ? null : Long.valueOf(stockId),!StringUtils.validString(maxWeight) ? null : Double.valueOf(maxWeight),!StringUtils.validString(maxVolume) ? null : Double.valueOf(maxVolume),!StringUtils.validString(manyCodes) ? null : Long.valueOf(manyCodes));
    }

    @Override
    public String toString() {
        return "CatStockCellDTO{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", stockId='" + stockId + '\'' +
                ", maxWeight='" + maxWeight + '\'' +
                ", maxVolume='" + maxVolume + '\'' +
                ", manyCodes='" + manyCodes + '\'' +
                '}';
    }
}
