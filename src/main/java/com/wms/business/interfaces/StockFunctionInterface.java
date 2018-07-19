package com.wms.business.interfaces;

import com.wms.dto.*;
import org.hibernate.Session;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Created by duyot on 3/6/2017.
 */
public interface StockFunctionInterface {
    //functions for import
    String saveStockTransByConnection(MjrStockTransDTO mjrStockTransDTO, Connection con);
    //
    String saveStockDetailByConnection(MjrStockTransDTO mjrStockTransDTO, List<MjrStockTransDetailDTO> lstMjrStockTransDetailDTO, Connection con);
    //
    String saveStockGoodsTotalByConnection(MjrStockTransDTO mjrStockTransDTO,Map<String,Float> mapGoodsNumber,
                                                  Map<String,CatGoodsDTO> mapGoods,Connection connection);
    //function for export
    ResponseObject exportStockGoodsDetail(MjrStockTransDTO mjrStockTransDTO, MjrStockTransDetailDTO goodsDetail, Session session);

    ResponseObject updateExportStockGoodsTotal(MjrStockTransDTO mjrStockTransDTO, Map<String,Float> mapGoodsNumber, Session session);
    //
    String getTotalStockTransaction(String stockId, Connection connection);
    String getTotalStockTransaction(String stockId, Session connection);
    //cancel Command
    ResponseObject cancelTransaction(String transId);
    //
    List<String> getListSerialInStock(String custId,  String stockId,String goodsId, String goodsState);
    //
    Long getCountGoodsDetail(String custId,  String stockId,String goodsId, String goodsState, String isSerial);
    //
    List<MjrStockTransDetailDTO> getTransGoodsDetail(String custId, String stockId, String transId, String transType);
    //
    List<MjrStockTransDetailDTO> getListTransGoodsDetail(String lstStockTransId);
    //
    List<MjrStockTransDTO> getStockTransInfo(String lstStockTransId);

    //
    List<ChartDTO> getTotalStockTrans(String custId, int type,String lstStockId);

}
