package com.wms.base;

import com.google.common.collect.Lists;
import com.wms.dto.Condition;
import com.wms.enums.Responses;
import com.wms.persistents.model.MjrStockGoodsTotal;
import com.wms.utils.DataUtil;
import com.wms.utils.FunctionUtils;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by duyot on 8/24/2016.
 */
@Repository
@Transactional
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
    @Transactional(readOnly = true)
    public Long getSequence(String sequense) {
        String sql = "select " + sequense + ".nextval from dual";
        Query query = getSession().createSQLQuery(sql);
        return ((BigDecimal) query.list().get(0)).longValue();
    }
    //--------------------------------------------------------------------
    @Transactional(readOnly = true)
    public String getSysDate(String pattern){
        String queryString = "SELECT to_char(sysdate,:id)  from dual";
        Query query = getSession().createSQLQuery(queryString);
        query.setParameter("id", pattern);
        try {
            return query.list().get(0).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    //-----------------------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true)
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
        if(object == null){
            return Responses.NOT_FOUND.getName();
        }
        return deleteByObject(object);
    }

    @Transactional
    public String deleteByObject(T obj) {
        try {
            getSession().delete(obj);
            return Responses.SUCCESS.getName();
        } catch (Exception e) {
            log.info(e.toString());
            return Responses.ERROR.getName();
        }
    }

    @Transactional
    public String deleteByObjectSession(T obj,Session session) {
        try {
            session.delete(obj);
            return Responses.SUCCESS.getName();
        } catch (Exception e) {
            log.info(e.toString());
            return Responses.ERROR.getName();
        }
    }
    //--------------------------------------------------------------------
    @Transactional
    public String update(T obj) {
        try {
            getSession().update(obj);
            return Responses.SUCCESS.getName();
        } catch (Exception e) {
            log.info(e.toString());
            return Responses.ERROR.getName();
        }
    }
    //--------------------------------------------------------------------
    @Transactional
    public String updateBySession(T obj,Session session) {
        try {
            session.update(obj);
            return Responses.SUCCESS.getName();
        } catch (Exception e) {
            log.info(e.toString());
            return Responses.ERROR.getName();
        }
    }
    //--------------------------------------------------------------------
    @Transactional
    public String saveOrUpdate(T obj) {
        try {
            getSession().saveOrUpdate(obj);
            return Responses.SUCCESS.getName();
        } catch (Exception e) {
            log.info(e.toString());
            return Responses.ERROR.getName();
        }
    }

    @Transactional
    public String save(T obj) {
        try {
            long savedObjectId = (long) getSession().save(obj);
            return savedObjectId +"";
        } catch (ConstraintViolationException e) {
            log.info(e.toString());
            return e.getConstraintName();
        }catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
            return Responses.ERROR.getName();
        }
    }

    @Transactional
    public String saveBySession(T obj,Session session) {
        try {
            long id = (long) session.save(obj);
            session.flush();
            return id+"";
        } catch (ConstraintViolationException e) {
            log.info(e.toString());
            return e.getConstraintName();
        } catch (Exception e){
            log.info(e.toString());
            return Responses.ERROR.getName();
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

    //------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public List<T> findByProperty(String property,String value) {
        return (List<T>)getSession().createCriteria(modelClass).add(Restrictions.eq(property,value)).list();
    }

    //------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public List<T> findByCondition(List<Condition> lstCondition) {
        Criteria cr = getSession().createCriteria(modelClass);

        if(DataUtil.isListNullOrEmpty(lstCondition)){
            return (List<T>)cr.list();
        }

        cr = FunctionUtils.initCriteria(cr,lstCondition);
        try {
            return (List<T>)cr.list();
        } catch (HibernateException e) {
            e.printStackTrace();
            return Lists.newArrayList();
        }
    }//
    public Long countByCondition(List<Condition> lstCondition) {
        Criteria cr = getSession().createCriteria(modelClass);

        if(DataUtil.isListNullOrEmpty(lstCondition)){
            return 0l;
        }

        cr = FunctionUtils.initCriteria(cr,lstCondition);
        return  (Long)cr.setProjection(Projections.rowCount()).uniqueResult();
    }//
    //------------------------------------------------------------------------------------------------
    public List<T> findByConditionSession(List<Condition> lstCondition, Session session) {
        Criteria cr = session.createCriteria(modelClass);
        if(DataUtil.isListNullOrEmpty(lstCondition)){
            return Lists.newArrayList();
        }

        cr = FunctionUtils.initCriteria(cr,lstCondition);

        try {
            return (List<T>)cr.list();
        } catch (HibernateException e) {
            e.printStackTrace();
            return Lists.newArrayList();
        }
    }
}
