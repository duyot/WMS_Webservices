package com.wms.business.impl;

import com.google.common.collect.Lists;
import com.wms.base.BaseBusinessInterface;
import com.wms.business.interfaces.MjrStockGoodsTotalBusinessInterface;
import com.wms.business.interfaces.StockBusinessWithConnectionInterface;
import com.wms.business.interfaces.StockManagementBusinessInterface;
import com.wms.dto.*;
import com.wms.enums.Responses;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import com.wms.utils.FunctionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by duyot on 12/19/2016.
 */
@Service("stockManagementBusiness")
public class StockManagementBusinessImpl implements StockManagementBusinessInterface {
    @Autowired
    MjrStockGoodsTotalBusinessInterface mjrStockGoodsTotalBusiness;
    @Autowired
    BaseBusinessInterface mjrStockTransBusiness;
    @Autowired
    BaseBusinessInterface catGoodsBusiness;

    @Autowired
    BaseBusinessInterface err$MjrStockGoodsSerialBusiness;

    @Autowired
    MjrStockGoodsTotalBusinessInterface advancedMjrStockGoodsTotalBusiness;

    @Autowired
    StockBusinessWithConnectionInterface stockBusinessWithConnectionBusiness;

    @Autowired
    SessionFactory sessionFactory;

    Logger log = LoggerFactory.getLogger(StockManagementBusinessImpl.class);

