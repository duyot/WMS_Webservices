package com.wms.utils;

import com.google.common.collect.Lists;
import com.wms.business.impl.StockManagementBusinessImpl;
import com.wms.dto.Condition;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by duyot on 12/19/2016.
 */
public class FunctionUtils {
    public static Logger log = LoggerFactory.getLogger(StockManagementBusinessImpl.class);

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


    //ROLLBACK
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

    public static String  randomString(){
        return ThreadLocalRandom.current().nextInt(10000, 99998 + 1)+"";
    }


    public static Criteria initCriteria(Criteria cr, List<Condition> lstCondition){
        for(Condition i: lstCondition){
            String operator = i.getOperator();
            switch (operator){
                case "EQUAL":
                    if(!DataUtil.isStringNullOrEmpty(i.getPropertyType()) && i.getPropertyType().equals(Constants.SQL_PRO_TYPE.LONG)){
                        Long[] value = new Long[1];
                        value[0] = Long.parseLong(i.getValue()+"");
                        cr.add(Restrictions.in(i.getProperty(),Arrays.asList(value)));
                    }else{
                        cr.add(Restrictions.eq(i.getProperty(), i.getValue()));
                        cr.add(Restrictions.eq(i.getProperty(), i.getValue()));
                    }
                    break;
                case "NOT_EQUAL":
                    cr.add(Restrictions.ne(i.getProperty(), i.getValue()));
                    break;
                case "GREATER":
                    cr.add(Restrictions.gt(i.getProperty(), i.getValue()));
                    break;
                case "GREATER_EQUAL":
                    cr.add(Restrictions.ge(i.getProperty(), i.getValue()));
                    break;
                case "LOWER":
                    cr.add(Restrictions.lt(i.getProperty(), i.getValue()));
                    break;
                case "LOWER_EQUAL":
                    cr.add(Restrictions.le(i.getProperty(), i.getValue()));
                    break;
                case "IN":
                    if(!DataUtil.isStringNullOrEmpty(i.getPropertyType()) && i.getPropertyType().equals(Constants.SQL_PRO_TYPE.LONG)){
                        Long[] value = (Long[]) i.getValue();
                        List<Long> lstValue = Lists.newArrayList();
                        for(int j = 0;j<value.length;j++){
                            lstValue.add(value[j]);
                        }
                        cr.add(Restrictions.in(i.getProperty(), lstValue));
                    }else{
                        String inValue = (String) i.getValue();
                        String[] inValues = inValue.split(",");
                        cr.add(Restrictions.in(i.getProperty(), inValues));
                    }
                    break;
                case "LIKE":
                    cr.add(Restrictions.like(i.getProperty(), "%"+ i.getValue()+"%"));
                    break;
                case "ORDER":
                    if(i.getValue().toString().equalsIgnoreCase("asc")){
                        cr.addOrder(Order.asc(i.getProperty()));
                    }else{
                        cr.addOrder(Order.desc(i.getProperty()));
                    }
                    break;
                case "BETWEEN":
                    String[] arrValue = i.getValue().toString().split("\\|");
                    try {
                        String toDateStr = arrValue[1].length() <= 10 ? arrValue[1] + " 23:59:59":arrValue[1];
                        Date fromDate = DateTimeUtils.convertStringToDate(arrValue[0]);
                        Date toDate   = DateTimeUtils.convertStringToDate(toDateStr);
                        cr.add(Restrictions.between(i.getProperty(),fromDate,toDate));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    cr.add(Restrictions.eq(i.getProperty(), i.getValue()));
                    break;
            }
        }

        return cr;
    }
}
