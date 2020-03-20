package com.wms.business.impl;

import com.wms.business.interfaces.StockFunctionInterface;
import com.wms.dto.*;
import com.wms.persistents.dao.StockFunctionDAO;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duyot on 3/6/2017.
 */
@Service("stockFunctionBusiness")
public class StockFunctionBusinessImpl implements StockFunctionInterface {
    @Autowired
    StockFunctionDAO stockFunctionDAO;

    @Override
    public String saveStockTransByConnection(MjrStockTransDTO mjrStockTransDTO, Connection con) {
        return stockFunctionDAO.saveStockTransByConnection(mjrStockTransDTO, con);
    }

    @Override
    public String saveStockDetailByConnection(MjrStockTransDTO mjrStockTransDTO, List<MjrStockTransDetailDTO> lstMjrStockTransDetailDTO, Connection con) {
        return stockFunctionDAO.importStockGoods(mjrStockTransDTO, lstMjrStockTransDetailDTO, con);

    }

    @Override
    public String saveStockGoodsTotalByConnection(MjrStockTransDTO mjrStockTransDTO, Map<String, Float> mapGoodsNumber,
                                                  Map<String, CatGoodsDTO> mapGoods, Connection connection) {
        return stockFunctionDAO.saveStockGoodsTotalByConnection(mjrStockTransDTO, mapGoodsNumber, mapGoods, connection);
    }

    @Override
    public ResponseObject exportStockGoodsDetail(MjrStockTransDTO mjrStockTransDTO, MjrStockTransDetailDTO goodsDetail, Session session) {
        return stockFunctionDAO.exportStockGoodsDetail(mjrStockTransDTO, goodsDetail, session);
    }

    @Override
    public ResponseObject updateExportStockGoodsTotal(MjrStockTransDTO mjrStockTransDTO, Map<String, Float> mapGoodsNumber, Session session) {
        return stockFunctionDAO.updateExportStockGoodsTotal(mjrStockTransDTO, mapGoodsNumber, session);
    }

    @Override
    public String getTotalStockTransaction(String stockId, Connection connection) {
        return stockFunctionDAO.getTotalStockTransaction(stockId, connection);
    }

    @Override
    public String getTotalStockTransaction(String stockId, Session session) {
        return stockFunctionDAO.getTotalStockTransaction(stockId, session);
    }

    @Override
    public ResponseObject cancelTransaction(String transId) {
        return stockFunctionDAO.cancelTransaction(transId);
    }

    @Override
    public List<String> getListSerialInStock(String custId, String stockId, String goodsId, String goodsState) {
        return stockFunctionDAO.getListSerialInStock(custId, stockId, goodsId, goodsState);
    }

    @Override
    public Long getCountGoodsDetail(String custId, String stockId, String goodsId, String goodsState, String isSerial) {
        return stockFunctionDAO.getCountGoodsDetail(custId, stockId, goodsId, goodsState, isSerial);
    }

    @Override
    public List<MjrStockTransDetailDTO> getTransGoodsDetail(String custId, String stockId, String transId, String transType) {
        return stockFunctionDAO.getTransGoodsDetail(custId, stockId, transId, transType);
    }

    @Override
    public List<MjrStockTransDetailDTO> getListTransGoodsDetail(String transId) {
        return stockFunctionDAO.getListTransGoodsDetail(transId);
    }

    @Override
    public List<MjrStockTransDTO> getListTransSerial(String custId, String goodsId, String serial) {
        return stockFunctionDAO.getListTransSerial(custId, goodsId, serial);
    }

    @Override
    public List<MjrStockTransDetailDTO> getListSerialAfterImport(String custId, String orderId, String lstSerial) {
        return stockFunctionDAO.getListSerialAfterImport(custId, orderId, lstSerial);
    }

    @Override
    public List<MjrStockTransDTO> getStockTransInfo(String lstStockTransId) {
        return stockFunctionDAO.getStockTransInfo(lstStockTransId);
    }

    @Override
    public List<ChartDTO> getTotalStockTrans(String custId, int type, String userId) {
        return stockFunctionDAO.getTotalStockTrans(custId, type, userId);
    }

    @Override
    public List<ChartDTO> getKPIStorage(String custId, int type, String userId) {
        return stockFunctionDAO.getKPIStorage(custId, type, userId);
    }

    @Override
    public InventoryInfoDTO getInventoryInfor(String custId) {
        return stockFunctionDAO.getInventoryInfor(custId);
    }

}