    @Override
    public ResponseObject importStock(MjrStockTransDTO mjrStockTransDTO, List<MjrStockTransDetailDTO> lstMjrStockTransDetailDTO) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Connection connection = null;
        SessionFactory sessionFactoryBatch = session.getSessionFactory();
        //
        try {
            connection = sessionFactoryBatch.getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
            connection.setAutoCommit(false);
            //1. INSERT STOCK_TRANS
            String savedStockTransId = mjrStockTransBusiness.getSequence("SEQ_MJR_STOCK_TRANS")+"";
            mjrStockTransDTO = initMjrStockTransDTOInfo(mjrStockTransDTO,savedStockTransId);
            String insertStockTransResult = stockBusinessWithConnectionBusiness.saveStockTransByConnection(mjrStockTransDTO,connection);
            if(!Responses.SUCCESS.getName().equalsIgnoreCase(insertStockTransResult)){
                FunctionUtils.rollback(transaction,connection);
                return new ResponseObject(Responses.ERROR.getName(), Constants.RESULT_NAME.ERROR_CREATE_STOCK_TRANS,savedStockTransId);
            }
            log.info("Starting import transaction: "+ savedStockTransId + " with: "+ lstMjrStockTransDetailDTO.size() +" items");
            //2. INSERT TRANS_DETAIL -> TRANS_GOODS (SERIAL|GOODS)
            Map<String,CatGoodsDTO> mapImportingGoods = new HashMap<>();
            Map<String,Float>          mapGoodsAmount = new HashMap<>();
            //
            String strListGoodsCode;
            StringBuilder sbGoodsCode = new StringBuilder();
            for(MjrStockTransDetailDTO i: lstMjrStockTransDetailDTO){
                sbGoodsCode.append(i.getGoodsCode()).append(",");
                String key = i.getGoodsId() + "," + i.getGoodsState();
                if(mapGoodsAmount.containsKey(key)){
                    mapGoodsAmount.put(key,mapGoodsAmount.get(key)+ Float.valueOf(i.getAmount()));
                }else{
                    mapGoodsAmount.put(key,Float.valueOf(i.getAmount()));
                }
            }
            strListGoodsCode =  sbGoodsCode.substring(0,sbGoodsCode.lastIndexOf(",")) ;
            //
            List<Condition> lstCondition = Lists.newArrayList();
            lstCondition.add(new Condition("code",Constants.SQL_OPERATOR.IN,strListGoodsCode));
            List<CatGoodsDTO> lstImportCatGoods = catGoodsBusiness.findByCondition(lstCondition);
            //
            if(DataUtil.isListNullOrEmpty(lstImportCatGoods)){
                FunctionUtils.rollback(transaction,connection);
                return new ResponseObject(Responses.ERROR.getName(), Constants.RESULT_NAME.ERROR_NOT_VALID_GOODS_IN_REQUEST,savedStockTransId);
            }
            //
            for(CatGoodsDTO i: lstImportCatGoods){
                mapImportingGoods.put(i.getId(),i);
            }
            //
            String insertStockDetailResult = stockBusinessWithConnectionBusiness.saveStockDetailByConnection(mjrStockTransDTO,lstMjrStockTransDetailDTO,connection);
            if(!Responses.SUCCESS.getName().equalsIgnoreCase(insertStockDetailResult)){
                FunctionUtils.rollback(transaction,connection);
                return new ResponseObject(Responses.ERROR.getName(), Constants.RESULT_NAME.ERROR_CREATE_STOCK_TRANS_DETAIL,savedStockTransId);
            }
            log.info("Finished insert transdetail...");
            //3. UPDATE TOTAL
            List<Err$MjrStockGoodsSerialDTO> lstGoodsError = getListRecordsError(savedStockTransId);
            if(!DataUtil.isListNullOrEmpty(lstGoodsError)){
                //re-evalueate map goods-number
                Map<String,Float> mapGoodsNumberError = new HashMap<>();
                for(Err$MjrStockGoodsSerialDTO i: lstGoodsError){
                    String key = i.getGoodsId() + "," + i.getGoodsState();
                    if(mapGoodsAmount.containsKey(key)){
                        mapGoodsAmount.put(key,mapGoodsAmount.get(key)+ Float.valueOf(i.getAmount()));
                    }else{
                        mapGoodsAmount.put(key,Float.valueOf(i.getAmount()));
                    }
                }

                mapGoodsAmount = updateFinalTotal(mapGoodsAmount,mapGoodsNumberError);
            }else{
                log.info("No record error...");
            }
            String importTotalResult = stockBusinessWithConnectionBusiness.saveStockGoodsTotalByConnection(mjrStockTransDTO,mapGoodsAmount,mapImportingGoods,connection);
            if(!Responses.SUCCESS.getName().equalsIgnoreCase(importTotalResult)){
                log.info("Error update stock total...");
                FunctionUtils.rollback(transaction,connection);
                return new ResponseObject(Responses.ERROR.getName(), Constants.RESULT_NAME.ERROR_CREATE_TOTAL);
            }
            //4. Commit
            FunctionUtils.commit(transaction,connection);
            return new ResponseObject(Responses.SUCCESS.getName(),Responses.SUCCESS.getName(),savedStockTransId);

        } catch (Exception e) {
            e.printStackTrace();
            FunctionUtils.rollback(transaction,connection);
            return new ResponseObject(Responses.ERROR.getName(), Constants.RESULT_NAME.ERROR_SYSTEM);
        }finally {
            FunctionUtils.closeConnection(session,connection);
        }
    }

    @Override
    public ResponseObject exportStock(MjrStockTransDTO mjrStockTransDTO,List<MjrStockTransDetailDTO> lstMjrStockTransDetailDTO) {
        return null;
    }


    //@SUPPORT-FUNCTION-------------------------------------------------------------------------------------------------
    private String initTransCode(MjrStockTransDTO mjrStockTransDTO,String createdTime){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mjrStockTransDTO.getCustId())
                .append("_")
                .append(mjrStockTransDTO.getType())
                .append("_")
                .append(createdTime)
                .append("_")
                .append(FunctionUtils.randomString());
        return  stringBuilder.toString();
    }

    private Map<String,Float> updateFinalTotal(Map<String,Float> mapAll,Map<String,Float> mapError){
        Iterator it = mapError.entrySet().iterator();
        String errorKey;
        Float  errorAmount;
        Float currentAmount;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            //
            errorKey        = (String) pair.getKey();
            errorAmount     = (Float) pair.getValue();
            //get value in mapAll
            currentAmount = mapAll.get(errorKey);
            mapAll.put(errorKey,(currentAmount - errorAmount));
        }
        return mapAll;
    }

    private List<Err$MjrStockGoodsSerialDTO> getListRecordsError(String stockTransId){
        List<Condition> lstCon = Lists.newArrayList();
        lstCon.add(new Condition("importStockTransId",Constants.SQL_PRO_TYPE.LONG,Constants.SQL_OPERATOR.EQUAL,stockTransId));
        return err$MjrStockGoodsSerialBusiness.findByCondition(lstCon);
    }

    private MjrStockTransDTO initMjrStockTransDTOInfo(MjrStockTransDTO mjrStockTransDTO,String id){
        //
        String createdDate = mjrStockTransBusiness.getSysDate();
        String createdDatePartition = mjrStockTransBusiness.getSysDate("ddMMyyyy_hh24miss");
        //
        mjrStockTransDTO.setId(id);
        mjrStockTransDTO.setCreatedDate(createdDate);
        mjrStockTransDTO.setCode(initTransCode(mjrStockTransDTO,createdDatePartition));
        return mjrStockTransDTO;
    }
}
