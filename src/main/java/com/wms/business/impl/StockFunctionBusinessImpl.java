package com.wms.business.impl;

import com.wms.business.interfaces.StockFunctionInterface;
import com.wms.dto.*;
import com.wms.persistents.dao.StockFunctionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.hibernate.Session;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Created by duyot on 3/6/2017.
 */
@Service("stockFunctionBusiness")
public class StockFunctionBusinessImpl implements StockFunctionInterface {
    @Autowired
    StockFunctionDAO stockFunctionDAO;

    @Override
    public String saveStockTransByConnection(MjrStockTransDTO mjrStockTransDTO, Connection con) {
        return stockFunctionDAO.saveStockTransByConnection(mjrStockTransDTO,con);
    }

    @Override
    public String saveStockDetailByConnection(MjrStockTransDTO mjrStockTransDTO,List<MjrStockTransDetailDTO> lstMjrStockTransDetailDTO, Connection con) {
        return stockFunctionDAO.importStockGoods(mjrStockTransDTO, lstMjrStockTransDetailDTO, con);

    }

    @Override
    public String saveStockGoodsTotalByConnection(MjrStockTransDTO mjrStockTransDTO,Map<String,Float> mapGoodsNumber,
                                                  Map<String,CatGoodsDTO> mapGoods,Connection connection) {
        return stockFunctionDAO.saveStockGoodsTotalByConnection(mjrStockTransDTO,mapGoodsNumber,mapGoods,connection);
    }

    @Override
    public ResponseObject exportStockGoodsDetail(MjrStockTransDTO mjrStockTransDTO, MjrStockTransDetailDTO goodsDetail, Session session) {
        return stockFunctionDAO.exportStockGoodsDetail(mjrStockTransDTO, goodsDetail, session);
    }

    @Override
    public ResponseObject exportStockGoodsDetailForPartner(MjrStockTransDTO mjrStockTransDTO, MjrStockTransDetailDTO goodsDetail, Session session) {
        return stockFunctionDAO.exportStockGoodsDetailForPartner(mjrStockTransDTO, goodsDetail, session);
    }

    @Override
    public ResponseObject updateExportStockGoodsTotal(MjrStockTransDTO mjrStockTransDTO, Map<String, Float> mapGoodsNumber, Session session) {
        return stockFunctionDAO.updateExportStockGoodsTotal(mjrStockTransDTO,mapGoodsNumber,session);
    }

    @Override
    public String getTotalStockTransaction(String stockId, Connection connection) {
        return stockFunctionDAO.getTotalStockTransaction(stockId,connection);
    }

    @Override
    public String getTotalStockTransaction(String stockId, Session session) {
        return stockFunctionDAO.getTotalStockTransaction(stockId,session);
    }

    @Override
    public ResponseObject cancelTransaction(String transId) {
        return stockFunctionDAO.cancelTransaction(transId);
    }

    @Override
    public List<String> getListSerialInStock(String custId, String stockId, String goodsId, String goodsState) {
        return stockFunctionDAO.getListSerialInStock(custId,stockId,goodsId,goodsState);
    }

    @Override
    public Long getCountGoodsDetail(String custId, String stockId, String goodsId, String goodsState, String isSerial) {
        return stockFunctionDAO.getCountGoodsDetail(custId,stockId,goodsId,goodsState,isSerial);
    }

    @Override
    public List<MjrStockTransDetailDTO> getTransGoodsDetail(String custId, String stockId, String transId, String transType) {
        return stockFunctionDAO.getTransGoodsDetail(custId,stockId,transId,transType);
    }

    @Override
    public List<MjrStockTransDetailDTO> getListTransGoodsDetail(String lstStockTransId) {
        return stockFunctionDAO.getListTransGoodsDetail(lstStockTransId);
    }
    @Override
    public List<MjrStockTransDTO> getStockTransInfo(String lstStockTransId) {
        return stockFunctionDAO.getStockTransInfo(lstStockTransId);
    }


}
