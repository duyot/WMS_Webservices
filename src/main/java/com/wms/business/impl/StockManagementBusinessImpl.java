package com.wms.business.impl;

import com.google.common.collect.Lists;
import com.wms.base.BaseBusinessInterface;
import com.wms.business.interfaces.RevenueBusinessInterface;
import com.wms.business.interfaces.StockFunctionInterface;
import com.wms.business.interfaces.StockManagementBusinessInterface;
import com.wms.dto.*;
import com.wms.enums.Responses;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import com.wms.utils.FunctionUtils;
import java.sql.Connection;
import java.util.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duyot on 12/19/2016.
 */
@Service("stockManagementBusiness")
public class StockManagementBusinessImpl implements StockManagementBusinessInterface {
    @Autowired
    BaseBusinessInterface mjrStockTransBusiness;
    @Autowired
    BaseBusinessInterface catGoodsBusiness;

    @Autowired
    BaseBusinessInterface err$MjrStockGoodsSerialBusiness;

    @Autowired
    BaseBusinessInterface catStockBusiness;

    @Autowired
    StockFunctionInterface stockFunctionBusiness;

    @Autowired
    BaseBusinessInterface catPartnerBusiness;

    @Autowired
    RevenueBusinessInterface revenueBusiness;

    @Autowired
    SessionFactory sessionFactory;

