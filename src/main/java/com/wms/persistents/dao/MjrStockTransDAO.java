package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.dto.MjrStockTransDTO;
import com.wms.enums.Responses;
import com.wms.persistents.model.MjrStockTrans;
import com.wms.utils.DataUtil;
import com.wms.utils.FunctionUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 12/28/2016.
 */
@Repository
@Transactional
public class MjrStockTransDAO extends BaseDAOImpl<MjrStockTrans, Long> {

    Logger log = LoggerFactory.getLogger(MjrStockTransDAO.class);

    public String saveStockTransByConnection(MjrStockTransDTO mjrStockTransDTO, Connection connection) {
        StringBuilder sql = new StringBuilder();
        List params = initSaveParams(mjrStockTransDTO);
        sql.append("Insert into MJR_STOCK_TRANS ");
        sql.append(" (ID,CODE,CUST_ID,STOCK_ID,CONTRACT_NUMBER,INVOICE_NUMBER,TYPE,STATUS,CREATED_DATE,CREATED_USER,DESCRIPTION,PARTNER_ID,PARTNER_NAME,RECEIVE_ID,RECEIVE_NAME, trans_money_total, REASON_ID, REASON_NAME) ");
        sql.append(" values (?,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),?,?,?,?,?,?,?,?,?) ");
        //
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql.toString());

            for (int idx = 0; idx < params.size(); idx++) {
                stm.setString(idx + 1, (String) DataUtil.nvl(params.get(idx), null));
            }
            stm.execute();
        } catch (SQLException ex) {
            log.info(ex.toString());
            return Responses.ERROR.getName();
        } finally {
            FunctionUtils.closeStatement(stm);
        }
        return Responses.SUCCESS.getName();
        //

    }

    public List initSaveParams(MjrStockTransDTO stockTrans) {
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
        params.add(stockTrans.getReceiveId());//
        params.add(stockTrans.getReceiveName());//
        params.add(stockTrans.getTransMoneyTotal());//
        params.add(stockTrans.getReasonId());//
        params.add(stockTrans.getReasonName());//
        return params;
    }
}

