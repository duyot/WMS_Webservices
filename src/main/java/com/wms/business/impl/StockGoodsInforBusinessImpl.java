package com.wms.business.impl;

/**
 * Created by truongbx on 7/7/2018.
 */

import com.wms.base.BaseBusinessImpl;
import com.wms.business.interfaces.StockGoodsInforBusinessInterface;
import com.wms.dto.AppParamsDTO;
import com.wms.dto.StockGoodsInfor;
import com.wms.persistents.dao.AppParamsDAO;
import com.wms.persistents.dao.StockGoodsInforDAO;
import com.wms.persistents.model.AppParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service("stockGoodsInforBusiness")
public class StockGoodsInforBusinessImpl implements StockGoodsInforBusinessInterface {
    @Autowired
    StockGoodsInforDAO stockGoodsInforDAO;

    @Override
    public List<StockGoodsInfor> getStockGoodsInfor(String partnerId, String custId) {
       return stockGoodsInforDAO.getStockGoodsInfor(partnerId,custId);
    }

    @Override
    public List<StockGoodsInfor> getStockGoodsDetailInfor(String partnerId, String custId, String stockId, String goodsId, String serial) {
        return stockGoodsInforDAO.getStockGoodsDetailInfor(partnerId,custId,stockId,goodsId,serial);
    }
}
