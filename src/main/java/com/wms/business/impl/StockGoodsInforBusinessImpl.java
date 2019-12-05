package com.wms.business.impl;

/**
 * Created by truongbx on 7/7/2018.
 */

import com.wms.business.interfaces.StockGoodsInforBusinessInterface;
import com.wms.dto.MjrStockTransDetailDTO;
import com.wms.dto.StockGoodsInfor;
import com.wms.persistents.dao.StockGoodsInforDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("stockGoodsInforBusiness")
public class StockGoodsInforBusinessImpl implements StockGoodsInforBusinessInterface {
    @Autowired
    StockGoodsInforDAO stockGoodsInforDAO;

    @Override
    public List<StockGoodsInfor> getStockGoodsInfor(String partnerId, String custId) {
        return stockGoodsInforDAO.getStockGoodsInfor(partnerId, custId);
    }

    @Override
    public List<StockGoodsInfor> getStockGoodsDetailInfor(String partnerId, String custId, String stockId, String goodsId, String serial) {
        return stockGoodsInforDAO.getStockGoodsDetailInfor(partnerId, custId, stockId, goodsId, serial);
    }

    @Override
    public List<MjrStockTransDetailDTO> getAllStockGoodsDetail(String userId, String custId, String stockId, String partnerId, String goodsId, String goodState) {
        return stockGoodsInforDAO.getAllStockGoodsDetail(userId, custId, stockId, partnerId, goodsId, goodState);
    }
}
