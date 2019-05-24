package com.wms.persistents.dao;

import com.google.common.collect.Lists;
import com.wms.base.BaseDAOImpl;
import com.wms.dto.*;
import com.wms.enums.Responses;
import com.wms.persistents.model.CatGoods;
import com.wms.persistents.model.ErrorLog;
import com.wms.persistents.model.MjrStockGoodsTotal;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.FunctionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.DateType;
import org.hibernate.type.FloatType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duyot on 12/19/2016.
 */
@Transactional
@Repository
public class MjrStockGoodsTotalDAO extends BaseDAOImpl<MjrStockGoodsTotal,Long> {

    @Autowired
    ErrorLogDAO errorLogDAO;

    @Autowired
    CatGoodsDAO catGoodsDAO;
    @Autowired
    MjrStockGoodsSerialDAO mjrStockGoodsSerialDAO;

    Logger log = LoggerFactory.getLogger(MjrStockGoodsTotalDAO.class);


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


    @Transactional
    public List<MjrStockGoodsTotalDTO> findMoreCondition(MjrStockGoodsTotalDTO stockGoodsTotal) {

        StringBuilder str = new StringBuilder();
        List<String> lstParamsStockGooods = new ArrayList<>();
        List<String> lstParamsStockGooodsSerial = new ArrayList<>();
        StringBuilder strStockGoods = new StringBuilder();
        StringBuilder strStockGoodsSerial = new StringBuilder();
        strStockGoods.append("select a.goods_id, b.code as goods_code,b.name as goods_name, a.cust_id, a.goods_state, a.stock_id, c.name as stock_name, sum (a.amount) as amount, max (a.changed_date) changed_date \n" +
                "                 from MJR_STOCK_GOODS a, \n" +
                "                 cat_goods b,\n" +
                "                 cat_stock c \n" +
                "                 where 1=1 \n" +
                "                 and a.goods_id = b.id\n" +
                "                 and a.stock_id = c.id\n" +
                "                 and a.cust_id =? ");
        lstParamsStockGooods.add(stockGoodsTotal.getCustId());

        strStockGoodsSerial.append("select a.goods_id, b.code as goods_code,b.name as goods_name, a.cust_id, a.goods_state, a.stock_id, c.name as stock_name, sum (a.amount) as amount, max (a.changed_date) changed_date \n" +
                "                 from MJR_STOCK_GOODS_SERIAL a, \n" +
                "                 cat_goods b,\n" +
                "                 cat_stock c \n" +
                "                 where 1=1 \n" +
                "                 and a.goods_id = b.id\n" +
                "                 and a.stock_id = c.id\n" +
                "                 and a.cust_id =?\n");
        lstParamsStockGooodsSerial.add(stockGoodsTotal.getCustId());

        if(!DataUtil.isStringNullOrEmpty(stockGoodsTotal.getStockId())){
            strStockGoods.append("  and a.stock_id = ?");
            strStockGoodsSerial.append("  and a.stock_id = ?");
            lstParamsStockGooods.add(stockGoodsTotal.getStockId());
            lstParamsStockGooodsSerial.add(stockGoodsTotal.getStockId());
        }
        if(!DataUtil.isStringNullOrEmpty(stockGoodsTotal.getPartnerId())){
            strStockGoods.append("  and a.partner_id in ("+stockGoodsTotal.getPartnerId()+")");
            strStockGoodsSerial.append("  and a.partner_id in ("+stockGoodsTotal.getPartnerId()+")");
            //lstParamsStockGooods.add(stockGoodsTotal.getPartnerId());
            //lstParamsStockGooodsSerial.add(stockGoodsTotal.getPartnerId());
        }
        if(!DataUtil.isStringNullOrEmpty(stockGoodsTotal.getGoodsState())){
            strStockGoods.append("  and a.goods_state = ?");
            strStockGoodsSerial.append("  and a.goods_state = ?");
            lstParamsStockGooods.add(stockGoodsTotal.getGoodsState());
            lstParamsStockGooodsSerial.add(stockGoodsTotal.getGoodsState());
        }
        if(!DataUtil.isStringNullOrEmpty(stockGoodsTotal.getGoodsId())){
            strStockGoods.append("  and a.goods_id = ?");
            strStockGoodsSerial.append("  and a.goods_id = ?");
            lstParamsStockGooods.add(stockGoodsTotal.getGoodsId());
            lstParamsStockGooodsSerial.add(stockGoodsTotal.getGoodsId());
        }

        strStockGoods.append(" group by a.goods_id, b.code, b.name, a.cust_id, a.goods_state, a.stock_id, c.name");
        strStockGoodsSerial.append(" group by a.goods_id, b.code, b.name, a.cust_id, a.goods_state, a.stock_id, c.name");

        str.append(strStockGoods + "\n");
        str.append(" union all \n");
        str.append(strStockGoodsSerial);

        Query ps = getSession().createSQLQuery(str.toString())
                .addScalar("goods_id",      StringType.INSTANCE)
                .addScalar("goods_code",      StringType.INSTANCE)
                .addScalar("goods_name",      StringType.INSTANCE)
                .addScalar("cust_id",      StringType.INSTANCE)
                .addScalar("goods_state",      StringType.INSTANCE)
                .addScalar("stock_id",      StringType.INSTANCE)
                .addScalar("stock_name",      StringType.INSTANCE)
                .addScalar("amount",      StringType.INSTANCE)
                .addScalar("changed_date",      StringType.INSTANCE)
                ;
        int idx = 0;
        //1.Chen tham so de lay Stock_goods
        ps.setLong(idx,Long.valueOf(lstParamsStockGooods.get(idx++)));//cust_id stock_goods
        if(!DataUtil.isStringNullOrEmpty(stockGoodsTotal.getStockId())){
            ps.setLong(idx,Long.valueOf(lstParamsStockGooods.get(idx++)));//stock_id stock_goods
        }
        /*if(!DataUtil.isStringNullOrEmpty(stockGoodsTotal.getPartnerId())){
            ps.setLong(idx,Long.valueOf(lstParamsStockGooods.get(idx++)));//partner_id stock_goods
        }*/
        if(!DataUtil.isStringNullOrEmpty(stockGoodsTotal.getGoodsState())){
            ps.setString(idx,lstParamsStockGooods.get(idx++));//goods_state stock_goods
        }
        if(!DataUtil.isStringNullOrEmpty(stockGoodsTotal.getGoodsId())){
            ps.setLong(idx,Long.valueOf(lstParamsStockGooods.get(idx++)));//goods_id stock_goods
        }
        //2.Chen tham so de lay Stock_goods_serial
        int serIdx = 0;
        ps.setLong(idx++,Long.valueOf(lstParamsStockGooodsSerial.get(serIdx++)));//cust_id stock_goods_serial
        if(!DataUtil.isStringNullOrEmpty(stockGoodsTotal.getStockId())){
            ps.setLong(idx++,Long.valueOf(lstParamsStockGooodsSerial.get(serIdx++)));//stock_id stock_goods_serial
        }
        /*if(!DataUtil.isStringNullOrEmpty(stockGoodsTotal.getPartnerId())){
            ps.setLong(idx++,Long.valueOf(lstParamsStockGooodsSerial.get(serIdx++)));//partner_id stock_goods_serial
        }*/
        if(!DataUtil.isStringNullOrEmpty(stockGoodsTotal.getGoodsState())){
            ps.setString(idx++,lstParamsStockGooodsSerial.get(serIdx++));//goods_state stock_goods_serial
        }
        if(!DataUtil.isStringNullOrEmpty(stockGoodsTotal.getGoodsId())){
            ps.setLong(idx++,Long.valueOf(lstParamsStockGooodsSerial.get(serIdx++)));//goods_id stock_goods_serial
        }
        return  convertToStockGoodsTotal(ps.list());
    }


