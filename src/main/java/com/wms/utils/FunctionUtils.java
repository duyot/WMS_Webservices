package com.wms.utils;

import com.wms.base.OrderOracleVietnameseSort;
import com.wms.business.impl.StockManagementBusinessImpl;
import com.wms.dto.Condition;
import com.wms.dto.MjrStockTransDetailDTO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by duyot on 12/19/2016.
 */
public class FunctionUtils {
    public static Logger log = LoggerFactory.getLogger(StockManagementBusinessImpl.class);

    public static void writeIEGoodsLog(List<MjrStockTransDetailDTO> lstGoods, Logger log){
        StringBuilder strLog = new StringBuilder();
        for(MjrStockTransDetailDTO item: lstGoods){
            strLog.append("\nItem: "+ item.getGoodsCode()+" serial:"+ item.getSerial()+" state: "+ item.getGoodsState()+ " amount: "+ item.getAmount());
        }
        log.info(strLog.toString());
    }
    public static String removeScientificNotation(String number){
        BigDecimal num = new BigDecimal(number);
        return num.toPlainString();
    }

    public static String formatNumber(String number){
        if (!DataUtil.isStringNullOrEmpty(number)) {
            String plainNumber = removeScientificNotation(number);
            double dNumber = Double.valueOf(plainNumber);
            if (dNumber%1 != 0) {
                return String.format("%,.4f", dNumber);
            }else{
                return String.format("%,.0f", dNumber);
            }
        }else{
            return "";
        }
    }

    public static Float convertStringToFloat(MjrStockTransDetailDTO goodsDetail){
        try {
            return Float.valueOf(goodsDetail.getOutputPrice());
        } catch (NumberFormatException e) {
            log.info("Convert error output price: "+ goodsDetail.getOutputPrice() + "-"+ goodsDetail.getGoodsCode());
            return null;
        }
    }

    public static void commit(Transaction transaction, Connection con) {
        try {
            if (transaction != null) {
                transaction.commit();
            }
            if (con != null) {
                con.commit();
            }

        } catch (SQLException ex) {
            log.info(ex.toString());
        }
    }

    public static void commit(Transaction transaction) {
        try {
            if (transaction != null) {
                transaction.commit();
            }
        } catch (Exception ex) {
            log.info(ex.toString());
        }
    }

