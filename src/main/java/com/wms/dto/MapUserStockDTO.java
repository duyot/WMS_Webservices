package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.persistents.model.MapUserStock;
import com.wms.utils.StringUtils;

public class MapUserStockDTO extends BaseDTO {

    String id;
    String userId;
    String stockId;

    public MapUserStockDTO(String id, String userId, String stockId) {
        this.id = id;
        this.userId = userId;
        this.stockId = stockId;
    }

    public MapUserStockDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    @Override
    public MapUserStock toModel() {
        return new MapUserStock(!StringUtils.validString(id) ? null : Long.valueOf(id), !StringUtils.validString(userId) ? null : Long.valueOf(userId), !StringUtils.validString(stockId) ? null : Long.valueOf(stockId));
    }
}
