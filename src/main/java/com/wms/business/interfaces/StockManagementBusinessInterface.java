package com.wms.business.interfaces;

import com.wms.dto.MjrStockGoodsTotalDTO;
import com.wms.dto.MjrStockTransDTO;
import com.wms.dto.MjrStockTransDetailDTO;
import com.wms.dto.ResponseObject;

import java.util.List;

/**
 * Created by duyot on 12/19/2016.
 */
public interface StockManagementBusinessInterface {
    public ResponseObject importStock(MjrStockTransDTO mjrStockTransDTO, List<MjrStockTransDetailDTO> lstMjrStockTransDetailDTO);
    public ResponseObject exportStock(MjrStockTransDTO mjrStockTransDTO, List<MjrStockTransDetailDTO> lstMjrStockTransDetailDTO);
}