    public static void closeConnection(Session session,Connection connection){
        try {
            if (session.isOpen()) {
                session.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            log.info(ex.toString());
        }
    }

    public static void closeSession(Session session){
        try {
            if (session.isOpen()) {
                session.close();
            }
        } catch (Exception ex) {
            log.info(ex.toString());
        }
    }


    public static void rollback(Transaction transaction, Connection con) {
        try {
            if (con != null) {
                con.rollback();
            }
            if (transaction != null) {
                transaction.rollback();
            }

            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException ex) {
            log.info(ex.toString());
        }
    }

    public static void rollback(Transaction transaction) {
        try {

            if (transaction != null) {
                transaction.rollback();
            }
        } catch (Exception ex) {
            log.info(ex.toString());
        }
    }

    public static String  randomString(){
        return ThreadLocalRandom.current().nextInt(10000, 99998 + 1)+"";
    }



    public static Criteria initCriteria(Criteria cr, List<Condition> lstCondition) {
        for (Condition i : lstCondition) {
            if (DataUtil.isNullOrEmpty(i.getOperator())){
                cr.add(buildQueryCriterion(i));
            }else{
            switch (i.getOperator()){
                case "LIMIT":
                    cr.setMaxResults((int) i.getValue());
                case "VNM_ORDER":
                    if (i.getValue().toString().equalsIgnoreCase("asc")) {
                        cr.addOrder(OrderOracleVietnameseSort.asc(i.getProperty()));
                    } else {
                        cr.addOrder(OrderOracleVietnameseSort.desc(i.getProperty()));
                    }
                    break;
                case "ORDER":
                    if (i.getValue().toString().equalsIgnoreCase("asc")) {
                        cr.addOrder(Order.asc(i.getProperty()));
                    } else {
                        cr.addOrder(Order.desc(i.getProperty()));
                    }
                    break;
                case "OFFSET":
                    cr.setFirstResult((int) i.getValue());
                    break;
                 default:
                        cr.add(buildQueryCriterion(i));
            } }
        }
        return cr;
    }
    public static Criterion buildQueryCriterion( Condition condition){
        if (Constants.SQL_LOGIC.OR.equalsIgnoreCase(condition.getExpType()) ||Constants.SQL_LOGIC.AND.equalsIgnoreCase(condition.getExpType())) {
            return buildOrCriterion(condition.getLstCondition(),condition.getExpType());
        } else {
            return buildCriterion(condition);
        }
    }
    public static Criterion buildOrCriterion( List<Condition> lstCondition , String sqlLogic){

        List<Criterion> lstOrCriterion = new ArrayList<>();
        for (Condition con :lstCondition){
            lstOrCriterion.add(buildQueryCriterion(con));
        }
        Criterion[] array = lstOrCriterion.toArray(new Criterion[lstOrCriterion.size()]);
        if (Constants.SQL_LOGIC.OR.equalsIgnoreCase(sqlLogic) ){
            return Restrictions.or(array);
        }else {
            return Restrictions.and(array);
        }

    }
    public static Criterion buildCriterion( Condition con){
        String operator = con.getOperator();
        switch (operator){
            case "EQUAL":
                if(!DataUtil.isStringNullOrEmpty(con.getPropertyType())){
                    if (con.getPropertyType().equals(Constants.SQL_PRO_TYPE.LONG)) {
                        //
                        Long[] value = new Long[1];
                        value[0] = Long.parseLong(con.getValue()+"");
                        return Restrictions.in(con.getProperty(),Arrays.asList(value));
                        //
                    }else if (con.getPropertyType().equals(Constants.SQL_PRO_TYPE.BYTE)){
                        //
                        Byte[] value = new Byte[1];
                        value[0] = Byte.parseByte(con.getValue()+"");
                        return Restrictions.in(con.getProperty(),Arrays.asList(value));
                        //
                    }else if(con.getPropertyType().equals(Constants.SQL_PRO_TYPE.INT)){
                        //
                        Integer[] value = new Integer[1];
                        value[0] = Integer.parseInt(con.getValue()+"");
                        return Restrictions.in(con.getProperty(),Arrays.asList(value));
                    }else{
                        return Restrictions.eq(con.getProperty(), con.getValue()).ignoreCase();
                    }
                }
            case "NOT_EQUAL":
                return Restrictions.ne(con.getProperty(), con.getValue());
            case "GREATER":
                return Restrictions.gt(con.getProperty(), con.getValue());
            case "GREATER_EQUAL":
                 return Restrictions.ge(con.getProperty(), con.getValue());
            case "LOWER":
                return Restrictions.lt(con.getProperty(), con.getValue());
            case "LOWER_EQUAL":
                return Restrictions.le(con.getProperty(), con.getValue());
            case "IN":
                if(!DataUtil.isStringNullOrEmpty(con.getPropertyType()) && con.getPropertyType().equals(Constants.SQL_PRO_TYPE.LONG)){

                    List<Integer> lstValue = (List<Integer>) con.getValue();;
                    List<Long> lstLong = new ArrayList<>();
                    for(Integer interger :lstValue ){
                        lstLong.add(interger.longValue());
                    }
                    return Restrictions.in(con.getProperty(),lstLong);
                }else{
                    String inValue = (String) con.getValue();
                    String[] inValues = inValue.split(",");
                    return Restrictions.in(con.getProperty(), inValues);
                }
            case "LIKE":
                return Restrictions.like(con.getProperty(), "%"+ con.getValue()+"%").ignoreCase();
            case "BETWEEN":
                String[] arrValue = con.getValue().toString().split("\\|");
                try {
                    String toDateStr = arrValue[1].length() <= 10 ? arrValue[1] + " 23:59:59":arrValue[1];
                    Date fromDate = DateTimeUtils.convertStringToDate(arrValue[0]);
                    Date toDate   = DateTimeUtils.convertStringToDate(toDateStr);
                   return Restrictions.between(con.getProperty(),fromDate,toDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            default:
                return  Restrictions.eq(con.getProperty(), con.getValue());
        }
    }
}
