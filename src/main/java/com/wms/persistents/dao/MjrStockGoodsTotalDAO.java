package com.wms.persistents.dao;

import com.google.common.collect.Lists;
import com.sun.corba.se.impl.orbutil.closure.Constant;
import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseDAOImpl;
import com.wms.dto.Condition;
import com.wms.dto.ErrorLogDTO;
import com.wms.enums.Result;
import com.wms.persistents.model.ErrorLog;
import com.wms.persistents.model.MjrStockGoodsTotal;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import com.wms.utils.FunctionUtils;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
    public BaseBusinessInterface errorLogBusiness;

    Logger log = LoggerFactory.getLogger(MjrStockGoodsTotalDAO.class);

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public String updateSession(MjrStockGoodsTotal stockGoodsTotal,Session session) {
        log.info("Update Total: "+ "cust|"+stockGoodsTotal.getCustId()+ "stockId|"+ stockGoodsTotal.getStockId() + "goodsId|"+ stockGoodsTotal.getGoodsId()
                + "goodsState|"+ stockGoodsTotal.getGoodsState()+ "amount|"+ stockGoodsTotal.getAmount());

        StringBuilder sqlUpdateTotal = new StringBuilder();
        sqlUpdateTotal.append(" UPDATE  mjr_stock_goods_total a ");
        sqlUpdateTotal.append(" SET  a.amount      = a.amount + ?, ");
        sqlUpdateTotal.append("      a.change_date = ? ");
        sqlUpdateTotal.append(" WHERE a.cust_id = ? ");
        sqlUpdateTotal.append("   AND a.stock_id = ? ");
        sqlUpdateTotal.append("   AND a.goods_id = ? ");
        sqlUpdateTotal.append("   AND a.goods_state = ? ");

        Query query = session.createSQLQuery(sqlUpdateTotal.toString());
        query.setParameter(0, stockGoodsTotal.getAmount() +"");
        query.setParameter(1, stockGoodsTotal.getChangeDate());
        query.setParameter(2, stockGoodsTotal.getCustId());
        query.setParameter(3, stockGoodsTotal.getStockId());
        query.setParameter(4, stockGoodsTotal.getGoodsId());
        query.setParameter(5, stockGoodsTotal.getGoodsState());

        try {
            int updateCount = query.executeUpdate();
            if(updateCount ==0){
                log.info("Found no row to update, create new one.");
                String insertedTotalId = saveBySession(stockGoodsTotal,session);
                log.info("Insert successfully total: "+ insertedTotalId);
                return Result.SUCCESS.getName();
            }
            return Result.SUCCESS.getName();
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
            errorLogBusiness.save(initErrorInfo(e.toString(),stockGoodsTotal));
            return Result.ERROR.getName();
        }
    }

    private ErrorLogDTO initErrorInfo(String error,MjrStockGoodsTotal stockGoodsTotal){
        ErrorLogDTO errorLog = new ErrorLogDTO();
        errorLog.setClassName("MjrStockGoodsTotalDAO");
        errorLog.setFunction("updateSession");
        errorLog.setCreateDate(getSysDate());
        errorLog.setParameter(stockGoodsTotal.toString());
        errorLog.setErrorInfo(error);

        return errorLog;
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
