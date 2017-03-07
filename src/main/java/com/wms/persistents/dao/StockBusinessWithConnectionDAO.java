package com.wms.persistents.dao;

import com.wms.business.impl.StockManagementBusinessImpl;
import com.wms.dto.CatGoodsDTO;
import com.wms.dto.MjrStockGoodsTotalDTO;
import com.wms.dto.MjrStockTransDTO;
import com.wms.dto.MjrStockTransDetailDTO;
import com.wms.enums.Responses;
import com.wms.persistents.model.MjrStockGoodsTotal;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by duyot on 3/6/2017.
 */
@Repository
@Transactional
public class StockBusinessWithConnectionDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    MjrStockGoodsTotalDAO mjrStockGoodsTotalDAO;

    @Autowired
    MjrStockTransDAO mjrStockTransDAO;

    Logger log = LoggerFactory.getLogger(StockManagementBusinessImpl.class);

    public String saveStockTransByConnection(MjrStockTransDTO mjrStockTransDTO,Connection connection){
        return mjrStockTransDAO.saveStockTransByConnection(mjrStockTransDTO,connection);
    }


    public String saveStockGoodsTotalByConnection(MjrStockTransDTO mjrStockTransDTO,Map<String,Float> mapGoodsNumber,
                                           Map<String,CatGoodsDTO> mapGoods,Connection connection){
        Iterator it = mapGoodsNumber.entrySet().iterator();
        String updateResult;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            //
            String key = (String) pair.getKey();
            Float amount     = (Float) pair.getValue();
            String goodsCode = key.split(",")[0];
            String goodsState = key.split(",")[1];
            CatGoodsDTO goods = mapGoods.get(goodsCode);
            MjrStockGoodsTotalDTO stockGoodsTotal = new MjrStockGoodsTotalDTO();
            stockGoodsTotal.setCustId(mjrStockTransDTO.getCustId());
            stockGoodsTotal.setStockId(mjrStockTransDTO.getStockId());
            stockGoodsTotal.setGoodsId(goods.getId());
            stockGoodsTotal.setGoodsCode(goods.getCode());
            stockGoodsTotal.setGoodsName(goods.getName());
            stockGoodsTotal.setGoodsState(goodsState);
            stockGoodsTotal.setAmount(amount+"");
            stockGoodsTotal.setChangeDate(mjrStockTransDTO.getCreatedDate());

            updateResult = mjrStockGoodsTotalDAO.updateTotal(stockGoodsTotal,connection);
            if(!Responses.SUCCESS.getName().equalsIgnoreCase(updateResult)){
                return updateResult;
            }
            //
        }

        return Responses.SUCCESS.getName();
    }

    public String importStockGoods(MjrStockTransDTO mjrStockTransDTO, List<MjrStockTransDetailDTO> lstGoodsDetail, Connection connection){
        //
        List paramsStockTransDetail;
        List paramsStockGoodsSerial;
        List paramsStockGoods;
        //PREPARE STATEMENTS
        PreparedStatement prstmtInsertStockTransDetail;
        PreparedStatement prstmtInsertStockGoodsSerial;
        PreparedStatement prstmtInsertStockGoods;
        //SQL
        StringBuilder sqlStockTransDetail = new StringBuilder();
        StringBuilder sqlStockGoodsSerial = new StringBuilder();
        StringBuilder sqlStockGoods = new StringBuilder();
        //STOCK_TRANS_DETAIL
        sqlStockTransDetail.append(" Insert into MJR_STOCK_TRANS_DETAIL ");
        sqlStockTransDetail.append(" (ID,STOCK_TRANS_ID,GOODS_ID,GOODS_CODE,GOODS_STATE,IS_SERIAL,AMOUNT,SERIAL,INPUT_PRICE,CELL_CODE)  ");
        sqlStockTransDetail.append(" values  (SEQ_MJR_STOCK_TRANS_DETAIL.nextval,?,?,?,?,?,?,?,?,?) ");
        //STOCK_GOODS_SERIAL
        sqlStockGoodsSerial.append(" Insert into MJR_STOCK_GOODS_SERIAL  ");
        sqlStockGoodsSerial.append(" (ID,CUST_ID,STOCK_ID,GOODS_ID,GOODS_STATE,CELL_CODE,AMOUNT,SERIAL,IMPORT_DATE,CHANGED_DATE,STATUS,PARTNER_ID,IMPORT_STOCK_TRANS_ID,INPUT_PRICE)  ");
        sqlStockGoodsSerial.append(" values (SEQ_MJR_STOCK_GOODS_SERIAL.nextval,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),to_date(?,'dd/MM/yyyy hh24:mi:ss'),?,?,?,?) ");
        sqlStockGoodsSerial.append(" LOG ERRORS INTO ERR$_MJR_STOCK_GOODS_SERIAL REJECT LIMIT UNLIMITED ");
        //STOCK_GOODS
        sqlStockGoods.append(" Insert into MJR_STOCK_GOODS ");
        sqlStockGoods.append(" (ID,CUST_ID,STOCK_ID,GOODS_ID,GOODS_STATE,CELL_CODE,AMOUNT,IMPORT_DATE,CHANGED_DATE,STATUS,PARTNER_ID,IMPORT_STOCK_TRANS_ID,INPUT_PRICE)  ");
        sqlStockGoods.append(" values  (SEQ_MJR_STOCK_GOODS.nextval,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),to_date(?,'dd/MM/yyyy hh24:mi:ss'),?,?,?,?)  ");
        sqlStockGoods.append(" LOG ERRORS INTO ERR$_MJR_STOCK_GOODS_SERIAL REJECT LIMIT UNLIMITED ");
        //3. TAO PREPARE STATEMENT
        try {
            prstmtInsertStockTransDetail = connection.prepareStatement(sqlStockTransDetail.toString());
            prstmtInsertStockGoodsSerial = connection.prepareStatement(sqlStockGoodsSerial.toString());
            prstmtInsertStockGoods = connection.prepareStatement(sqlStockGoods.toString());
            //
            int count = 0;
            //
            for (MjrStockTransDetailDTO goods : lstGoodsDetail) {
                //
                count++;
                //DETAIL
                paramsStockTransDetail = setParamsStockTransSerial(mjrStockTransDTO,goods);
                //SET PARAMS AND ADD TO BATCH
                for (int idx = 0; idx < paramsStockTransDetail.size(); idx++) {
                    prstmtInsertStockTransDetail.setString(idx + 1, DataUtil.nvl(paramsStockTransDetail.get(idx), "").toString());
                }
                prstmtInsertStockTransDetail.addBatch();
                //
                if(Constants.IS_SERIAL.equalsIgnoreCase(goods.getIsSerial())){
                    paramsStockGoodsSerial = setParamsStockGoodsSerial(mjrStockTransDTO,goods);
                    for (int idx = 0; idx < paramsStockGoodsSerial.size(); idx++) {
                        prstmtInsertStockGoodsSerial.setString(idx + 1, DataUtil.nvl(paramsStockGoodsSerial.get(idx), "").toString());
                    }
                    prstmtInsertStockGoodsSerial.addBatch();
                }else{
                    paramsStockGoods = setParamsStockGoods(mjrStockTransDTO,goods);
                    for (int idx = 0; idx < paramsStockGoods.size(); idx++) {
                        prstmtInsertStockGoods.setString(idx + 1, DataUtil.nvl(paramsStockGoods.get(idx), "").toString());
                    }
                    prstmtInsertStockGoods.addBatch();
                }
                //
                if(count >= Constants.COMMIT_NUMBER){
                    try {
                        prstmtInsertStockTransDetail.executeBatch();
                        prstmtInsertStockGoodsSerial.executeBatch();
                        prstmtInsertStockGoods.executeBatch();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    count = 0;
                }
            }
            if(count > 0){
                try {
                    prstmtInsertStockTransDetail.executeBatch();
                    prstmtInsertStockGoodsSerial.executeBatch();
                    prstmtInsertStockGoods.executeBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //
            prstmtInsertStockTransDetail.close();
            prstmtInsertStockGoodsSerial.close();
            prstmtInsertStockGoods.close();


        } catch (SQLException e) {
            log.info(e.toString());
            Responses.ERROR.getName();
        }

        return Responses.SUCCESS.getName();
    }

    public List setParamsStockTransSerial(MjrStockTransDTO transDetail, MjrStockTransDetailDTO goods) {
        List paramsStockTrans = new ArrayList();
        paramsStockTrans.add(transDetail.getId());
        paramsStockTrans.add(goods.getGoodsId());
        paramsStockTrans.add(goods.getGoodsCode());
        paramsStockTrans.add(goods.getGoodsState());
        paramsStockTrans.add(goods.getIsSerial());
        paramsStockTrans.add(goods.getAmount());
        paramsStockTrans.add(goods.getSerial());
        paramsStockTrans.add(goods.getInputPrice());
        paramsStockTrans.add(goods.getCellCode());
        return paramsStockTrans;
    }

    public List setParamsStockGoodsSerial(MjrStockTransDTO transDetail,MjrStockTransDetailDTO goods) {
        List paramsStockTrans = new ArrayList();
        paramsStockTrans.add(transDetail.getCustId());
        paramsStockTrans.add(transDetail.getStockId());
        paramsStockTrans.add(goods.getGoodsId ());
        paramsStockTrans.add(goods.getGoodsState());
        paramsStockTrans.add(goods.getCellCode());
        paramsStockTrans.add(goods.getAmount());
        paramsStockTrans.add(goods.getSerial());
        paramsStockTrans.add(transDetail.getCreatedDate());
        paramsStockTrans.add(transDetail.getCreatedDate());
        paramsStockTrans.add(Constants.STATUS.ACTIVE);
        paramsStockTrans.add("1");
        paramsStockTrans.add(transDetail.getId());
        paramsStockTrans.add(goods.getInputPrice());
        return paramsStockTrans;
    }
    public List setParamsStockGoods(MjrStockTransDTO transDetail,MjrStockTransDetailDTO goods) {
        List paramsStockTrans = new ArrayList();
        paramsStockTrans.add(transDetail.getCustId());
        paramsStockTrans.add(transDetail.getStockId());
        paramsStockTrans.add(goods.getGoodsId ());
        paramsStockTrans.add(goods.getGoodsState());
        paramsStockTrans.add(goods.getCellCode());
        paramsStockTrans.add(goods.getAmount());
        paramsStockTrans.add(transDetail.getCreatedDate());
        paramsStockTrans.add(transDetail.getCreatedDate());
        paramsStockTrans.add(Constants.STATUS.ACTIVE);
        paramsStockTrans.add("1");
        paramsStockTrans.add(transDetail.getId());
        paramsStockTrans.add(goods.getInputPrice());
        return paramsStockTrans;
    }


}
