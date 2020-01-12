package com.wms.business.impl;

import com.google.common.collect.Lists;
import com.wms.base.BaseBusinessImpl;
import com.wms.base.BaseBusinessInterface;
import com.wms.business.interfaces.MjrOrderBusinessInterface;
import com.wms.business.interfaces.StockFunctionInterface;
import com.wms.dto.*;
import com.wms.enums.Responses;
import com.wms.persistents.dao.MjrOrderDAO;
import com.wms.persistents.dao.MjrStockGoodsDAO;
import com.wms.persistents.dao.MjrStockGoodsSerialDAO;
import com.wms.persistents.model.MjrOrder;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import java.sql.Connection;
import java.util.*;
import javax.annotation.PostConstruct;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by truongbx.
 */
@Service("mjrOrderBusiness")
public class MjrOrderBusinessImpl extends BaseBusinessImpl<MjrOrderDTO, MjrOrderDAO> implements MjrOrderBusinessInterface {
    private final String[] TOTAL_UPDATE_PROPERTIES = {"iSsueAmount"};
    @Autowired
    MjrOrderDAO mjrOrderDAO;
    @Autowired
    MjrStockGoodsDAO mjrStockGoodsDAO;
    @Autowired
    MjrStockGoodsSerialDAO mjrStockGoodsSerialDAO;
    @Autowired
    BaseBusinessInterface mjrOrderDetailBusiness;
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    StockFunctionInterface stockFunctionBusiness;
    @Autowired
    BaseBusinessInterface catStockBusiness;
    @Autowired
    BaseBusinessInterface mjrStockGoodsTotalBusiness;

    private Logger log = LoggerFactory.getLogger(MjrOrderBusinessImpl.class);

