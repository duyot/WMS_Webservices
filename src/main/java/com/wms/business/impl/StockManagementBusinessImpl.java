package com.wms.business.impl;

import com.google.common.collect.Lists;
import com.wms.base.BaseBusinessInterface;
import com.wms.business.interfaces.MjrStockGoodsTotalBusinessInterface;
import com.wms.business.interfaces.StockFunctionInterface;
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
import java.util.*;

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
    StockFunctionInterface stockFunctionBusiness;

    @Autowired
    SessionFactory sessionFactory;

    Logger log = LoggerFactory.getLogger(StockManagementBusinessImpl.class);

    @Override
    public ResponseObject importStock(MjrStockTransDTO mjrStockTransDTO, List<MjrStockTransDetailDTO> lstMjrStockTransDetailDTO) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setStatusCode(Responses.ERROR.getName());
        //
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Connection connection = null;
        SessionFactory sessionFactoryBatch = session.getSessionFactory();
        //
        float totalImportNumber = 0f;
        try {
            connection = sessionFactoryBatch.getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
            connection.setAutoCommit(false);
            //1. INSERT STOCK_TRANS
            String savedStockTransId = mjrStockTransBusiness.getSequence("SEQ_MJR_STOCK_TRANS")+"";
            mjrStockTransDTO.setId(savedStockTransId);
            mjrStockTransDTO = initMjrStockTransDTOInfo(mjrStockTransDTO);
            String insertStockTransResult = stockFunctionBusiness.saveStockTransByConnection(mjrStockTransDTO,connection);
            if(!Responses.SUCCESS.getName().equalsIgnoreCase(insertStockTransResult)){
                FunctionUtils.rollback(transaction,connection);
                responseObject.setStatusName(Responses.ERROR_CREATE_STOCK_TRANS.getName());
                responseObject.setKey(savedStockTransId);
                return responseObject;
            }
            //
            String savedStockTransCode = mjrStockTransDTO.getCode();
            responseObject.setKey(savedStockTransId);
            log.info("Starting import transaction: "+ savedStockTransCode + " with: "+ lstMjrStockTransDetailDTO.size() +" items");
            //2. INSERT TRANS_DETAIL -> TRANS_GOODS (SERIAL|GOODS)
            Map<String,CatGoodsDTO> mapImportingGoods = new HashMap<>();
            Map<String,Float>          mapGoodsAmount = new HashMap<>();
            //
            String strListGoodsCode;
            StringBuilder sbGoodsCode = new StringBuilder();
            Set<String> setGoodsCode = new HashSet<>();
            for(MjrStockTransDetailDTO i: lstMjrStockTransDetailDTO){
                setGoodsCode.add(i.getGoodsCode());
                String key = i.getGoodsId() + "," + i.getGoodsState();
                if(mapGoodsAmount.containsKey(key)){
                    mapGoodsAmount.put(key,mapGoodsAmount.get(key)+ Float.valueOf(i.getAmount()));
                }else{
                    mapGoodsAmount.put(key,Float.valueOf(i.getAmount()));
                }
                //
                totalImportNumber += Float.valueOf(i.getAmount());
            }
            //
            if(setGoodsCode.size()>999){
                FunctionUtils.rollback(transaction);
                responseObject.setStatusName(Responses.ERROR_OVER_GOODS_NUMBER.getName());
                return responseObject;
            }
            //
            for(String i: setGoodsCode){
                sbGoodsCode.append(i).append(",");
            }
            strListGoodsCode =  sbGoodsCode.substring(0,sbGoodsCode.lastIndexOf(",")) ;
            //
            List<Condition> lstCondition = Lists.newArrayList();
            lstCondition.add(new Condition("code",Constants.SQL_OPERATOR.IN,strListGoodsCode));
            List<CatGoodsDTO> lstImportCatGoods = catGoodsBusiness.findByCondition(lstCondition);
            //
            if(DataUtil.isListNullOrEmpty(lstImportCatGoods)){
                FunctionUtils.rollback(transaction,connection);
                responseObject.setStatusName(Responses.ERROR_NOT_VALID_GOODS_IN_REQUEST.getName());
                return responseObject;
            }
            //
            for(CatGoodsDTO i: lstImportCatGoods){
                mapImportingGoods.put(i.getId(),i);
            }
            //
            String insertStockDetailResult = stockFunctionBusiness.saveStockDetailByConnection(mjrStockTransDTO,lstMjrStockTransDetailDTO,connection);
            if(!Responses.SUCCESS.getName().equalsIgnoreCase(insertStockDetailResult)){
                FunctionUtils.rollback(transaction,connection);
                responseObject.setStatusName(Responses.ERROR_CREATE_STOCK_TRANS_DETAIL.getName());
                return responseObject;
            }
            log.info("Finished insert transdetail...");
            //3. UPDATE TOTAL
            List<Err$MjrStockGoodsSerialDTO> lstGoodsError = getListRecordsError(savedStockTransId);
            boolean isError = false;
            float totalErrorNumber = 0f;
            if(!DataUtil.isListNullOrEmpty(lstGoodsError)){
                //
                isError = true;
                //re-evalueate map goods-number
                log.info("Found "+ lstGoodsError.size() + " errors...");
                Map<String,Float> mapGoodsNumberError = new HashMap<>();
                for(Err$MjrStockGoodsSerialDTO i: lstGoodsError){
                    String key = i.getGoodsId() + "," + i.getGoodsState();
                    if(mapGoodsNumberError.containsKey(key)){
                        mapGoodsNumberError.put(key,mapGoodsNumberError.get(key)+ Float.valueOf(i.getAmount()));
                    }else{
                        mapGoodsNumberError.put(key,Float.valueOf(i.getAmount()));
                    }

                    totalErrorNumber += Float.valueOf(i.getAmount());
                }

                mapGoodsAmount = updateFinalTotal(mapGoodsAmount,mapGoodsNumberError);
            }else{
                log.info("No record error...");
            }
            String importTotalResult = stockFunctionBusiness.saveStockGoodsTotalByConnection(mjrStockTransDTO,mapGoodsAmount,mapImportingGoods,connection);
            if(!Responses.SUCCESS.getName().equalsIgnoreCase(importTotalResult)){
                log.info("Error update stock total...");
                FunctionUtils.rollback(transaction,connection);
                responseObject.setStatusName(Responses.ERROR_CREATE_TOTAL.getName());
                return responseObject;
            }
            //4. Commit
            FunctionUtils.commit(transaction,connection);
            if(isError){
                responseObject.setStatusCode(Responses.SUCCESS_WITH_ERROR.getName());
                responseObject.setTotal(totalImportNumber+"");
                responseObject.setSuccess((totalImportNumber - totalErrorNumber)+"");
                return responseObject;
            }else{
                responseObject.setStatusCode(Responses.SUCCESS.getName());
                responseObject.setTotal(totalImportNumber+"");
                responseObject.setSuccess(totalImportNumber+"");
                return responseObject;
            }

        } catch (Exception e) {
            e.printStackTrace();
            FunctionUtils.rollback(transaction,connection);
            responseObject.setStatusName(Responses.ERROR_SYSTEM.getName());
            return responseObject;
        }finally {
            FunctionUtils.closeConnection(session,connection);
        }
    }

    @Override
    public ResponseObject exportStock(MjrStockTransDTO mjrStockTransDTO,List<MjrStockTransDetailDTO> lstMjrStockTransDetailDTO) {
        ResponseObject response = new ResponseObject();
        response.setStatusCode(Responses.ERROR.getName());
        //
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        String savedStockTranId;
        String savedStockTranCode;
        float totalExport = 0f;
        try {
            //1. INSERT STOCK_TRANS
            mjrStockTransDTO = initMjrStockTransDTOInfo(mjrStockTransDTO);
            savedStockTranId = mjrStockTransBusiness.saveBySession(mjrStockTransDTO,session);
            if(!DataUtil.isInteger(savedStockTranId)){
                FunctionUtils.rollback(transaction);
                response.setStatusName(Responses.ERROR_CREATE_STOCK_TRANS.getName());
                response.setKey(savedStockTranId);
                return response;
            }
            mjrStockTransDTO.setId(savedStockTranId);
            savedStockTranCode = mjrStockTransDTO.getCode();
            log.info("Starting export transaction: "+ savedStockTranCode + " with: "+ lstMjrStockTransDetailDTO.size() +" items");
            response.setKey(savedStockTranId);
            //2. INSERT TRANS_DETAIL|STOCK(GOODS|SERIAL)
            Map<String,CatGoodsDTO> mapImportingGoods = new HashMap<>();
            Map<String,Float>          mapGoodsAmount = new HashMap<>();
            //
            String strListGoodsCode;
            StringBuilder sbGoodsCode = new StringBuilder();
            if(DataUtil.isListNullOrEmpty(lstMjrStockTransDetailDTO)){
                FunctionUtils.rollback(transaction);
                response.setStatusName(Responses.ERROR_NOT_FOUND_GOODS.getName());
                return response;
            }
            //
            Set<String> setGoodsCode = new HashSet<>();
            for(MjrStockTransDetailDTO i: lstMjrStockTransDetailDTO){
                setGoodsCode.add(i.getGoodsCode());
                String key = i.getGoodsId() + "," + i.getGoodsState();
                if(mapGoodsAmount.containsKey(key)){
                    mapGoodsAmount.put(key,mapGoodsAmount.get(key)+ Float.valueOf(i.getAmount()));
                }else{
                    mapGoodsAmount.put(key,Float.valueOf(i.getAmount()));
                }
                //
                try {
                    totalExport += Float.valueOf(i.getAmount());
                } catch (NumberFormatException e) {
                    FunctionUtils.rollback(transaction);
                    response.setStatusName(Responses.ERROR_AMOUNT_NOT_VALID.getName());
                    response.setKey(i.getAmount());
                    return response;
                }
            }
            //
            if(setGoodsCode.size()>999){
                FunctionUtils.rollback(transaction);
                response.setStatusName(Responses.ERROR_OVER_GOODS_NUMBER.getName());
                return response;
            }
            //
            for(String i: setGoodsCode){
                sbGoodsCode.append(i).append(",");
            }
            strListGoodsCode =  sbGoodsCode.substring(0,sbGoodsCode.lastIndexOf(",")) ;
            //
            List<Condition> lstCondition = Lists.newArrayList();
            lstCondition.add(new Condition("code",Constants.SQL_OPERATOR.IN,strListGoodsCode));
            List<CatGoodsDTO> lstImportCatGoods = catGoodsBusiness.findByCondition(lstCondition);
            //
            if(DataUtil.isListNullOrEmpty(lstImportCatGoods)){
                FunctionUtils.rollback(transaction);
                response.setStatusName(Responses.ERROR_NOT_VALID_GOODS_IN_REQUEST.getName());
                return response;
            }
            //
            for(CatGoodsDTO i: lstImportCatGoods){
                mapImportingGoods.put(i.getId(),i);
            }
            //
            ResponseObject transDetailResult;
            for(MjrStockTransDetailDTO i: lstMjrStockTransDetailDTO){
                transDetailResult = stockFunctionBusiness.exportStockGoodsDetail(mjrStockTransDTO,i,session);
                if(!Responses.SUCCESS.getName().equalsIgnoreCase(transDetailResult.getStatusCode())){
                    FunctionUtils.rollback(transaction);
                    return transDetailResult;
                }
        }
            log.info("Finished export "+ lstMjrStockTransDetailDTO.size() + " items.");
            //3. UPDATE TOTAL
            ResponseObject updateExportTotalResult = stockFunctionBusiness.updateExportStockGoodsTotal(mjrStockTransDTO,mapGoodsAmount,session);
            if(!Responses.SUCCESS.getName().equalsIgnoreCase(updateExportTotalResult.getStatusCode())){
                log.info("Error update stock total...");
                FunctionUtils.rollback(transaction);
                return updateExportTotalResult;
            }
            //4. Commit
            FunctionUtils.commit(transaction);
            //
            response.setStatusCode(Responses.SUCCESS.getName());
            response.setTotal(totalExport+"");
            response.setSuccess(totalExport+"");
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            FunctionUtils.rollback(transaction);
            response.setStatusName(Responses.ERROR_SYSTEM.getName());
            return response;
        } finally {
            FunctionUtils.closeSession(session);
        }

    }
    //@SUPPORT-FUNCTION-------------------------------------------------------------------------------------------------
    private String initTransCode(MjrStockTransDTO mjrStockTransDTO,String createdTime){
        StringBuilder stringBuilder = new StringBuilder();
        if(Constants.TRANSACTION_TYPE.IMPORT.equalsIgnoreCase(mjrStockTransDTO.getType())){
            stringBuilder.append("IMP");
        }else{
            stringBuilder.append("EXP");
        }
        stringBuilder.append("_").append(mjrStockTransDTO.getCustId())
                .append("_")
                .append(createdTime)
                .append("_")
                .append(FunctionUtils.randomString());
        return  stringBuilder.toString();
    }

    private Map<String,Float> updateFinalTotal(Map<String,Float> mapAllImportAmount,Map<String,Float> mapAmountError){
        Iterator it = mapAmountError.entrySet().iterator();
        String errorKey;
        Float  errorAmount;
        Float currentAmount;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            //
            errorKey        = (String) pair.getKey();
            errorAmount     = (Float) pair.getValue();
            //get value in mapAllImportAmount
            currentAmount = mapAllImportAmount.get(errorKey);
            mapAllImportAmount.put(errorKey,(currentAmount - errorAmount));
        }
        return mapAllImportAmount;
    }

    private List getListRecordsError(String stockTransId){
        List<Condition> lstCon = Lists.newArrayList();
        lstCon.add(new Condition("importStockTransId",Constants.SQL_PRO_TYPE.LONG,Constants.SQL_OPERATOR.EQUAL,stockTransId));
        return err$MjrStockGoodsSerialBusiness.findByCondition(lstCon);
    }

    private MjrStockTransDTO initMjrStockTransDTOInfo(MjrStockTransDTO mjrStockTransDTO){
        //
        String createdDate = mjrStockTransBusiness.getSysDate();
        String createdDatePartition = mjrStockTransBusiness.getSysDate("ddMMyyyy_hh24miss");
        //
        mjrStockTransDTO.setCreatedDate(createdDate);
        mjrStockTransDTO.setCode(initTransCode(mjrStockTransDTO,createdDatePartition));
        return mjrStockTransDTO;
    }
}