    private List<MjrStockGoodsTotalDTO> convertToStockGoodsTotal(List<Object[]> lstData){
        List<MjrStockGoodsTotalDTO> lstResult = Lists.newArrayList();
        for(Object[] i: lstData){
            MjrStockGoodsTotalDTO temp = new MjrStockGoodsTotalDTO();
            temp.setGoodsId( i[0]==null?"":String.valueOf(i[0]));
            temp.setGoodsCode( i[1]==null?"":String.valueOf(i[1]));
            temp.setGoodsName( i[2]==null?"":String.valueOf(i[2]));
            temp.setCustId( i[3]==null?"":String.valueOf(i[3]));
            temp.setGoodsState(i[4]==null?"":String.valueOf(i[4]));
            temp.setStockId( i[5]==null?"": String.valueOf(i[5]));
            temp.setStockName( i[6]==null?"":String.valueOf(i[6]));
            temp.setAmount( i[7]==null?"":String.valueOf(i[7]));
            temp.setChangeDate( i[8]==null?"":String.valueOf(i[8]));

            lstResult.add(temp);
        }
        return lstResult;
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
        sqlUpdateTotal.append("   AND a.goods_state = ? ");/////////////////////////////////////////////////

        try {
            PreparedStatement ps = connection.prepareStatement(sqlUpdateTotal.toString());
            ps.setString(1, stockGoodsTotal.getAmount()+"");
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
