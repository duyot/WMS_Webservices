package com.wms.business.interfaces;

import com.wms.dto.CatGoodsDTO;
import com.wms.dto.InsertStockDetailResultDTO;
import com.wms.dto.MjrStockTransDTO;
import com.wms.dto.MjrStockTransDetailDTO;
import org.hibernate.Session;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Created by duyot on 3/6/2017.
 */
public interface StockBusinessWithConnectionInterface {
    public String saveStockTransByConnection(MjrStockTransDTO mjrStockTransDTO, Connection con);
    //
    public String saveStockDetailByConnection(MjrStockTransDTO mjrStockTransDTO, List<MjrStockTransDetailDTO> lstMjrStockTransDetailDTO, Connection con);
    //
    public String saveStockGoodsTotalByConnection(MjrStockTransDTO mjrStockTransDTO,Map<String,Float> mapGoodsNumber,
                                                  Map<String,CatGoodsDTO> mapGoods,Connection connection);
}
