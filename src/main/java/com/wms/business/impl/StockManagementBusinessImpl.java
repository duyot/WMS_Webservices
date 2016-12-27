package com.wms.business.impl;

import com.wms.base.BaseBusinessInterface;
import com.wms.business.interfaces.MjrStockGoodsTotalBusinessInterface;
import com.wms.business.interfaces.StockManagermentBusinessInterface;
import com.wms.dto.MjrStockGoodsTotalDTO;
import com.wms.utils.FunctionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duyot on 12/19/2016.
 */
@Service("stockManagementBusiness")
public class StockManagementBusinessImpl implements StockManagermentBusinessInterface {
    @Autowired
    BaseBusinessInterface mjrStockGoodsTotalBusiness;

    @Autowired
    MjrStockGoodsTotalBusinessInterface advancedMjrStockGoodsTotalBusiness;

    @Autowired
    SessionFactory sessionFactory;

    Logger log = LoggerFactory.getLogger(StockManagementBusinessImpl.class);

    @Override
    public String importStock(MjrStockGoodsTotalDTO stockGoodsTotal) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        //
        try {
            MjrStockGoodsTotalDTO totalItem = (MjrStockGoodsTotalDTO) mjrStockGoodsTotalBusiness.findById(Long.parseLong(stockGoodsTotal.getId()));
            //
            //Thread.sleep(5000);
            totalItem.setCustId("2017");
            Double changeValue = Double.valueOf(stockGoodsTotal.getAmount());
            //
            String result = advancedMjrStockGoodsTotalBusiness.updateSession(totalItem,changeValue,session);
            //
            transaction.commit();
            log.info("TX commit value: "+ changeValue);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return "FAIL";
        }finally {
            FunctionUtils.closeSession(session);
        }
    }

    @Override
    public String exportStock(MjrStockGoodsTotalDTO stockGoodsTotal) {
        return null;
    }
}
