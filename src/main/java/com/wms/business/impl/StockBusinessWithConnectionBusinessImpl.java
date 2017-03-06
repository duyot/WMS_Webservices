package com.wms.business.impl;

import com.wms.business.interfaces.StockBusinessWithConnectionInterface;
import com.wms.dto.*;
import com.wms.enums.Responses;
import com.wms.persistents.dao.StockBusinessWithConnectionDAO;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import com.wms.utils.FunctionUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by duyot on 3/6/2017.
 */
@Service("stockBusinessWithConnectionBusiness")
public class StockBusinessWithConnectionBusinessImpl implements StockBusinessWithConnectionInterface{
    @Autowired
    StockBusinessWithConnectionDAO stockBusinessWithConnectionDAO;

    @Override
    public String saveStockTransByConnection(MjrStockTransDTO mjrStockTransDTO, Connection con) {
        return stockBusinessWithConnectionDAO.saveStockTransByConnection(mjrStockTransDTO,con);
    }

    @Override
    public String saveStockDetailByConnection(MjrStockTransDTO mjrStockTransDTO,List<MjrStockTransDetailDTO> lstMjrStockTransDetailDTO, Connection con) {
        return stockBusinessWithConnectionDAO.importStockGoods(mjrStockTransDTO, lstMjrStockTransDetailDTO, con);

    }

    @Override
    public String saveStockGoodsTotalByConnection(MjrStockTransDTO mjrStockTransDTO,Map<String,Float> mapGoodsNumber,
                                                  Map<String,CatGoodsDTO> mapGoods,Connection connection) {
        return stockBusinessWithConnectionDAO.saveStockGoodsTotalByConnection(mjrStockTransDTO,mapGoodsNumber,mapGoods,connection);
    }
}
