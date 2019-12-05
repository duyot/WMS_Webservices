package com.wms.dto;

import java.util.Map;

/**
 * Created by duyot on 3/6/2017.
 */
public class InsertStockDetailResultDTO {
    public ResponseObject responseObject;
    public Map<String, Float> mapGoodsNumber;

    public InsertStockDetailResultDTO(ResponseObject responseObject, Map<String, Float> mapGoodsNumber) {
        this.responseObject = responseObject;
        this.mapGoodsNumber = mapGoodsNumber;
    }

    public InsertStockDetailResultDTO() {
    }

    public ResponseObject getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(ResponseObject responseObject) {
        this.responseObject = responseObject;
    }

    public Map<String, Float> getMapGoodsNumber() {
        return mapGoodsNumber;
    }

    public void setMapGoodsNumber(Map<String, Float> mapGoodsNumber) {
        this.mapGoodsNumber = mapGoodsNumber;
    }
}