    @PostConstruct
    public void setupService() {
        this.tdao = mjrOrderDAO;
        this.entityClass = MjrOrderDTO.class;
        this.mjrOrderDAO.setModelClass(MjrOrder.class);
        this.tDTO = new MjrOrderDTO();
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseObject deleteOrder(Long orderId) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setStatusName(Responses.SUCCESS.getName());
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        try {
            deleteByIdSession(orderId, session);
            List<MjrOrderDetailDTO> orderDetails = mjrOrderDetailBusiness.findByProperty("orderId", orderId);
            for (MjrOrderDetailDTO mjrOrderDetail : orderDetails) {
                mjrOrderDetailBusiness.deleteByObjectSession(mjrOrderDetail, session);
            }
            MjrOrderDTO order = findById(orderId);
            if(order != null && order.getType().equals("2")){
                updateGoodsTotal(order, orderDetails, true, session);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            responseObject.setStatusName(Responses.ERROR.getName());
            responseObject.setKey("FAIL");
            log.error(e.toString());
        } finally {
            session.close();
        }
        return responseObject;
    }

    @Override
    public ResponseObject orderExport(MjrOrderDTO mjrOrder, List<MjrOrderDetailDTO> lstMjrOrderDetails) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setStatusName(Responses.SUCCESS.getName());
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        List<MjrOrderDetailDTO> mjrOrderDetails = new ArrayList<>();
        try {
            String id;
            if (!DataUtil.isNullOrEmpty(mjrOrder.getId())) {
                id = mjrOrder.getId();
                updateBySession(mjrOrder, session);
                List<MjrOrderDetailDTO> orderDetails = mjrOrderDetailBusiness.findByProperty("orderId", Long.valueOf(mjrOrder.getId()));
                for (MjrOrderDetailDTO mjrOrderDetail : orderDetails) {
                    mjrOrderDetailBusiness.deleteByObjectSession(mjrOrderDetail, session);
                }
                if(mjrOrder != null && mjrOrder.getType().equals("2")){
                    updateGoodsTotal(mjrOrder, orderDetails, true, session);
                }
            } else {
                String createdDatePartition = getSysDate("ddMMyyyy");
                mjrOrder.setCode(initTransCode(mjrOrder, createdDatePartition, session, null));
                id = saveBySession(mjrOrder, session);
            }


            for (MjrOrderDetailDTO mjrOrderDetailDTO : lstMjrOrderDetails) {
                MjrOrderDetailDTO mjrOrderDetail = mjrOrderDetailDTO;
                mjrOrderDetail.setOrderId(id);
                mjrOrderDetails.add(mjrOrderDetail);
            }
            mjrOrderDetailBusiness.saveBySession(mjrOrderDetails, session);
            if(mjrOrder != null && mjrOrder.getType().equals("2")) {
                updateGoodsTotal(mjrOrder, mjrOrderDetails, false, session);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            responseObject.setStatusName(Responses.ERROR.getName());
            log.error(e.toString());
        } finally {
            session.close();
        }
        return responseObject;
    }

    @Override
    public List<RealExportExcelDTO> orderExportData(Long mjrOrderId) {
        List<RealExportExcelDTO> realExportExcelDTOS = new ArrayList<>();
        MjrOrderDTO mjrOrder = findById(mjrOrderId);
        List<MjrOrderDetailDTO> mjrOrderDetail = getListOrderDetail( mjrOrderId);
        for (MjrOrderDetailDTO detail : mjrOrderDetail) {
            if (detail.getIsSerial() != null && Constants.IS_SERIAL.equalsIgnoreCase(detail.getIsSerial())) {
                List<MjrStockGoodsSerialDTO> mjrStockGoodsSerials = mjrStockGoodsSerialDAO.exportOrderStockGoodsSerial(mjrOrder, detail);
                realExportExcelDTOS.addAll(convertStockGoodsSerialToExcelData(mjrStockGoodsSerials, detail));
            } else {
                List<MjrStockGoodsDTO> mjrStockGoods = mjrStockGoodsDAO.exportOrderStockGoods(mjrOrder, detail);
                realExportExcelDTOS.addAll(convertStockGoodsToExcelData(mjrStockGoods, detail));
            }
        }
        return realExportExcelDTOS;
    }

    @Override
    public List<MjrOrderDetailDTO> getListOrderDetail(Long orderId) {
        return mjrOrderDetailBusiness.findByProperty("orderId", orderId, Order.asc("goodsOrder"));
    }

    //------------------------------------------------------------------------------------------------------------------
    private void updateGoodsTotal(MjrOrderDTO mjrOrder, List<MjrOrderDetailDTO> mjrOrderDetails, boolean isAdd, Session session) {
        Map<String, Float> mapGoodsAmount = initMapGoodsAmount(mjrOrderDetails);
        if (mapGoodsAmount != null && mapGoodsAmount.size() > 0) {
            //
            Iterator iterator = mapGoodsAmount.entrySet().iterator();
            String goodsInfo;
            Float amount;
            String updateResult;
            while (iterator.hasNext()) {
                Map.Entry pair = (Map.Entry) iterator.next();
                //
                goodsInfo = (String) pair.getKey();
                amount = (Float) pair.getValue();
                String[] goodsInfoArr = goodsInfo.split(",");
                String goodsId = goodsInfoArr[0];
                String goodsState = goodsInfoArr[1];
                //
                MjrStockGoodsTotalDTO goodsTotal = findGoodsTotal(mjrOrder.getCustId(), mjrOrder.getStockId(), goodsId, goodsState, session);
                if (goodsTotal != null) {
                    //update amount issue
                    updateTotalDetails(goodsTotal, amount, isAdd);
                    updateResult = mjrStockGoodsTotalBusiness.updateByPropertiesBySession(goodsTotal, Long.parseLong(goodsTotal.getId()), TOTAL_UPDATE_PROPERTIES, session);
                    log.info("Update amount issue " + updateResult + " : " + mjrOrder.getCustId() + " - " + mjrOrder.getStockId() + " - " + goodsId + " - " + goodsState);
                }
            }
        }
    }

    private void updateTotalDetails(MjrStockGoodsTotalDTO goodsTotal, Float amount, boolean isAddAmount) {
        Float totalCurrentAmount = Float.valueOf("".equals(goodsTotal.getIssueAmount()) == false ? goodsTotal.getIssueAmount() : "0" );
        if (isAddAmount) {
            totalCurrentAmount += amount;
        } else {
            totalCurrentAmount -= amount;
        }
        goodsTotal.setIssueAmount(String.valueOf(totalCurrentAmount));
    }

    private Map<String, Float> initMapGoodsAmount(List<MjrOrderDetailDTO> mjrOrderDetails) {
        Map<String, Float> mapGoodsAmount = new HashMap<>();
        for (MjrOrderDetailDTO i : mjrOrderDetails) {
            String key = i.getGoodsId() + "," + i.getGoodsState();
            if (mapGoodsAmount.containsKey(key)) {
                mapGoodsAmount.put(key, mapGoodsAmount.get(key) + Float.valueOf(i.getAmount()));
            } else {
                mapGoodsAmount.put(key, Float.valueOf(i.getAmount()));
            }
        }
        return mapGoodsAmount;
    }

    private MjrStockGoodsTotalDTO findGoodsTotal(String custId, String stockId, String goodsId, String goodsState, Session session) {
        List<Condition> lstCon = Lists.newArrayList();
        lstCon.add(new Condition("custId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, custId));
        lstCon.add(new Condition("stockId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, stockId));
        lstCon.add(new Condition("goodsId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, goodsId));
        lstCon.add(new Condition("goodsState", Constants.SQL_OPERATOR.EQUAL, goodsState));
        List<MjrStockGoodsTotalDTO> goodsTotals = mjrStockGoodsTotalBusiness.findByConditionSession(lstCon, session);
        if (!DataUtil.isListNullOrEmpty(goodsTotals)) {
            return goodsTotals.get(0);
        }
        return null;
    }


    //
    private String initTransCode(MjrOrderDTO mjrOrderDTO, String createdTime, Session session, Connection con) {
        //
        boolean isImport = Constants.TRANSACTION_TYPE.IMPORT.equalsIgnoreCase(mjrOrderDTO.getType());
        //
        String stockCode = "";
        CatStockDTO stock = (CatStockDTO) catStockBusiness.findById(Long.parseLong(mjrOrderDTO.getStockId()));
        if (stock != null) {
            stockCode = stock.getCode();
        }
        //
        StringBuilder stringBuilder = new StringBuilder();
        if (isImport) {
            stringBuilder.append("YCNK");
        } else {
            stringBuilder.append("YCXK");
        }
        stringBuilder.append("/").append(stockCode)
                .append("/")
                .append(createdTime)
                .append("/");
        stringBuilder.append(mjrOrderDAO.getTotalOrder(mjrOrderDTO.getStockId(), Integer.parseInt(mjrOrderDTO.getType())));
        return stringBuilder.toString();
    }


    public List<RealExportExcelDTO> convertStockGoodsToExcelData(List<MjrStockGoodsDTO> mjrStockGoods, MjrOrderDetailDTO mjrOrderDetail) {
        List<RealExportExcelDTO> realExportExcelDTOS = new ArrayList<>();
        for (MjrStockGoodsDTO mjrStockGoods1 : mjrStockGoods) {
            RealExportExcelDTO realExportExcelDTO = new RealExportExcelDTO();
            realExportExcelDTO.setUnitName(mjrOrderDetail.getUnitName());
            realExportExcelDTO.setGoodsCode(mjrOrderDetail.getGoodsCode());
            realExportExcelDTO.setGoodsState(mjrOrderDetail.getGoodsState());
            realExportExcelDTO.setAmount(mjrStockGoods1.getAmount());
            realExportExcelDTO.setWeight(mjrStockGoods1.getWeight());
            realExportExcelDTO.setVolume(mjrStockGoods1.getVolume());
            realExportExcelDTO.setCellCode(mjrStockGoods1.getCellCode());
            realExportExcelDTO.setDescription(mjrStockGoods1.getDescription());
            realExportExcelDTO.setProduceDate(mjrStockGoods1.getProduceDate());
            realExportExcelDTOS.add(realExportExcelDTO);
        }

        return realExportExcelDTOS;
    }

    public List<RealExportExcelDTO> convertStockGoodsSerialToExcelData(List<MjrStockGoodsSerialDTO> mjrStockGoodsSerials, MjrOrderDetailDTO mjrOrderDetail) {
        List<RealExportExcelDTO> realExportExcelDTOS = new ArrayList<>();
        for (MjrStockGoodsSerialDTO mjrStockGoodsSerial : mjrStockGoodsSerials) {
            RealExportExcelDTO realExportExcelDTO = new RealExportExcelDTO();
            realExportExcelDTO.setUnitName(mjrOrderDetail.getUnitName());
            realExportExcelDTO.setGoodsCode(mjrOrderDetail.getGoodsCode());
            realExportExcelDTO.setGoodsState(mjrOrderDetail.getGoodsState());
            realExportExcelDTO.setAmount(mjrStockGoodsSerial.getAmount());
            realExportExcelDTO.setWeight(mjrStockGoodsSerial.getWeight());
            realExportExcelDTO.setVolume(mjrStockGoodsSerial.getVolume());
            realExportExcelDTO.setCellCode(mjrStockGoodsSerial.getCellCode());
            realExportExcelDTO.setDescription(mjrStockGoodsSerial.getDescription());
            realExportExcelDTO.setProduceDate(mjrStockGoodsSerial.getProduceDate());
            realExportExcelDTOS.add(realExportExcelDTO);
        }
        return realExportExcelDTOS;
    }
}
