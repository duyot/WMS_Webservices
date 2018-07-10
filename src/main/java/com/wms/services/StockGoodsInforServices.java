package com.wms.services;

import com.wms.business.interfaces.StockGoodsInforBusinessInterface;
import com.wms.dto.ChartDTO;
import com.wms.dto.MjrStockGoodsSerialDTO;
import com.wms.dto.StockGoodsInfor;
import com.wms.utils.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by truongbx on 7/7/2018.
 */
@RestController
@RequestMapping("/services/stockGoodsInforServices")
public class StockGoodsInforServices {
    Logger log = LoggerFactory.getLogger(StockGoodsInforServices.class);
    @Autowired
    StockGoodsInforBusinessInterface stockGoodsInforBusiness;

    @RequestMapping(value = "/getStockGoodsInfor", produces = "application/json")
    public List<StockGoodsInfor> getStockGoodsInfor(@RequestBody MjrStockGoodsSerialDTO stockGoods) {
        if (DataUtil.isNullOrEmpty(stockGoods.getPartnerId()) || DataUtil.isNullOrEmpty(stockGoods.getCustId())) {
            return null;
        }
        return stockGoodsInforBusiness.getStockGoodsInfor(stockGoods.getPartnerId(), stockGoods.getCustId());
    }

    @RequestMapping(value = "/getStockGoodsDetailInfor", produces = "application/json")
    public List<StockGoodsInfor> getStockGoodsDetailInfor(@RequestBody MjrStockGoodsSerialDTO stockGoods) {
        if (DataUtil.isNullOrEmpty(stockGoods.getPartnerId()) || DataUtil.isNullOrEmpty(stockGoods.getCustId())||DataUtil.isNullOrEmpty(stockGoods.getStockId())||DataUtil.isNullOrEmpty(stockGoods.getGoodsId())) {
            return null;
        }
        return stockGoodsInforBusiness.getStockGoodsDetailInfor(stockGoods.getPartnerId(), stockGoods.getCustId(),stockGoods.getStockId(),stockGoods.getGoodsId(),stockGoods.getSerial());
    }
}
