package com.wms.persistents.dao;

import com.google.common.collect.Lists;
import com.wms.base.BaseDAOImpl;
import com.wms.dto.Condition;
import com.wms.dto.ErrorLogDTO;
import com.wms.dto.MjrStockGoodsTotalDTO;
import com.wms.dto.ResponseObject;
import com.wms.enums.Responses;
import com.wms.persistents.model.CatGoods;
import com.wms.persistents.model.ErrorLog;
import com.wms.persistents.model.MjrStockGoodsTotal;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import com.wms.utils.DateTimeUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duyot on 12/19/2016.
 */
@Repository
@Transactional
public class MjrStockGoodsTotalDAO extends BaseDAOImpl<MjrStockGoodsTotal,Long> {
    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    ErrorLogDAO errorLogDAO;

    @Autowired
    CatGoodsDAO catGoodsDAO;
    @Autowired
    MjrStockGoodsSerialDAO mjrStockGoodsSerialDAO;

    Logger log = LoggerFactory.getLogger(MjrStockGoodsTotalDAO.class);

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public int updateGoods(CatGoods catGoods){
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE MJR_STOCK_GOODS_TOTAL SET ");
        sql.append(" goods_code = :goodsCode, goods_name = :goodsName ");
        sql.append(" where goods_id = :goodsId ");
        Query query = getSession().createSQLQuery(sql.toString());
        query.setParameter("goodsCode", catGoods.getCode());
        query.setParameter("goodsName", catGoods.getName());
        query.setParameter("goodsId", catGoods.getId());

        try {
            return query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String saveMjrStockGoodsTotal(MjrStockGoodsTotalDTO stockGoodsTotal,Connection connection) {
        StringBuilder sqlInsert = new StringBuilder();
        PreparedStatement prstmtInsertTotal;
        sqlInsert.append(" Insert into MJR_STOCK_GOODS_TOTAL ");
        sqlInsert.append(" (ID,CUST_ID,GOODS_ID,GOODS_CODE,GOODS_NAME,GOODS_STATE,STOCK_ID,AMOUNT,CHANGED_DATE,PRE_AMOUNT) ");
        sqlInsert.append(" Values(SEQ_MJR_STOCK_GOODS_TOTAL.nextval,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),?)");
        List<String> lstParams = initSaveTotalParams(stockGoodsTotal);
        try {
            prstmtInsertTotal = connection.prepareStatement(sqlInsert.toString());
            for (int idx = 0; idx < lstParams.size(); idx++) {
                prstmtInsertTotal.setString(idx + 1, DataUtil.nvl(lstParams.get(idx), "").toString());
            }
            prstmtInsertTotal.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return Responses.ERROR.getName();
        }


        return Responses.SUCCESS.getName();
    }

    public List initSaveTotalParams(MjrStockGoodsTotalDTO stockGoodsTotal){
        List<String> params = new ArrayList();
        params.add(stockGoodsTotal.getCustId());
        params.add(stockGoodsTotal.getGoodsId());
        params.add(stockGoodsTotal.getGoodsCode());
        params.add(stockGoodsTotal.getGoodsName());
        params.add(stockGoodsTotal.getGoodsState());
        params.add(stockGoodsTotal.getStockId());
        params.add(stockGoodsTotal.getAmount());
        params.add(stockGoodsTotal.getChangeDate());
        params.add(stockGoodsTotal.getAmount());

        return params;
    }

    public ResponseObject updateExportTotal(MjrStockGoodsTotalDTO stockGoodsTotal, Session session){
        //
        ResponseObject responseObject = new ResponseObject();
        responseObject.setStatusCode(Responses.ERROR.getName());
        //1. Find match total for goods
        List<Condition> lstCon = Lists.newArrayList();
        lstCon.add(new Condition("custId", Constants.SQL_PRO_TYPE.LONG,Constants.SQL_OPERATOR.EQUAL,stockGoodsTotal.getCustId()));
        lstCon.add(new Condition("stockId", Constants.SQL_PRO_TYPE.LONG,Constants.SQL_OPERATOR.EQUAL,stockGoodsTotal.getStockId()));
        lstCon.add(new Condition("goodsId", Constants.SQL_PRO_TYPE.LONG,Constants.SQL_OPERATOR.EQUAL,stockGoodsTotal.getGoodsId()));
        lstCon.add(new Condition("goodsState", Constants.SQL_OPERATOR.EQUAL,stockGoodsTotal.getGoodsState()));

        List<MjrStockGoodsTotal> lstResultTotal = findByConditionSession(lstCon,session);
        if(DataUtil.isListNullOrEmpty(lstResultTotal)){
            responseObject.setStatusName(Responses.ERROR_NOT_FOUND_TOTAL.getName());
            responseObject.setKey(stockGoodsTotal.getGoodsId());
            return responseObject;
        }
        MjrStockGoodsTotal currentTotal = lstResultTotal.get(0);
        //2. check current amount
        Double changeAmount = 0d;

        try {
            changeAmount = Double.valueOf(stockGoodsTotal.getAmount());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            log.info("Error in conver amount value in total" + stockGoodsTotal.toString());
        }
        if(changeAmount > currentTotal.getAmount()){
            responseObject.setStatusName(Responses.ERROR_TOTAL_NOT_ENOUGH.getName());
            responseObject.setKey(stockGoodsTotal.getGoodsId());
            responseObject.setTotal(currentTotal.getAmount()+"");
            responseObject.setSuccess(changeAmount+"");
            return responseObject;
        }
        //update amount
        currentTotal.setPreAmount(currentTotal.getAmount());
        currentTotal.setAmount(currentTotal.getAmount() - changeAmount);
        currentTotal.setChangeDate(DateTimeUtils.convertStringToDate(stockGoodsTotal.getChangeDate()));
        //
        String updateTotalResult = updateBySession(currentTotal,session);
        if(!Responses.SUCCESS.getName().equalsIgnoreCase(updateTotalResult)){
            responseObject.setStatusName(Responses.ERROR_UPDATE_TOTAL.getName());
            responseObject.setKey(stockGoodsTotal.getGoodsId());
            return responseObject;
    }
        log.info("Update export total: "+ currentTotal.getId()+ " current value: "+ (currentTotal.getPreAmount()) + " export: "+ changeAmount);
        log.info("Value after: "+ currentTotal.getAmount());
        responseObject.setStatusCode(Responses.SUCCESS.getName());
        responseObject.setKey(stockGoodsTotal.getGoodsId());
        return responseObject;
    }

    public String updateImportTotal(MjrStockGoodsTotalDTO stockGoodsTotal, Connection connection){
        log.info("Update import total: "+ "cust="+stockGoodsTotal.getCustId()+ "|stockId="+ stockGoodsTotal.getStockId() + "|goodsId="+ stockGoodsTotal.getGoodsId()
                + "|goodsState="+ stockGoodsTotal.getGoodsState()+ "|amount="+ stockGoodsTotal.getAmount());

        StringBuilder sqlUpdateTotal = new StringBuilder();
        sqlUpdateTotal.append(" UPDATE  mjr_stock_goods_total a ");
        sqlUpdateTotal.append(" SET  a.pre_amount      = a.amount, ");
        sqlUpdateTotal.append("      a.amount      = a.amount + ?, ");
        sqlUpdateTotal.append("      a.changed_date = to_date(?,'dd/MM/yyyy hh24:mi:ss') ");
        sqlUpdateTotal.append(" WHERE a.cust_id = ? ");
        sqlUpdateTotal.append("   AND a.stock_id = ? ");
        sqlUpdateTotal.append("   AND a.goods_id = ? ");
        sqlUpdateTotal.append("   AND a.goods_state = ? ");

        try {
            PreparedStatement ps = connection.prepareStatement(sqlUpdateTotal.toString());
            ps.setFloat(1, Float.valueOf(stockGoodsTotal.getAmount()));
            ps.setString(2, stockGoodsTotal.getChangeDate());
            ps.setString(3, stockGoodsTotal.getCustId());
            ps.setString(4, stockGoodsTotal.getStockId());
            ps.setString(5, stockGoodsTotal.getGoodsId());
            ps.setString(6, stockGoodsTotal.getGoodsState());
            int updateCount = ps.executeUpdate();
            if(updateCount ==0){
                log.info("Found no row to update, create new one.");
                String insertedTotalResult = saveMjrStockGoodsTotal(stockGoodsTotal,connection);
                if(!Responses.SUCCESS.getName().equalsIgnoreCase(insertedTotalResult)){
                    return Responses.ERROR.getName();
                }
                log.info("Insert successfully total");
                return Responses.SUCCESS.getName();
            }
            return Responses.SUCCESS.getName();
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
            errorLogDAO.save(initErrorInfo(e.toString(),stockGoodsTotal));
            return Responses.ERROR.getName();
        }
    }

    private ErrorLog initErrorInfo(String error, MjrStockGoodsTotalDTO stockGoodsTotal){
        ErrorLogDTO errorLog = new ErrorLogDTO();
        errorLog.setClassName("MjrStockGoodsTotalDAO");
        errorLog.setFunction("updateSession");
        errorLog.setCreateDate(getSysDate());
        errorLog.setParameter(stockGoodsTotal.toString());
        errorLog.setErrorInfo(error);

        return errorLog.toModel();
    }

}
