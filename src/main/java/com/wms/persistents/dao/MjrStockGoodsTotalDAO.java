package com.wms.persistents.dao;

import com.google.common.collect.Lists;
import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseDAOImpl;
import com.wms.dto.Condition;
import com.wms.dto.ErrorLogDTO;
import com.wms.dto.MjrStockGoodsTotalDTO;
import com.wms.enums.Responses;
import com.wms.persistents.model.ErrorLog;
import com.wms.persistents.model.MjrStockGoodsTotal;
import com.wms.utils.DataUtil;
import com.wms.utils.FunctionUtils;
import org.hibernate.*;
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

    Logger log = LoggerFactory.getLogger(MjrStockGoodsTotalDAO.class);

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public String saveMjrStockGoodsTotal(MjrStockGoodsTotalDTO stockGoodsTotal,Connection connection) {
        StringBuilder sqlInsert = new StringBuilder();
        PreparedStatement prstmtInsertTotal;
        sqlInsert.append(" Insert into MJR_STOCK_GOODS_TOTAL ");
        sqlInsert.append(" (ID,CUST_ID,GOODS_ID,GOODS_CODE,GOODS_NAME,GOODS_STATE,STOCK_ID,AMOUNT,CHANGED_DATE) ");
        sqlInsert.append(" Values(SEQ_MJR_STOCK_GOODS_TOTAL.nextval,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'))");
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
        List params = new ArrayList();
        params.add(stockGoodsTotal.getCustId());
        params.add(stockGoodsTotal.getGoodsId());
        params.add(stockGoodsTotal.getGoodsCode());
        params.add(stockGoodsTotal.getGoodsName());
        params.add(stockGoodsTotal.getGoodsState());
        params.add(stockGoodsTotal.getStockId());
        params.add(stockGoodsTotal.getAmount());
        params.add(stockGoodsTotal.getChangeDate());

        return params;
    }

    public String updateTotal(MjrStockGoodsTotalDTO stockGoodsTotal, Connection connection){
        log.info("Update Total: "+ "cust|"+stockGoodsTotal.getCustId()+ "stockId|"+ stockGoodsTotal.getStockId() + "goodsId|"+ stockGoodsTotal.getGoodsId()
                + "goodsState|"+ stockGoodsTotal.getGoodsState()+ "amount|"+ stockGoodsTotal.getAmount());

        StringBuilder sqlUpdateTotal = new StringBuilder();
        sqlUpdateTotal.append(" UPDATE  mjr_stock_goods_total a ");
        sqlUpdateTotal.append(" SET  a.amount      = a.amount + ?, ");
        sqlUpdateTotal.append("      a.changed_date = to_date(?,'dd/MM/yyyy hh24:mi:ss') ");
        sqlUpdateTotal.append(" WHERE a.cust_id = ? ");
        sqlUpdateTotal.append("   AND a.stock_id = ? ");
        sqlUpdateTotal.append("   AND a.goods_id = ? ");
        sqlUpdateTotal.append("   AND a.goods_state = ? ");

        try {
            PreparedStatement ps = connection.prepareStatement(sqlUpdateTotal.toString());
            ps.setString(1, stockGoodsTotal.getAmount() +"");
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

    public List<MjrStockGoodsTotal> findByConditionSession(List<Condition> lstCondition,Session session) {
        Criteria cr = session.createCriteria(MjrStockGoodsTotal.class);
        if(DataUtil.isListNullOrEmpty(lstCondition)){
            return Lists.newArrayList();
        }

        cr = FunctionUtils.initCriteria(cr,lstCondition);

        try {
            return (List<MjrStockGoodsTotal>)cr.list();
        } catch (HibernateException e) {
            e.printStackTrace();
            return Lists.newArrayList();
        }
    }

}
