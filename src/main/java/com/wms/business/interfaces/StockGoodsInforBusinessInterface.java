package com.wms.business.interfaces;

import com.wms.dto.CatStockDTO;
import com.wms.dto.MjrStockTransDetailDTO;
import com.wms.dto.StockGoodsInfor;

import java.util.List;

public interface StockGoodsInforBusinessInterface {
    List<StockGoodsInfor> getStockGoodsInfor(String partnerId, String custId);
    List<StockGoodsInfor> getStockGoodsDetailInfor(String partnerId, String custId,String stockId,String goodsId,String serial);
    List<MjrStockTransDetailDTO> getAllStockGoodsDetail(String userId, String custId, String stockId, String partnerId, String goodsId, String goodsState);
}