    private Logger log = LoggerFactory.getLogger(StockManagementBusinessImpl.class);

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
            mjrStockTransDTO = saveStockTrans(mjrStockTransDTO, connection);
            if (mjrStockTransDTO == null) {
                FunctionUtils.rollback(transaction, connection);
                responseObject.setStatusName(Responses.ERROR_CREATE_STOCK_TRANS.getName());
                return responseObject;
            }
            //
            String savedStockTransCode = mjrStockTransDTO.getCode();
            String savedStockTransId = mjrStockTransDTO.getId();
            responseObject.setKey(savedStockTransId + "|" + savedStockTransCode);
            log.info("Starting import to stock: " + mjrStockTransDTO.getStockId() + " trans code: " + savedStockTransCode + " with: " + lstMjrStockTransDetailDTO.size() + " items");
            FunctionUtils.writeIEGoodsLog(lstMjrStockTransDetailDTO, log);
            //2. INSERT TRANS_DETAIL -> TRANS_GOODS (SERIAL|GOODS)
            Map<String, CatGoodsDTO> mapImportingGoods = new HashMap<>();
            Map<String, Float> mapGoodsAmount = new HashMap<>();
            //
            Set<String> setGoodsCode = new HashSet<>();
            for (MjrStockTransDetailDTO i : lstMjrStockTransDetailDTO) {
                setGoodsCode.add(i.getGoodsCode());
                String key = i.getGoodsId() + "," + i.getGoodsState();
                if (mapGoodsAmount.containsKey(key)) {
                    mapGoodsAmount.put(key, mapGoodsAmount.get(key) + Float.valueOf(i.getAmount()));
                } else {
                    mapGoodsAmount.put(key, Float.valueOf(i.getAmount()));
                }
                //
                totalImportNumber += Float.valueOf(i.getAmount());
            }
            //
            if (setGoodsCode.size() > 999) {
                FunctionUtils.rollback(transaction);
                responseObject.setStatusName(Responses.ERROR_OVER_GOODS_NUMBER.getName());
                return responseObject;
            }
            //
            String strListGoodsCode = getStrListGoodsCode(setGoodsCode);
            //
            List<CatGoodsDTO> lstImportCatGoods = getGoodsFromCode(strListGoodsCode, mjrStockTransDTO.getCustId());
            //
            if (DataUtil.isListNullOrEmpty(lstImportCatGoods)) {
                FunctionUtils.rollback(transaction, connection);
                responseObject.setStatusName(Responses.ERROR_NOT_VALID_GOODS_IN_REQUEST.getName());
                return responseObject;
            }
            //
            for (CatGoodsDTO i : lstImportCatGoods) {
                mapImportingGoods.put(i.getId(), i);
            }
            //
            String insertStockDetailResult = stockFunctionBusiness.saveStockDetailByConnection(mjrStockTransDTO, lstMjrStockTransDetailDTO, connection);
            if (!Responses.SUCCESS.getName().equalsIgnoreCase(insertStockDetailResult)) {
                FunctionUtils.rollback(transaction, connection);
                responseObject.setStatusName(Responses.ERROR_CREATE_STOCK_TRANS_DETAIL.getName());
                return responseObject;
            }
            log.info("Finished insert trans_detail...");
            //3. UPDATE TOTAL
            List<Err$MjrStockGoodsSerialDTO> lstGoodsError = getListRecordsError(savedStockTransId);
            boolean isError = false;
            float totalErrorNumber = 0f;
            if (!DataUtil.isListNullOrEmpty(lstGoodsError)) {
                //
                isError = true;
                //re-evaluated map goods-number
                log.info("Found " + lstGoodsError.size() + " errors...");
                Map<String, Float> mapGoodsNumberError = new HashMap<>();
                for (Err$MjrStockGoodsSerialDTO i : lstGoodsError) {
                    String key = i.getGoodsId() + "," + i.getGoodsState();
                    if (mapGoodsNumberError.containsKey(key)) {
                        mapGoodsNumberError.put(key, mapGoodsNumberError.get(key) + Float.valueOf(i.getAmount()));
                    } else {
                        mapGoodsNumberError.put(key, Float.valueOf(i.getAmount()));
                    }

                    totalErrorNumber += Float.valueOf(i.getAmount());
                }

                mapGoodsAmount = updateFinalTotal(mapGoodsAmount, mapGoodsNumberError);
            } else {
                log.info("No record error...");
            }
            String importTotalResult = stockFunctionBusiness.saveStockGoodsTotalByConnection(mjrStockTransDTO, mapGoodsAmount, mapImportingGoods, connection);
            if (!Responses.SUCCESS.getName().equalsIgnoreCase(importTotalResult)) {
                log.info("Error update stock total...");
                FunctionUtils.rollback(transaction, connection);
                responseObject.setStatusName(Responses.ERROR_CREATE_TOTAL.getName());
                return responseObject;
            }
            //4. Commit
            FunctionUtils.commit(transaction, connection);
            if (isError) {
                responseObject.setStatusCode(Responses.SUCCESS_WITH_ERROR.getName());
                responseObject.setTotal(totalImportNumber + "");
                responseObject.setSuccess((totalImportNumber - totalErrorNumber) + "");
                return responseObject;
            } else {
                responseObject.setStatusCode(Responses.SUCCESS.getName());
                responseObject.setTotal(totalImportNumber + "");
                responseObject.setSuccess(totalImportNumber + "");
                return responseObject;
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.toString());
            FunctionUtils.rollback(transaction, connection);
            responseObject.setStatusName(Responses.ERROR_SYSTEM.getName());
            return responseObject;
        } finally {
            FunctionUtils.closeConnection(session, connection);
        }
    }

    @Override
    public ResponseObject exportStock(MjrStockTransDTO mjrStockTransDTO, List<MjrStockTransDetailDTO> lstMjrStockTransDetailDTO) {
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
            mjrStockTransDTO = initMjrStockTransDTOInfo(mjrStockTransDTO, session, null);
            savedStockTranId = mjrStockTransBusiness.saveBySession(mjrStockTransDTO, session);
            if (!DataUtil.isInteger(savedStockTranId)) {
                FunctionUtils.rollback(transaction);
                response.setStatusName(Responses.ERROR_CREATE_STOCK_TRANS.getName());
                response.setKey(savedStockTranId);
                return response;
            }
            mjrStockTransDTO.setId(savedStockTranId);
            savedStockTranCode = mjrStockTransDTO.getCode();
            response.setKey(savedStockTranCode);
            log.info("Starting export from stock:" + mjrStockTransDTO.getStockId() + " code: " + savedStockTranCode + " with: " + lstMjrStockTransDetailDTO.size() + " items");
            FunctionUtils.writeIEGoodsLog(lstMjrStockTransDetailDTO, log);
            //2. INSERT TRANS_DETAIL|STOCK(GOODS|SERIAL)
            if (DataUtil.isListNullOrEmpty(lstMjrStockTransDetailDTO)) {
                FunctionUtils.rollback(transaction);
                response.setStatusName(Responses.ERROR_NOT_FOUND_GOODS.getName());
                return response;
            }
            //
            Map<String, CatGoodsDTO> mapImportingGoods = new HashMap<>();
            Map<String, Float> mapGoodsAmount = new HashMap<>();
            Set<String> setGoodsCode = new HashSet<>();
            //
            for (MjrStockTransDetailDTO i : lstMjrStockTransDetailDTO) {
                setGoodsCode.add(i.getGoodsCode());
                String key = i.getGoodsId() + "," + i.getGoodsState();
                if (mapGoodsAmount.containsKey(key)) {
                    mapGoodsAmount.put(key, mapGoodsAmount.get(key) + Float.valueOf(i.getAmount()));
                } else {
                    mapGoodsAmount.put(key, Float.valueOf(i.getAmount()));
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
            if (setGoodsCode.size() > 999) {
                FunctionUtils.rollback(transaction);
                response.setStatusName(Responses.ERROR_OVER_GOODS_NUMBER.getName());
                return response;
            }
            //
            String strListGoodsCode = getStrListGoodsCode(setGoodsCode);
            //
            List<CatGoodsDTO> lstImportCatGoods = getGoodsFromCode(strListGoodsCode, mjrStockTransDTO.getCustId());
            //
            if (DataUtil.isListNullOrEmpty(lstImportCatGoods)) {
                FunctionUtils.rollback(transaction);
                response.setStatusName(Responses.ERROR_NOT_VALID_GOODS_IN_REQUEST.getName());
                return response;
            }
            //
            for (CatGoodsDTO i : lstImportCatGoods) {
                mapImportingGoods.put(i.getId(), i);
            }
            //
            ResponseObject transDetailResult;
            for (MjrStockTransDetailDTO i : lstMjrStockTransDetailDTO) {
                transDetailResult = stockFunctionBusiness.exportStockGoodsDetail(mjrStockTransDTO, i, session);
                if (!Responses.SUCCESS.getName().equalsIgnoreCase(transDetailResult.getStatusCode())) {
                    FunctionUtils.rollback(transaction);
                    return transDetailResult;
                }
            }
            log.info("Finished export " + lstMjrStockTransDetailDTO.size() + " items.");
            //3. UPDATE TOTAL
            ResponseObject updateExportTotalResult = stockFunctionBusiness.updateExportStockGoodsTotal(mjrStockTransDTO, mapGoodsAmount, session);
            if (!Responses.SUCCESS.getName().equalsIgnoreCase(updateExportTotalResult.getStatusCode())) {
                log.info("Error update stock total...");
                FunctionUtils.rollback(transaction);
                return updateExportTotalResult;
            }
            //4. Update Revenue
            RevenueDTO revenueDTO = new RevenueDTO();
            revenueDTO.setCustId(mjrStockTransDTO.getCustId());
            revenueDTO.setStockTransCode(mjrStockTransDTO.getCode());
            revenueDTO.setStockTransId(mjrStockTransDTO.getId());
            revenueDTO.setPartnerId(mjrStockTransDTO.getReceiveId());
            revenueDTO.setDescription(mjrStockTransDTO.getDescription());
            revenueDTO.setCreatedDate(mjrStockTransDTO.getCreatedDate());
            revenueDTO.setCreatedUser(mjrStockTransDTO.getCreatedUser());
            revenueDTO.setAmount(mjrStockTransDTO.getTransMoneyTotal());
            revenueDTO.setVat("-1");
            revenueDTO.setType("1");
            revenueDTO.setCharge("0");
            revenueBusiness.saveBySession(revenueDTO, session);

            //5. Commit
            FunctionUtils.commit(transaction);
            //
            response.setStatusCode(Responses.SUCCESS.getName());
            response.setTotal(totalExport + "");
            response.setSuccess(totalExport + "");
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


    private boolean isEmptyRequest(MjrStockTransDTO mjrStockTransDTO, List<MjrStockTransDetailDTO> lstMjrStockTransDetailDTO) {
        return mjrStockTransDTO == null || DataUtil.isListNullOrEmpty(lstMjrStockTransDetailDTO);
    }

    private CatPartnerDTO getPartner(MjrStockTransDTO transDetail) {
        String partnerId = transDetail.getPartnerId();
        if (DataUtil.isStringNullOrEmpty(partnerId)) {
            return null;
        }
        //
        List<Condition> lstCon = Lists.newArrayList();
        lstCon.add(new Condition("custId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, transDetail.getCustId()));
        lstCon.add(new Condition("status", Constants.SQL_PRO_TYPE.BYTE, Constants.SQL_OPERATOR.EQUAL, Constants.STATUS.ACTIVE));
        if (!DataUtil.isStringNullOrEmpty(transDetail.getPartnerId())) {
            lstCon.add(new Condition("id", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, transDetail.getPartnerId()));
        }
        //
        List<CatPartnerDTO> lstCatPartner = catPartnerBusiness.findByCondition(lstCon);
        if (DataUtil.isListNullOrEmpty(lstCatPartner)) {
            return null;
        }
        //
        return lstCatPartner.get(0);
    }

    private CatStockDTO getStock(String stockId, String custId) {
        if (DataUtil.isStringNullOrEmpty(stockId) || !DataUtil.isInteger(stockId)) {
            return null;
        }
        //
        List<Condition> lstCon = Lists.newArrayList();
        lstCon.add(new Condition("custId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, custId));
        lstCon.add(new Condition("status", Constants.SQL_PRO_TYPE.BYTE, Constants.SQL_OPERATOR.EQUAL, Constants.STATUS.ACTIVE));
        lstCon.add(new Condition("id", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, stockId));
        //
        List<CatStockDTO> stocks = catStockBusiness.findByCondition(lstCon);
        if (DataUtil.isListNullOrEmpty(stocks)) {
            return null;
        }
        //
        return stocks.get(0);
    }

    //@SUPPORT-FUNCTION-------------------------------------------------------------------------------------------------
    private Map<String, Float> updateFinalTotal(Map<String, Float> mapAllImportAmount, Map<String, Float> mapAmountError) {
        Iterator it = mapAmountError.entrySet().iterator();
        String errorKey;
        Float errorAmount;
        Float currentAmount;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            //
            errorKey = (String) pair.getKey();
            errorAmount = (Float) pair.getValue();
            //get value in mapAllImportAmount
            currentAmount = mapAllImportAmount.get(errorKey);
            mapAllImportAmount.put(errorKey, (currentAmount - errorAmount));
        }
        return mapAllImportAmount;
    }

    private List getListRecordsError(String stockTransId) {
        List<Condition> lstCon = Lists.newArrayList();
        lstCon.add(new Condition("importStockTransId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, stockTransId));
        return err$MjrStockGoodsSerialBusiness.findByCondition(lstCon);
    }

    private MjrStockTransDTO initMjrStockTransDTOInfo(MjrStockTransDTO mjrStockTransDTO, Session session, Connection con) {
        //
        String createdDate = mjrStockTransBusiness.getSysDate();
        String createdDatePartition = mjrStockTransBusiness.getSysDate("ddMMyyyy");
        //
        mjrStockTransDTO.setCreatedDate(createdDate);
        mjrStockTransDTO.setCode(initTransCode(mjrStockTransDTO, createdDatePartition, session, con));
        return mjrStockTransDTO;
    }

    //
    private String initTransCode(MjrStockTransDTO mjrStockTransDTO, String createdTime, Session session, Connection con) {
        //
        boolean isImport = Constants.TRANSACTION_TYPE.IMPORT.equalsIgnoreCase(mjrStockTransDTO.getType());
        //
        String stockCode = "";
        CatStockDTO stock = (CatStockDTO) catStockBusiness.findById(Long.parseLong(mjrStockTransDTO.getStockId()));
        if (stock != null) {
            stockCode = stock.getCode();
        }
        //
        StringBuilder stringBuilder = new StringBuilder();
        if (isImport) {
            stringBuilder.append("PNK");
        } else {
            stringBuilder.append("PXK");
        }
        stringBuilder.append("/").append(stockCode)
                .append("/")
                .append(createdTime)
                .append("/");
        if (isImport) {
            stringBuilder.append(stockFunctionBusiness.getTotalStockTransaction(mjrStockTransDTO.getStockId(), con));
        } else {
            stringBuilder.append(stockFunctionBusiness.getTotalStockTransaction(mjrStockTransDTO.getStockId(), session));
        }
        return stringBuilder.toString();
    }

    private MjrStockTransDTO saveStockTrans(MjrStockTransDTO mjrStockTransDTO, Connection connection) {
        if (mjrStockTransDTO == null || connection == null) {
            return null;
        }
        //
        String savedStockTransId = mjrStockTransBusiness.getSequence("SEQ_MJR_STOCK_TRANS") + "";
        mjrStockTransDTO.setId(savedStockTransId);
        mjrStockTransDTO = initMjrStockTransDTOInfo(mjrStockTransDTO, null, connection);
        String insertStockTransResult = stockFunctionBusiness.saveStockTransByConnection(mjrStockTransDTO, connection);
        if (!Responses.SUCCESS.getName().equalsIgnoreCase(insertStockTransResult)) {
            return null;
        } else {
            return mjrStockTransDTO;
        }
    }

    /*
        inp: set A -> B -> C
        out: str: A,B,C
     */
    private String getStrListGoodsCode(Set<String> setGoodsCode) {
        StringBuilder sbGoodsCode = new StringBuilder();
        for (String i : setGoodsCode) {
            sbGoodsCode.append(i).append(",");
        }
        return sbGoodsCode.substring(0, sbGoodsCode.lastIndexOf(","));
    }

    private List getGoodsFromCode(String strListGoodsCode, String custId) {
        List<Condition> lstCondition = Lists.newArrayList();
        lstCondition.add(new Condition("code", Constants.SQL_OPERATOR.IN, strListGoodsCode));
        lstCondition.add(new Condition("custId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, custId));
        return catGoodsBusiness.findByCondition(lstCondition);
    }


}
