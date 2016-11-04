package com.wms.base;

import com.wms.dto.Condition;
import com.wms.enums.Result;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static com.wms.utils.Constants.*;

/**
 * Created by duyot on 8/24/2016.
 */
@Repository
public class BaseDAOImpl<T extends BaseModel,ID extends Serializable> implements BaseDAOInteface {
    @Autowired
    SessionFactory sessionFactory;

    private Class<T> modelClass;

    Logger log = LoggerFactory.getLogger(BaseDAOImpl.class);

    public void setModelClass(Class modelClass){
        this.modelClass = modelClass;
    }

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    //--------------------------------------------------------------------
    public String getSysDate(String partern) throws Exception {
        String queryString = "SELECT to_char(sysdate,:id)  from dual";
        Query query = getSession().createSQLQuery(queryString);
        query.setParameter("id", partern);
        return query.list().get(0).toString();
    }

    public String getSysDate() {
        String queryString = "SELECT to_char(sysdate,:id)  from dual";
        try {
            Query query = getSession().createSQLQuery(queryString);
            query.setParameter("id", "dd/MM/yyyy hh24:mi:ss");
            return query.list().get(0).toString();
        } catch (Exception e) {
            log.error("Error when getting sysdate");
            return "";
        }
    }
    //--------------------------------------------------------------------
    //CURD
    @Transactional
    public String deleteById(long id){
        T object = (T)getSession().get(modelClass,id);
        return deleteByObject(object);
    }

    @Transactional
    public String deleteByObject(T obj) {
        try {
            getSession().delete(obj);
            return Result.SUCCESS.getName();
        } catch (Exception e) {
            log.info(e.toString());
            return e.getMessage();
        }
    }
    //--------------------------------------------------------------------
    @Transactional
    public String update(T obj) {
        try {
            getSession().update(obj);
            return Result.SUCCESS.getName();
        } catch (Exception e) {
            log.info(e.toString());
            return e.getMessage();
        }
    }
    //--------------------------------------------------------------------
    @Transactional
    public String saveOrUpdate(T obj) {
        try {
            getSession().saveOrUpdate(obj);
            return Result.SUCCESS.getName();
        } catch (Exception e) {
            log.info(e.toString());
            return e.getMessage();
        }
    }

    @Transactional
    public String save(T obj) {
        try {
            long savedObjectId = (long) getSession().save(obj);
            return savedObjectId +"";
        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public String saveBySession(T obj,Session session) {
        try {
            long id = (long) session.save(obj);
            session.flush();
            return id+"";
        } catch (Exception e) {
            log.info(e.toString());
            return e.getMessage();
        }
    }
    //--------------------------------------------------------------------
    //GET
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return (List<T>)getSession().createCriteria(modelClass).list();
    }

    @Transactional(readOnly = true)
    public List<T> getAllByPage(int pageNum, int countPerPage) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(modelClass);
        c.setMaxResults(countPerPage);
        c.setFirstResult(pageNum * countPerPage);
        return c.list();
    }

     @Transactional(readOnly = true)
    public List<T> getList(int count) {
        return getSession().createCriteria(modelClass).setMaxResults(count).list();

    }
    //--------------------------------------------------------------------
    //find
    @Transactional(readOnly = true)
    public T findById(long id) {
        return getSession().get(modelClass,id);
    }

    @Transactional(readOnly = true)
    public List<T> findByProperty(String property,String value) {
        return (List<T>)getSession().createCriteria(modelClass).add(Restrictions.eq(property,value)).list();
    }

    @Transactional(readOnly = true)
    public List<T> findByCondition(List<Condition> lstCondition) {
        Criteria cr = getSession().createCriteria(modelClass);

        if(DataUtil.isListNullOrEmpty(lstCondition)){
            return (List<T>)cr.list();
        }

        cr = initCriteria(cr,lstCondition);

        return (List<T>)cr.list();
    }



    //------------------------------------------------------------------------------------------------
    private Criteria initCriteria(Criteria cr,List<Condition> lstCondition){
        for(Condition i: lstCondition){
            String operator = i.getOperator();
            switch (operator){
                case "EQUAL":
                    cr.add(Restrictions.eq(i.getProperty(), i.getValue()));
                    break;
                case "NOT_EQUAL":
                    cr.add(Restrictions.ne(i.getProperty(), i.getValue()));
                    break;
                case "GREATER":
                    cr.add(Restrictions.gt(i.getProperty(), i.getValue()));
                    break;
                case "GREATER_EQAL":
                    cr.add(Restrictions.ge(i.getProperty(), i.getValue()));
                    break;
                case "LOWER":
                    cr.add(Restrictions.lt(i.getProperty(), i.getValue()));
                    break;
                case "LOWER_EQUAL":
                    cr.add(Restrictions.le(i.getProperty(), i.getValue()));
                    break;
                case "IN":
                    if(!DataUtil.isStringNullOrEmpty(i.getPropertyType()) && i.getPropertyType().equals(SQL_PRO_TYPE.LONG)){
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
                default:
                    cr.add(Restrictions.eq(i.getProperty(), i.getValue()));
                    break;
            }
        }

        return cr;
    }




}
