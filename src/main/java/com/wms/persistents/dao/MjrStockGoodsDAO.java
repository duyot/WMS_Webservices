package com.wms.persistents.dao;

import com.google.common.collect.Lists;
import com.wms.base.BaseDAOImpl;
import com.wms.business.impl.StockManagementBusinessImpl;
import com.wms.dto.*;
import com.wms.enums.Responses;
import com.wms.persistents.model.MjrStockGoods;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.FunctionUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 1/3/2017.
 */
@Repository
@Transactional
public class MjrStockGoodsDAO extends BaseDAOImpl<MjrStockGoods, Long> {

    Logger log = LoggerFactory.getLogger(StockManagementBusinessImpl.class);

    public ResponseObject exportStockGoods(MjrStockTransDTO mjrStockTransDTO, MjrStockTransDetailDTO goodsDetail, Session session) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setStatusCode(Responses.ERROR.getName());
        //1. find valid stock goods
        List<Condition> lstCon = Lists.newArrayList();
        lstCon.add(new Condition("custId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, mjrStockTransDTO.getCustId()));
        lstCon.add(new Condition("stockId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, mjrStockTransDTO.getStockId()));
        lstCon.add(new Condition("goodsId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, goodsDetail.getGoodsId()));
        lstCon.add(new Condition("goodsState", Constants.SQL_OPERATOR.EQUAL, goodsDetail.getGoodsState()));
        lstCon.add(new Condition("status", Constants.SQL_PRO_TYPE.BYTE, Constants.SQL_OPERATOR.EQUAL, Constants.STATUS.ACTIVE));
        //#add export with cellcode
        if (!DataUtil.isStringNullOrEmpty(goodsDetail.getCellCode())) {
            lstCon.add(new Condition("cellCode", Constants.SQL_OPERATOR.EQUAL, goodsDetail.getCellCode()));
        }

        if (!DataUtil.isStringNullOrEmpty(mjrStockTransDTO.getPartnerId()) && !mjrStockTransDTO.getPartnerId().equals("-1")) {
            lstCon.add(new Condition("partnerId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, mjrStockTransDTO.getPartnerId()));
        }
        if ("0".equals(mjrStockTransDTO.getExportMethod())) {
            lstCon.add(new Condition("importDate", Constants.SQL_OPERATOR.ORDER, "asc"));
        } else if ("1".equals(mjrStockTransDTO.getExportMethod())) {
            lstCon.add(new Condition("produceDate", Constants.SQL_OPERATOR.ORDER, "asc"));
        } else if ("2".equals(mjrStockTransDTO.getExportMethod())) {
            lstCon.add(new Condition("expireDate", Constants.SQL_OPERATOR.ORDER, "asc"));
        }


        //2. check valid
        List<MjrStockGoods> lstCurrentStockGoods = findByConditionSession(lstCon, session);
        if (DataUtil.isListNullOrEmpty(lstCurrentStockGoods)) {
            responseObject.setStatusName(Responses.ERROR_NOT_FOUND_STOCK_GOODS.getName() + "|" + goodsDetail.getGoodsCode());
            return responseObject;
        }
        //3. update
        float exportAmount = Float.valueOf(goodsDetail.getAmount());
        float currentAmount;
        Date sysDate = DateTimeUtils.convertStringToDate(mjrStockTransDTO.getCreatedDate());
        String updateResult = null;
        for (MjrStockGoods i : lstCurrentStockGoods) {
            currentAmount = i.getAmount();
            if (exportAmount == currentAmount) {
                //
                i.setChangeDate(sysDate);
                i.setExportDate(sysDate);
                i.setOutputPrice(FunctionUtils.convertStringToFloat(goodsDetail));
                i.setStatus(Constants.STATUS.BYTE_EXPORTED);
                i.setExportStockTransId(Long.valueOf(mjrStockTransDTO.getId()));
                //
                updateResult = updateBySession(i, session);
                if (!Responses.SUCCESS.getName().equalsIgnoreCase(updateResult)) {
                    responseObject.setStatusName(Responses.ERROR_UPDATE_STOCK_GOODS.getName());
                    return responseObject;
                }
                //
                log.info("Export goods: " + i.getId());
                //
                break;
            } else if (exportAmount < currentAmount) {
                //
                i.setAmount(currentAmount - exportAmount);
                i.setChangeDate(sysDate);
                if (i.getAmount() != 0f) {
                    updateResult = updateBySession(i, session);
                }
                if (!Responses.SUCCESS.getName().equalsIgnoreCase(updateResult)) {
                    responseObject.setStatusName(Responses.ERROR_UPDATE_STOCK_GOODS.getName());
                    return responseObject;
                }
                log.info("Update stock_goods " + i.getId() + " : " + currentAmount + " export: " + exportAmount);
                //
                String saveResult = saveBySession(initExportedStockGoods(mjrStockTransDTO, i, goodsDetail, exportAmount, sysDate), session);
                if (!DataUtil.isInteger(saveResult)) {
                    responseObject.setStatusName(Responses.ERROR_INSERT_STOCK_GOODS.getName());
                    return responseObject;
                }
                log.info("Insert new export stock goods: " + saveResult);
                break;
            } else {
                exportAmount -= currentAmount;
                i.setOutputPrice(FunctionUtils.convertStringToFloat(goodsDetail));
                i.setStatus(Constants.STATUS.BYTE_EXPORTED);
                i.setChangeDate(sysDate);
                i.setExportDate(sysDate);
                i.setExportStockTransId(Long.valueOf(mjrStockTransDTO.getId()));
                updateResult = updateBySession(i, session);
                if (!Responses.SUCCESS.getName().equalsIgnoreCase(updateResult)) {
                    responseObject.setStatusName(Responses.ERROR_UPDATE_STOCK_GOODS.getName());
                    return responseObject;
                }
                log.info("Export goods: " + i.getId());
            }
        }
        //
        responseObject.setStatusCode(Responses.SUCCESS.getName());
        return responseObject;
    }

    //for real export
    public List<MjrStockGoodsDTO> exportOrderStockGoods(MjrOrderDTO mjrOrder, MjrOrderDetailDTO mjrOrderDetail) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setStatusCode(Responses.ERROR.getName());
        List<MjrStockGoodsDTO> lstResult = new ArrayList<>();
        //1. find valid stock goods
        List<Condition> lstCon = Lists.newArrayList();
        lstCon.add(new Condition("custId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, mjrOrder.getCustId()));
        lstCon.add(new Condition("goodsId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, mjrOrderDetail.getGoodsId()));
        lstCon.add(new Condition("stockId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, mjrOrder.getStockId()));
        lstCon.add(new Condition("goodsState", Constants.SQL_OPERATOR.EQUAL, mjrOrderDetail.getGoodsState()));
        lstCon.add(new Condition("status", Constants.SQL_PRO_TYPE.BYTE, Constants.SQL_OPERATOR.EQUAL, Constants.STATUS.ACTIVE));

        if (!DataUtil.isStringNullOrEmpty(mjrOrder.getPartnerId()) && !mjrOrder.getPartnerId().equals("-1")) {
            lstCon.add(new Condition("partnerId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, mjrOrder.getPartnerId()));
        }
        if ("0".equals(mjrOrder.getExportMethod())) {
            lstCon.add(new Condition("importDate", Constants.SQL_OPERATOR.ORDER, "asc"));
        } else if ("1".equals(mjrOrder.getExportMethod())) {
            lstCon.add(new Condition("produceDate", Constants.SQL_OPERATOR.ORDER, "asc"));
        } else if ("2".equals(mjrOrder.getExportMethod())) {
            lstCon.add(new Condition("expireDate", Constants.SQL_OPERATOR.ORDER, "asc"));
        }


        //2. check valid
        List<MjrStockGoods> lstCurrentStockGoods = findByConditionSession(lstCon, getSession());
        if (DataUtil.isListNullOrEmpty(lstCurrentStockGoods)) {
            return lstResult;
        }
        //3. update
        float exportAmount = Float.valueOf(mjrOrderDetail.getAmount());
        float currentAmount;
        String updateResult = null;
        for (MjrStockGoods i : lstCurrentStockGoods) {
            MjrStockGoodsDTO data = i.toDTO();
            currentAmount = i.getAmount();
            if (exportAmount > currentAmount) {
                exportAmount -= currentAmount;
                data.setRequestAmount(currentAmount +"");
                if (lstCurrentStockGoods.indexOf(i) + 1 == lstCurrentStockGoods.size()){
                    data.setRequestAmount(currentAmount +exportAmount +"");
                }
                lstResult.add(data);
            } else {
                data.setAmount(exportAmount + "");
                data.setRequestAmount(exportAmount +"");
                lstResult.add(data);
                break;
            }

        }
        return lstResult;
    }

    public MjrStockGoods initExportedStockGoods(MjrStockTransDTO mjrStockTransDTO, MjrStockGoods currentGoodsDetail, MjrStockTransDetailDTO exportGoodsDetail,
                                                float exportAmount, Date changeDate) {
        MjrStockGoods goods = new MjrStockGoods();
        goods.setCustId(Long.parseLong(mjrStockTransDTO.getCustId()));
        goods.setStockId(Long.parseLong(mjrStockTransDTO.getStockId()));
        goods.setGoodsId(currentGoodsDetail.getGoodsId());
        goods.setGoodsState(currentGoodsDetail.getGoodsState());
        goods.setCellCode(currentGoodsDetail.getCellCode());
        goods.setAmount(exportAmount);
        goods.setImportDate(currentGoodsDetail.getImportDate());
        goods.setChangeDate(changeDate);
        goods.setExportDate(changeDate);
        goods.setStatus(Constants.STATUS.BYTE_EXPORTED);
        goods.setPartnerId(currentGoodsDetail.getPartnerId());
        goods.setImportStockTransId(currentGoodsDetail.getImportStockTransId());
        goods.setExportStockTransId(Long.valueOf(mjrStockTransDTO.getId()));
        goods.setInputPrice(currentGoodsDetail.getInputPrice());
        goods.setOutputPrice(FunctionUtils.convertStringToFloat(exportGoodsDetail));
        return goods;
    }
}
