package com.wms.persistents.dao;

import com.google.common.collect.Lists;
import com.wms.base.BaseDAOImpl;
import com.wms.dto.*;
import com.wms.enums.Responses;
import com.wms.persistents.model.MjrStockGoodsSerial;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.FunctionUtils;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 1/3/2017.
 */
@Repository
@Transactional
public class MjrStockGoodsSerialDAO extends BaseDAOImpl<MjrStockGoodsSerial, Long> {

    public ResponseObject exportStockGoodsSerial(MjrStockTransDTO mjrStockTransDTO, MjrStockTransDetailDTO goodsDetail, Session session) {
        ResponseObject responseObject = new ResponseObject();
        //1. Find match total for goods
        List<Condition> lstCon = Lists.newArrayList();
        lstCon.add(new Condition("custId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, mjrStockTransDTO.getCustId()));
        lstCon.add(new Condition("stockId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, mjrStockTransDTO.getStockId()));
        lstCon.add(new Condition("goodsId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, goodsDetail.getGoodsId()));
        lstCon.add(new Condition("goodsState", Constants.SQL_OPERATOR.EQUAL, goodsDetail.getGoodsState()));
        lstCon.add(new Condition("serial", Constants.SQL_OPERATOR.EQUAL, goodsDetail.getSerial()));
        lstCon.add(new Condition("status", Constants.SQL_PRO_TYPE.BYTE, Constants.SQL_OPERATOR.EQUAL, Constants.STATUS.ACTIVE));
        //#add export with cellcode
        if (!DataUtil.isStringNullOrEmpty(goodsDetail.getCellCode())) {
            lstCon.add(new Condition("cellCode", Constants.SQL_OPERATOR.EQUAL, goodsDetail.getCellCode()));
        }
        if (!DataUtil.isStringNullOrEmpty(mjrStockTransDTO.getPartnerId()) && !mjrStockTransDTO.getPartnerId().equals("-1")) {
            lstCon.add(new Condition("partnerId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, mjrStockTransDTO.getPartnerId()));
        }
        List<MjrStockGoodsSerial> lstResultSerial = findByConditionSession(lstCon, session);
        if (DataUtil.isListNullOrEmpty(lstResultSerial)) {
            responseObject.setStatusCode(Responses.ERROR.getName());
            responseObject.setStatusName(Responses.ERROR_NOT_FOUND_SERIAL.getName() + "|" + goodsDetail.getSerial());
            responseObject.setKey(goodsDetail.getSerial());
            return responseObject;
        }
        MjrStockGoodsSerial currentGoodsSerial = lstResultSerial.get(0);
        //2. set export info
        currentGoodsSerial.setStatus(Constants.STATUS.BYTE_EXPORTED);
        currentGoodsSerial.setOutputPrice(FunctionUtils.convertStringToFloat(goodsDetail));
        currentGoodsSerial.setChangeDate(DateTimeUtils.convertStringToDate(mjrStockTransDTO.getCreatedDate()));
        currentGoodsSerial.setExportDate(DateTimeUtils.convertStringToDate(mjrStockTransDTO.getCreatedDate()));
        currentGoodsSerial.setExportStockTransId(Long.valueOf(mjrStockTransDTO.getId()));
        //
        String updateResult = updateBySession(currentGoodsSerial, session);
        responseObject.setStatusCode(updateResult);
        return responseObject;
    }

    public List<MjrStockGoodsSerialDTO> exportOrderStockGoodsSerial(MjrOrderDTO mjrOrderDTO, MjrOrderDetailDTO goodsDetail) {
        //1. Find match total for goods
        List<Condition> lstCon = Lists.newArrayList();
        List<MjrStockGoodsSerialDTO> data = new ArrayList<>();
        lstCon.add(new Condition("custId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, mjrOrderDTO.getCustId()));
        lstCon.add(new Condition("stockId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, mjrOrderDTO.getStockId()));
        lstCon.add(new Condition("goodsId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, goodsDetail.getGoodsId()));
        lstCon.add(new Condition("goodsState", Constants.SQL_OPERATOR.EQUAL, goodsDetail.getGoodsState()));
//        lstCon.add(new Condition("serial", Constants.SQL_OPERATOR.EQUAL, goodsDetail.getSerial()));
        lstCon.add(new Condition("status", Constants.SQL_PRO_TYPE.BYTE, Constants.SQL_OPERATOR.EQUAL, Constants.STATUS.ACTIVE));

        if (!DataUtil.isStringNullOrEmpty(mjrOrderDTO.getPartnerId()) && !mjrOrderDTO.getPartnerId().equals("-1")) {
            lstCon.add(new Condition("partnerId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, mjrOrderDTO.getPartnerId()));
        }
        Condition limitRow = new Condition();
        limitRow.setOperator("LIMIT");
        limitRow.setValue((int)Double.parseDouble(goodsDetail.getAmount()));
        lstCon.add(limitRow);
        List<MjrStockGoodsSerial> lstResultSerial = findByConditionSession(lstCon, getSession());
        lstResultSerial.forEach(e -> {
            data.add(e.toDTO());
        });
        return data;
    }

}
