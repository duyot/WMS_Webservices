package com.wms.business.impl;

import com.google.common.collect.Lists;
import com.wms.base.BaseBusinessInterface;
import com.wms.business.interfaces.MjrStockGoodsTotalBusinessInterface;
import com.wms.business.interfaces.StockManagementBusinessInterface;
import com.wms.dto.*;
import com.wms.enums.Responses;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import com.wms.utils.FunctionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    BaseBusinessInterface mjrStockTransDetailBusiness;
    @Autowired
    BaseBusinessInterface mjrStockGoodsBusiness;
    @Autowired
    BaseBusinessInterface mjrStockGoodsSerialBusiness;
    @Autowired
    BaseBusinessInterface catGoodsBusiness;

    @Autowired
    MjrStockGoodsTotalBusinessInterface advancedMjrStockGoodsTotalBusiness;

    @Autowired
    SessionFactory sessionFactory;

    Logger log = LoggerFactory.getLogger(StockManagementBusinessImpl.class);

    private String initTransCode(MjrStockTransDTO mjrStockTransDTO,String createdTime){
        return mjrStockTransDTO.getCustId() + "_" + mjrStockTransDTO.getType() + "_"+ createdTime;
    }

    private Map getMapGoods(List<MjrStockTransDetailDTO> lstGoods){
        //
        String strListGoodsCode = getStrListGoodsCode(lstGoods);
        List<Condition> lstCondition = Lists.newArrayList();
        lstCondition.add(new Condition("code",Constants.SQL_OPERATOR.IN,strListGoodsCode));
        List<CatGoodsDTO> lstImportCatGoods = catGoodsBusiness.findByCondition(lstCondition);
        //
        if(DataUtil.isListNullOrEmpty(lstImportCatGoods)){
            return new HashMap();
        }
        //
        Map<String,CatGoodsDTO> mapGoods = new HashMap();
        for(CatGoodsDTO i: lstImportCatGoods){
            mapGoods.put(i.getCode(),i);
        }
        //
        return mapGoods;
    }

    private String getStrListGoodsCode(List<MjrStockTransDetailDTO> lstGoods){
        StringBuilder stringBuilder = new StringBuilder();
        for(MjrStockTransDetailDTO i: lstGoods){
            stringBuilder.append(i.getGoodsCode()).append(",");
        }
        return stringBuilder.substring(0,stringBuilder.lastIndexOf(",")) ;
    };

    @Override
    public ResponseObject importStock(MjrStockTransDTO mjrStockTransDTO, List<MjrStockTransDetailDTO> lstMjrStockTransDetailDTO) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        //
        String createdDate = mjrStockTransBusiness.getSysDate();
        String createdDatePartition = mjrStockTransBusiness.getSysDate("ddMMyyyy_hh24miss");

        try {
            //
            mjrStockTransDTO.setCreatedDate(createdDate);
            mjrStockTransDTO.setCode(initTransCode(mjrStockTransDTO,createdDatePartition));
            //
            String savedStockTransId = mjrStockTransBusiness.saveBySession(mjrStockTransDTO,session);
            if(!DataUtil.isInteger(savedStockTransId)){
                FunctionUtils.rollBack(transaction);
                return new ResponseObject(Responses.ERROR.getName(), Constants.RESULT_NAME.ERROR_CREATE_STOCK_TRANS,savedStockTransId);
            }
            mjrStockTransDTO.setId(savedStockTransId);
            //get map list of import gooods
            Map<String,CatGoodsDTO> mapGoods = getMapGoods(lstMjrStockTransDetailDTO);
            if(mapGoods.size() == 0){
                return new ResponseObject(Responses.ERROR.getName(), Constants.RESULT_NAME.ERROR_NOT_VALID_GOODS_IN_REQUEST,savedStockTransId);
            }
            //
            for (MjrStockTransDetailDTO i: lstMjrStockTransDetailDTO) {
                //
                i.setStockTransId(savedStockTransId);
                CatGoodsDTO goods = mapGoods.get(i.getGoodsCode());
                if(goods == null){
                    return new ResponseObject(Responses.ERROR.getName(), Constants.RESULT_NAME.ERROR_GOODS_INFO_NOT_VALID,i.getGoodsCode());
                }

                i.setGoodsId(goods.getId());
                i.setIsSerial(goods.getIsSerial());
                //
                String savedStockTransDetailId = mjrStockTransDetailBusiness.saveBySession(i,session);
                if(Responses.ERROR.getName().equalsIgnoreCase(savedStockTransDetailId)){
                    log.info("Error stock_trans_detail:  "+"stockTransId|"+savedStockTransDetailId+"goodsCode|"+i.getGoodsCode());
                    FunctionUtils.rollBack(transaction);
                    return new ResponseObject(Responses.ERROR.getName(), Constants.RESULT_NAME.ERROR_CREATE_STOCK_TRANS_DETAIL);
                }
                //update stock_goods||stock_goods_serial
                String importStockGoodsResult;
                if(Constants.IS_SERIAL.equalsIgnoreCase(i.getIsSerial())){
                    importStockGoodsResult = importMjrStockGoodsSerial(mjrStockTransDTO,i,session);
                }else{
                    importStockGoodsResult = importMjrStockGoods(mjrStockTransDTO,i,session);
                }
                if(!DataUtil.isInteger(importStockGoodsResult)){
                    log.info("Error stock_goods:  "+"stockTransId|"+savedStockTransDetailId+"goodsCode|"+i.getGoodsCode());
                    FunctionUtils.rollBack(transaction);
                    return new ResponseObject(Responses.ERROR.getName(), importStockGoodsResult,i.getSerial());
                }
                //update total
                String importTotalResult = importMjrStockGoodsTotal(mjrStockTransDTO,i,session);
                if(Responses.ERROR.getName().equalsIgnoreCase(importTotalResult)){
                    log.info("Error stock_total:  "+"stockTransId|"+savedStockTransDetailId+"goodsCode|"+i.getGoodsCode());
                    FunctionUtils.rollBack(transaction);
                    return new ResponseObject(Responses.ERROR.getName(), Constants.RESULT_NAME.ERROR_CREATE_TOTAL);
                }
            }

            transaction.commit();
            return new ResponseObject(Responses.SUCCESS.getName(),"",savedStockTransId);
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return new ResponseObject(Responses.ERROR.getName(), Constants.RESULT_NAME.ERROR_SYSTEM);
        }finally {
            FunctionUtils.closeSession(session);
        }
    }

    @Override
    public ResponseObject exportStock(MjrStockTransDTO mjrStockTransDTO,List<MjrStockTransDetailDTO> lstMjrStockTransDetailDTO) {
        return null;
    }

    private String importMjrStockGoodsSerial(MjrStockTransDTO stockTransDTO, MjrStockTransDetailDTO stockTransDetailDTO,Session session){
        MjrStockGoodsSerialDTO stockGoodsSerialDTO = new MjrStockGoodsSerialDTO();

        stockGoodsSerialDTO.setCustId(stockTransDTO.getCustId());
        stockGoodsSerialDTO.setStockId(stockTransDTO.getStockId());
        stockGoodsSerialDTO.setGoodsId(stockTransDetailDTO.getGoodsId());
        stockGoodsSerialDTO.setGoodsState(stockTransDetailDTO.getGoodsState());
        stockGoodsSerialDTO.setCellCode(stockTransDetailDTO.getCellCode());
        stockGoodsSerialDTO.setAmount(stockTransDetailDTO.getAmount());
        stockGoodsSerialDTO.setSerial(stockTransDetailDTO.getSerial());
        stockGoodsSerialDTO.setImportDate(stockTransDTO.getCreatedDate());
        stockGoodsSerialDTO.setChangeDate(stockTransDTO.getCreatedDate());
        stockGoodsSerialDTO.setStatus(Constants.STATUS.ACTIVE);
        stockGoodsSerialDTO.setImportStockTransId(stockTransDTO.getId());
        stockGoodsSerialDTO.setInputPrice(stockTransDetailDTO.getInputPrice());
        stockGoodsSerialDTO.setOutputPrice(stockTransDetailDTO.getOutputPrice());

        return mjrStockGoodsBusiness.saveBySession(stockGoodsSerialDTO,session);
    }

    private String importMjrStockGoods(MjrStockTransDTO stockTransDTO, MjrStockTransDetailDTO stockTransDetailDTO,Session session){
        MjrStockGoodsDTO stockGoodsSerialDTO = new MjrStockGoodsDTO();

        stockGoodsSerialDTO.setCustId(stockTransDTO.getCustId());
        stockGoodsSerialDTO.setStockId(stockTransDTO.getStockId());
        stockGoodsSerialDTO.setGoodsId(stockTransDetailDTO.getGoodsId());
        stockGoodsSerialDTO.setGoodsState(stockTransDetailDTO.getGoodsState());
        stockGoodsSerialDTO.setCellCode(stockTransDetailDTO.getCellCode());
        stockGoodsSerialDTO.setAmount(stockTransDetailDTO.getAmount());
        stockGoodsSerialDTO.setImportDate(stockTransDTO.getCreatedDate());
        stockGoodsSerialDTO.setChangeDate(stockTransDTO.getCreatedDate());
        stockGoodsSerialDTO.setStatus(Constants.STATUS.ACTIVE);
        stockGoodsSerialDTO.setImportStockTransId(stockTransDTO.getId());
        stockGoodsSerialDTO.setInputPrice(stockTransDetailDTO.getInputPrice());
        stockGoodsSerialDTO.setOutputPrice(stockTransDetailDTO.getOutputPrice());

        return mjrStockGoodsSerialBusiness.saveBySession(stockGoodsSerialDTO,session);
    }

    private String importMjrStockGoodsTotal(MjrStockTransDTO mjrStockTransDTO,MjrStockTransDetailDTO mjrStockTransDetailDTO,Session session){
        MjrStockGoodsTotalDTO stockGoodsTotal = new MjrStockGoodsTotalDTO();

        stockGoodsTotal.setCustId(mjrStockTransDTO.getCustId());
        stockGoodsTotal.setStockId(mjrStockTransDTO.getStockId());
        stockGoodsTotal.setGoodsId(mjrStockTransDetailDTO.getGoodsId());
        stockGoodsTotal.setGoodsCode(mjrStockTransDetailDTO.getGoodsCode());
        stockGoodsTotal.setGoodsName(mjrStockTransDetailDTO.getGoodsName());
        stockGoodsTotal.setGoodsState(mjrStockTransDetailDTO.getGoodsState());
        stockGoodsTotal.setAmount(mjrStockTransDetailDTO.getAmount());
        stockGoodsTotal.setChangeDate(mjrStockTransDTO.getCreatedDate());

        return mjrStockGoodsTotalBusiness.updateSession(stockGoodsTotal,session);
    }

}
