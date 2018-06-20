package com.wms.persistents.dao;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseDAOImpl;
import com.wms.dto.MjrStockGoodsTotalDTO;
import com.wms.dto.MjrStockTransDTO;
import com.wms.enums.Responses;
import com.wms.persistents.model.MjrStockGoodsTotal;
import com.wms.persistents.model.MjrStockTrans;
import com.wms.utils.DataUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by duyot on 12/28/2016.
 */
@Repository
@Transactional
public class MjrStockTransDAO extends BaseDAOImpl<MjrStockTrans,Long> {
    @Autowired
    SessionFactory sessionFactory;
    Logger log = LoggerFactory.getLogger(MjrStockTransDAO.class);
    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public String saveStockTransByConnection(MjrStockTransDTO mjrStockTransDTO, Connection connection){
        StringBuilder sql = new StringBuilder();
        List params = initSaveParams(mjrStockTransDTO);
        sql.append("Insert into MJR_STOCK_TRANS ");
        sql.append(" (ID,CODE,CUST_ID,STOCK_ID,CONTRACT_NUMBER,INVOICE_NUMBER,TYPE,STATUS,CREATED_DATE,CREATED_USER,DESCRIPTION,PARTNER_ID,PARTNER_NAME) ");
        sql.append(" values (?,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),?,?,?,?) ");
        //
        try {
            PreparedStatement stm = connection.prepareStatement(sql.toString());

            for (int idx = 0; idx < params.size(); idx++) {
                stm.setString(idx + 1, (String) DataUtil.nvl(params.get(idx), null));
            }
            stm.execute();
        } catch (SQLException ex) {
            log.info(ex.toString());
            return Responses.ERROR.getName();
        }
        return Responses.SUCCESS.getName();
        //

    }

    public List initSaveParams(MjrStockTransDTO stockTrans){
        List params = new ArrayList();
        params.add(stockTrans.getId());//
        params.add(stockTrans.getCode());//
        params.add(stockTrans.getCustId());//
        params.add(stockTrans.getStockId());//
        params.add(stockTrans.getContractNumber());//
        params.add(stockTrans.getInvoiceNumber());//
        params.add(stockTrans.getType());//
        params.add(stockTrans.getStatus());//
        params.add(stockTrans.getCreatedDate());//
        params.add(stockTrans.getCreatedUser());//
        params.add(stockTrans.getDescription());//
        params.add(stockTrans.getPartnerId());//
        params.add(stockTrans.getPartnerName());//
        return params;
    }
}

