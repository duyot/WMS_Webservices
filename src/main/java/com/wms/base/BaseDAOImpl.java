package com.wms.base;

import com.wms.enums.Result;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by duyot on 8/24/2016.
 */
@Repository
public class BaseDAOImpl<T extends BaseModel,ID extends Serializable> implements BaseDAOInteface {
    @Autowired
    SessionFactory sessionFactory;

    private Class<T> modelClass;

    public void setModelClass(Class modelClass){
        this.modelClass = modelClass;
    }

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public String getSysDate(String pattern) throws Exception {
        String queryString = "SELECT to_char(sysdate,:id)  from dual";
        Query query = getSession().createSQLQuery(queryString);
        query.setParameter("id", pattern);
        return query.list().get(0).toString();
    }
    //crud
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
            System.out.println(e.toString());
            return e.getMessage();
        }
    }

    @Transactional
    public String saveOrUpdate(T obj) {
        try {
            getSession().saveOrUpdate(obj);
            return Result.SUCCESS.getName();
        } catch (Exception e) {
            System.out.println(e.toString());
            return e.getMessage();
        }
    }

    @Transactional
    public String save(T obj) {
        try {
            long savedObjectId = (long) getSession().save(obj);
            return savedObjectId +"";
        } catch (Exception e) {
            System.out.println(e.toString());
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
            System.out.println(e.toString());
            return e.getMessage();
        }
    }
    //
    //GET
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return getSession().createCriteria(modelClass).list();
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

    //find
    @Transactional(readOnly = true)
    public T findById(long id) {
        return getSession().get(modelClass,id);
    }

    @Transactional(readOnly = true)
    public List<T> findByProperty(String property,String value) {
        return getSession().createCriteria(modelClass).add(Restrictions.eq(property,value)).list();
    }
}
