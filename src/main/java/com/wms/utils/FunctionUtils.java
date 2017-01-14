package com.wms.utils;

import com.wms.base.BaseBusinessInterface;
import com.wms.dto.Condition;
import com.wms.dto.ErrorLogDTO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by duyot on 12/19/2016.
 */
public class FunctionUtils {

    public static void closeSession(Session session){
        if(session != null && session.isOpen()){
            session.close();
        }
    }

    public static void rollBack(Transaction tx){
        tx.rollback();
    }


    public static Criteria initCriteria(Criteria cr, List<Condition> lstCondition){
        for(Condition i: lstCondition){
            String operator = i.getOperator();
            switch (operator){
                case "EQUAL":
                    if(!DataUtil.isStringNullOrEmpty(i.getPropertyType()) && i.getPropertyType().equals(Constants.SQL_PRO_TYPE.LONG)){
                        Long value = Long.parseLong(i.getValue()+"");
                        cr.add(Restrictions.in(i.getProperty(), value));
                    }else{
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
                        List<Long> lstValue = Arrays.asList((Long[])i.getValue());
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
