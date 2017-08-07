package com.wms.persistents.dao;

import com.google.common.collect.Lists;
import com.wms.business.impl.StockManagementBusinessImpl;
import com.wms.dto.*;
import com.wms.enums.Responses;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import oracle.jdbc.OracleTypes;
import org.hibernate.*;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.result.Output;
import org.hibernate.result.ResultSetOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by duyot on 3/6/2017.
 */
@Repository
@Transactional
public class StockFunctionDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    MjrStockGoodsTotalDAO mjrStockGoodsTotalDAO;

    @Autowired
    MjrStockGoodsSerialDAO mjrStockGoodsSerialDAO;

    @Autowired
    MjrStockGoodsDAO mjrStockGoodsDAO;

    @Autowired
    MjrStockTransDAO mjrStockTransDAO;

    @Autowired
    MjrStockTransDetailDAO mjrStockTransDetailDAO;

    private Logger log = LoggerFactory.getLogger(StockManagementBusinessImpl.class);

    @Transactional
    public List<MjrStockTransDetailDTO> getTransGoodsDetail(String custId, String stockId, String transId, String transType){
        Session session;
        List<Object[]> lstResult = null;
        try {
            session = sessionFactory.getCurrentSession();
            ProcedureCall pc =  session.createStoredProcedureCall("pkg_stock_info.p_get_trans_goods_details");
            pc.registerParameter("p_cust_id", Integer.class,ParameterMode.IN).bindValue   (Integer.parseInt(custId));
            pc.registerParameter("p_stock_id", Integer.class,ParameterMode.IN).bindValue   (Integer.parseInt(stockId));
            pc.registerParameter("p_stock_trans_id", Integer.class,ParameterMode.IN).bindValue   (Integer.parseInt(transId));
            pc.registerParameter("p_trans_type", Integer.class,ParameterMode.IN).bindValue   (Integer.parseInt(transType));
            pc.registerParameter("p_result", Class.class,ParameterMode.REF_CURSOR);
            Output output = pc.getOutputs().getCurrent();
            if (output.isResultSet()) {
               lstResult = ((ResultSetOutput) output).getResultList();
            }
            return convertToDetail(lstResult);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private List<MjrStockTransDetailDTO> convertToDetail(List<Object[]> lstData){
        List<MjrStockTransDetailDTO> lstResult = Lists.newArrayList();
        for(Object[] i: lstData){
            MjrStockTransDetailDTO temp = new MjrStockTransDetailDTO();
            temp.setGoodsId((i[0]+""));
            temp.setGoodsState((String) i[1]);
            temp.setCellCode((String) i[2]);
            temp.setAmount(i[3]+"");
            temp.setSerial((String) i[4]);
            temp.setInputPrice(i[5]==null?"":i[5]+"");
            temp.setOutputPrice(i[6]==null?"":i[6]+"");
            //
            lstResult.add(temp);
        }
        return lstResult;
    }

    @Transactional
    public Long getCountGoodsDetail(String custId, String stockId, String goodsId, String goodsState, String isSerial){
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            ProcedureCall pc =  session.createStoredProcedureCall("pkg_stock_info.count_goods_details");
            pc.registerParameter("p_cust_id", Integer.class,ParameterMode.IN).bindValue   (Integer.parseInt(custId));
            pc.registerParameter("p_stock_id", Integer.class,ParameterMode.IN).bindValue   (Integer.parseInt(stockId));
            pc.registerParameter("p_goods_id", Integer.class,ParameterMode.IN).bindValue   (Integer.parseInt(goodsId));
            pc.registerParameter("p_goods_state", Integer.class,ParameterMode.IN).bindValue   (Integer.parseInt(goodsState));
            pc.registerParameter("p_is_serial", Integer.class,ParameterMode.IN).bindValue   (Integer.parseInt(isSerial));
            pc.registerParameter("p_total", Long.class,ParameterMode.OUT);

            return (Long) pc.getOutputs().getOutputParameterValue("p_total");
        }catch (Exception e){
            e.printStackTrace();
            return 0L;
        }
    }
    //
    @Transactional
    public List<String> getListSerialInStock(String custId, String stockId, String goodsId, String goodsState) {
        Session session = sessionFactory.getCurrentSession();
        StringBuilder str = new StringBuilder();
        str.append("SELECT serial FROM mjr_stock_goods_serial a")
                .append(" WHERE ")
                .append("     a.cust_id  = :custId ")
                .append(" and a.stock_id = :stockId ")
                .append(" and a.goods_id = :goodsId ")
                .append(" and a.goods_state = :goodsState ")
                .append(" and a.status = 1 ")
                .append(" order by serial desc ");
        Query ps = session.createSQLQuery(str.toString());
        ps.setInteger("custId",Integer.parseInt(custId));
        ps.setInteger("stockId",Integer.parseInt(stockId));
        ps.setInteger("goodsId",Integer.parseInt(goodsId));
        ps.setInteger("goodsState",Integer.parseInt(goodsState));

        return ps.list();
    }


    @Transactional
    public ResponseObject cancelTransaction(String transId) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            ProcedureCall pc =  session.createStoredProcedureCall("pkg_stock_transaction.f_destroy_command");
            pc.registerParameter("p_command_id", Integer.class,ParameterMode.IN).bindValue   (Integer.parseInt(transId));
            pc.registerParameter("v_result", String.class,ParameterMode.OUT);

            String result = (String) pc.getOutputs().getOutputParameterValue("v_result");
            return getResponseFromResult(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new ResponseObject(Responses.ERROR.getName(),Responses.ERROR.getName(),"");

    }

    public ResponseObject getResponseFromResult(String result){
        ResponseObject responseObject = new ResponseObject();
        String [] resultArr = result.split("\\|");
        String resultCode = resultArr[0];
        String resultName = resultArr[1];
        if("1".equalsIgnoreCase(resultCode)){
            responseObject.setStatusCode(Responses.SUCCESS.getName());
        }else{
            responseObject.setStatusCode(Responses.ERROR.getName());
            responseObject.setStatusName(resultName);
        }
        return responseObject;
    }
    //------------------------------------------------------------------------------------------------------------------
    public String getTotalStockTransaction(String stockId, Connection connection){
        String sql = " select count(*) trans_num from mjr_stock_trans where type = 1 and stock_id = ? ";
        int count = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(sql.toString());
            ps.setInt(1,Integer.parseInt(stockId));
            ResultSet rs =  ps.executeQuery();
            while(rs.next()){
                count = rs.getInt("trans_num");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            count = 0;
        }
        return String.format("%08d", count);
    }
    //
    public String getTotalStockTransaction(String stockId, Session session){
        String sql = " select count(*) from MJR_STOCK_TRANS where  type = 2 and stock_id = :stockId ";
        Query ps = session.createSQLQuery(sql);
        ps.setInteger("stockId",Integer.parseInt(stockId));
        BigDecimal countNumber = (BigDecimal) ps.uniqueResult();

        return String.format("%08d",countNumber.intValue());
    }
    //------------------------------------------------------------------------------------------------------------------
    public ResponseObject exportStockGoodsDetail(MjrStockTransDTO mjrStockTransDTO, MjrStockTransDetailDTO goodsDetail, Session session){
        //
        ResponseObject exportResult = new ResponseObject();
        //1. save mjr_trans_detail
        goodsDetail.setStockTransId(mjrStockTransDTO.getId());
        String savedStockTranDetailId = mjrStockTransDetailDAO.saveBySession(goodsDetail.toModel(),session);
        if(!DataUtil.isInteger(savedStockTranDetailId)){
            exportResult.setStatusCode(Responses.ERROR.getName());
            exportResult.setStatusName(Responses.ERROR_CREATE_STOCK_TRANS_DETAIL.getName());
            return exportResult;
        }
        //
        if(goodsDetail.isSerial()){
            exportResult = mjrStockGoodsSerialDAO.exportStockGoodsSerial(mjrStockTransDTO, goodsDetail, session);
        }else{
            exportResult = mjrStockGoodsDAO.exportStockGoods(mjrStockTransDTO,goodsDetail,session);
        }
        //
        return exportResult;
    }

    public ResponseObject updateExportStockGoodsTotal(MjrStockTransDTO mjrStockTransDTO, Map<String, Float> mapGoodsNumber, Session session) {
        Iterator iterator = mapGoodsNumber.entrySet().iterator();
        String goodsInfo;
        Float amount;
        MjrStockGoodsTotalDTO total = new MjrStockGoodsTotalDTO();
        total.setCustId(mjrStockTransDTO.getCustId());
        total.setStockId(mjrStockTransDTO.getStockId());
        total.setChangeDate(mjrStockTransDTO.getCreatedDate());
        //
        ResponseObject updateTotalResult;
        //
        while (iterator.hasNext()){
            Map.Entry pair = (Map.Entry)iterator.next();
            //
            goodsInfo        = (String) pair.getKey();
            amount           = (Float) pair.getValue();
            String[] goodsInfoArr = goodsInfo.split(",");
            String goodsId    = goodsInfoArr[0];
            String goodsState = goodsInfoArr[1];
            total.setAmount(amount+"");
            total.setGoodsId(goodsId);
            total.setGoodsState(goodsState);
            updateTotalResult = mjrStockGoodsTotalDAO.updateExportTotal(total,session);
            if(!Responses.SUCCESS.getName().equalsIgnoreCase(updateTotalResult.getStatusCode())){
                return updateTotalResult;
            }
        }
        return new ResponseObject(Responses.SUCCESS.getName(),Responses.SUCCESS.getName());
    }

    public String saveStockTransByConnection(MjrStockTransDTO mjrStockTransDTO,Connection connection){
        return mjrStockTransDAO.saveStockTransByConnection(mjrStockTransDTO,connection);
    }


    public String saveStockGoodsTotalByConnection(MjrStockTransDTO mjrStockTransDTO,Map<String,Float> mapGoodsNumber,
                                           Map<String,CatGoodsDTO> mapGoods,Connection connection){
        Iterator it = mapGoodsNumber.entrySet().iterator();
        String updateResult;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            //
            String key = (String) pair.getKey();
            Float amount     = (Float) pair.getValue();
            String goodsCode = key.split(",")[0];
            String goodsState = key.split(",")[1];
            CatGoodsDTO goods = mapGoods.get(goodsCode);
            MjrStockGoodsTotalDTO stockGoodsTotal = new MjrStockGoodsTotalDTO();
            stockGoodsTotal.setCustId(mjrStockTransDTO.getCustId());
            stockGoodsTotal.setStockId(mjrStockTransDTO.getStockId());
            stockGoodsTotal.setGoodsId(goods.getId());
            stockGoodsTotal.setGoodsCode(goods.getCode());
            stockGoodsTotal.setGoodsName(goods.getName());
            stockGoodsTotal.setGoodsState(goodsState);
            stockGoodsTotal.setAmount(amount+"");
            stockGoodsTotal.setChangeDate(mjrStockTransDTO.getCreatedDate());

            updateResult = mjrStockGoodsTotalDAO.updateImportTotal(stockGoodsTotal,connection);
            if(!Responses.SUCCESS.getName().equalsIgnoreCase(updateResult)){
                return updateResult;
            }
            //
        }

        return Responses.SUCCESS.getName();
    }

    public String importStockGoods(MjrStockTransDTO mjrStockTransDTO, List<MjrStockTransDetailDTO> lstGoodsDetail, Connection connection){
        //
        List paramsStockTransDetail;
        List paramsStockGoodsSerial;
        List paramsStockGoods;
        //PREPARE STATEMENTS
        PreparedStatement prstmtInsertStockTransDetail;
        PreparedStatement prstmtInsertStockGoodsSerial;
        PreparedStatement prstmtInsertStockGoods;
        //SQL
        StringBuilder sqlStockTransDetail = new StringBuilder();
        StringBuilder sqlStockGoodsSerial = new StringBuilder();
        StringBuilder sqlStockGoods = new StringBuilder();
        //STOCK_TRANS_DETAIL
        sqlStockTransDetail.append(" Insert into MJR_STOCK_TRANS_DETAIL ");
        sqlStockTransDetail.append(" (ID,STOCK_TRANS_ID,GOODS_ID,GOODS_CODE,GOODS_STATE,IS_SERIAL,AMOUNT,SERIAL,INPUT_PRICE,CELL_CODE)  ");
        sqlStockTransDetail.append(" values  (SEQ_MJR_STOCK_TRANS_DETAIL.nextval,?,?,?,?,?,?,?,?,?) ");
        //STOCK_GOODS_SERIAL
        sqlStockGoodsSerial.append(" Insert into MJR_STOCK_GOODS_SERIAL  ");
        sqlStockGoodsSerial.append(" (ID,CUST_ID,STOCK_ID,GOODS_ID,GOODS_STATE,CELL_CODE,AMOUNT,SERIAL,IMPORT_DATE,CHANGED_DATE,STATUS,PARTNER_ID,IMPORT_STOCK_TRANS_ID,INPUT_PRICE)  ");
        sqlStockGoodsSerial.append(" values (SEQ_MJR_STOCK_GOODS_SERIAL.nextval,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),to_date(?,'dd/MM/yyyy hh24:mi:ss'),?,?,?,?) ");
        sqlStockGoodsSerial.append(" LOG ERRORS INTO ERR$_MJR_STOCK_GOODS_SERIAL REJECT LIMIT UNLIMITED ");
        //STOCK_GOODS
        sqlStockGoods.append(" Insert into MJR_STOCK_GOODS ");
        sqlStockGoods.append(" (ID,CUST_ID,STOCK_ID,GOODS_ID,GOODS_STATE,CELL_CODE,AMOUNT,IMPORT_DATE,CHANGED_DATE,STATUS,PARTNER_ID,IMPORT_STOCK_TRANS_ID,INPUT_PRICE)  ");
        sqlStockGoods.append(" values  (SEQ_MJR_STOCK_GOODS.nextval,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),to_date(?,'dd/MM/yyyy hh24:mi:ss'),?,?,?,?)  ");
        sqlStockGoods.append(" LOG ERRORS INTO ERR$_MJR_STOCK_GOODS_SERIAL REJECT LIMIT UNLIMITED ");
        //3. TAO PREPARE STATEMENT
        try {
            prstmtInsertStockTransDetail = connection.prepareStatement(sqlStockTransDetail.toString());
            prstmtInsertStockGoodsSerial = connection.prepareStatement(sqlStockGoodsSerial.toString());
            prstmtInsertStockGoods = connection.prepareStatement(sqlStockGoods.toString());
            //
            int count = 0;
            //
            for (MjrStockTransDetailDTO goods : lstGoodsDetail) {
                //
                count++;
                //DETAIL
                paramsStockTransDetail = setParamsStockTransSerial(mjrStockTransDTO,goods);
                //SET PARAMS AND ADD TO BATCH
                for (int idx = 0; idx < paramsStockTransDetail.size(); idx++) {
                    prstmtInsertStockTransDetail.setString(idx + 1, DataUtil.nvl(paramsStockTransDetail.get(idx), "").toString());
                }
                prstmtInsertStockTransDetail.addBatch();
                //
                if(Constants.IS_SERIAL.equalsIgnoreCase(goods.getIsSerial())){
                    paramsStockGoodsSerial = setParamsStockGoodsSerial(mjrStockTransDTO,goods);
                    for (int idx = 0; idx < paramsStockGoodsSerial.size(); idx++) {
                        prstmtInsertStockGoodsSerial.setString(idx + 1, DataUtil.nvl(paramsStockGoodsSerial.get(idx), "").toString());
                    }
                    prstmtInsertStockGoodsSerial.addBatch();
                }else{
                    paramsStockGoods = setParamsStockGoods(mjrStockTransDTO,goods);
                    for (int idx = 0; idx < paramsStockGoods.size(); idx++) {
                        prstmtInsertStockGoods.setString(idx + 1, DataUtil.nvl(paramsStockGoods.get(idx), "").toString());
                    }
                    prstmtInsertStockGoods.addBatch();
                }
                //
                if(count >= Constants.COMMIT_NUMBER){
                    try {
                        prstmtInsertStockTransDetail.executeBatch();
                        prstmtInsertStockGoodsSerial.executeBatch();
                        prstmtInsertStockGoods.executeBatch();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    count = 0;
                }
            }
            if(count > 0){
                try {
                    prstmtInsertStockTransDetail.executeBatch();
                    prstmtInsertStockGoodsSerial.executeBatch();
                    prstmtInsertStockGoods.executeBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //
            prstmtInsertStockTransDetail.close();
            prstmtInsertStockGoodsSerial.close();
            prstmtInsertStockGoods.close();


        } catch (SQLException e) {
            log.info(e.toString());
            return Responses.ERROR.getName();
        }

        return Responses.SUCCESS.getName();
    }

    private List setParamsStockTransSerial(MjrStockTransDTO transDetail, MjrStockTransDetailDTO goods) {
        List<String> paramsStockTrans = Lists.newArrayList();
        paramsStockTrans.add(transDetail.getId());
        paramsStockTrans.add(goods.getGoodsId());
        paramsStockTrans.add(goods.getGoodsCode());
        paramsStockTrans.add(goods.getGoodsState());
        paramsStockTrans.add(goods.getIsSerial());
        paramsStockTrans.add(goods.getAmount());
        paramsStockTrans.add(goods.getSerial());
        paramsStockTrans.add(goods.getInputPrice());
        paramsStockTrans.add(goods.getCellCode());
        return paramsStockTrans;
    }

    private List setParamsStockGoodsSerial(MjrStockTransDTO transDetail,MjrStockTransDetailDTO goods) {
        List<String> paramsStockTrans = Lists.newArrayList();
        paramsStockTrans.add(transDetail.getCustId());
        paramsStockTrans.add(transDetail.getStockId());
        paramsStockTrans.add(goods.getGoodsId ());
        paramsStockTrans.add(goods.getGoodsState());
        paramsStockTrans.add(goods.getCellCode());
        paramsStockTrans.add(goods.getAmount());
        paramsStockTrans.add(goods.getSerial());
        paramsStockTrans.add(transDetail.getCreatedDate());
        paramsStockTrans.add(transDetail.getCreatedDate());
        paramsStockTrans.add(Constants.STATUS.ACTIVE);
        paramsStockTrans.add("1");
        paramsStockTrans.add(transDetail.getId());
        paramsStockTrans.add(goods.getInputPrice());
        return paramsStockTrans;
    }

    private List setParamsStockGoods(MjrStockTransDTO transDetail,MjrStockTransDetailDTO goods) {
        List<String> paramsStockTrans = Lists.newArrayList();
        paramsStockTrans.add(transDetail.getCustId());
        paramsStockTrans.add(transDetail.getStockId());
        paramsStockTrans.add(goods.getGoodsId ());
        paramsStockTrans.add(goods.getGoodsState());
        paramsStockTrans.add(goods.getCellCode());
        paramsStockTrans.add(goods.getAmount());
        paramsStockTrans.add(transDetail.getCreatedDate());
        paramsStockTrans.add(transDetail.getCreatedDate());
        paramsStockTrans.add(Constants.STATUS.ACTIVE);
        paramsStockTrans.add("1");
        paramsStockTrans.add(transDetail.getId());
        paramsStockTrans.add(goods.getInputPrice());
        return paramsStockTrans;
    }


}
