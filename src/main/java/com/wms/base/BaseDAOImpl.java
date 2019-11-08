package com.wms.base;

import com.google.common.collect.Lists;
import com.wms.dto.Condition;
import com.wms.enums.Responses;
import com.wms.utils.AccessorUtil;
import com.wms.utils.Constants;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by duyot on 8/24/2016.
 */
@Repository
@Transactional
public class BaseDAOImpl<T extends BaseModel, ID extends Serializable> implements BaseDAOInteface {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SessionFactory sessionFactory;

    private Class<T> modelClass;

    private Logger log = LoggerFactory.getLogger(BaseDAOImpl.class);

    public void setModelClass(Class modelClass) {
        this.modelClass = modelClass;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    //SEQUENCE----------------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public Long getSequence(String sequence) {
        String sql = "select " + sequence + ".nextval from dual";
        Query query = getSession().createSQLQuery(sql);
        return ((BigDecimal) query.list().get(0)).longValue();
    }

    //SYSDATE-----------------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public String getSysDate(String pattern) {
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

    //@DELETE-----------------------------------------------------------------------------------------------------------
    @Transactional
    public String deleteById(long id) {
        T object = (T) getSession().get(modelClass, id);
        if (object == null) {
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

    public String deleteByObjectSession(T obj, Session session) {
        try {
            session.delete(obj);
            return Responses.SUCCESS.getName();
        } catch (Exception e) {
            log.info(e.toString());
            return Responses.ERROR.getName();
        }
    }

    //UPDATE-----------------------------------------------------------------------------------------------------------
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

    public String updateBySession(T obj, Session session) {
        try {
            session.update(obj);
            return Responses.SUCCESS.getName();
        } catch (Exception e) {
            log.info(e.toString());
            return Responses.ERROR.getName();
        }
    }

    @Transactional
    public String updateByProperties(T sourceObject, Long id, String[] copiedProperties) {
        try {
            Session session = getSession();
            T targetObject = session.get(modelClass, id);
            AccessorUtil.copyClass(sourceObject, targetObject, copiedProperties);
            session.update(targetObject);
            return Responses.SUCCESS.getName();
        } catch (Exception e) {
            log.info(e.toString());
            return Responses.ERROR.getName();
        }
    }

    //SAVE-----------------------------------------------------------------------------------------------------------
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
            return savedObjectId + "";
        } catch (ConstraintViolationException e) {
            log.info(e.toString());
            return e.getConstraintName();
        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
            return Responses.ERROR.getName();
        }
    }

    @Transactional
    public String save(List<T> lstObj) {
        try {
            for (T obj : lstObj) {
                getSession().save(obj);
            }
            return Responses.SUCCESS.getName();
        } catch (ConstraintViolationException e) {
            log.info(e.toString());
            return e.getConstraintName();
        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
            return Responses.ERROR.getName();
        }
    }

    @Transactional
    public String saveBySession(T obj, Session session) {
        try {
            long id = (long) session.save(obj);
            session.flush();
            return id + "";
        } catch (ConstraintViolationException e) {
            log.info(e.toString());
            return e.getConstraintName();
        } catch (Exception e) {
            log.info(e.toString());
            return Responses.ERROR.getName();
        }
    }

    @Transactional
    public String saveBySession(List<T> lstObj, Session session) {
        try {
            for (T obj : lstObj) {
                session.save(obj);
            }
            return Responses.SUCCESS.getName();
        } catch (ConstraintViolationException e) {
            log.info(e.toString());
            return e.getConstraintName();
        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
            return Responses.ERROR.getName();
        }
    }
    //GET--------------------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return (List<T>) getSession().createCriteria(modelClass).list();
    }

    @Transactional(readOnly = true)
    public List<T> getAllByPage(int pageNum, int countPerPage) {
        Criteria c = getSession().createCriteria(modelClass);
        c.setMaxResults(countPerPage);
        c.setFirstResult(pageNum * countPerPage);
        return c.list();
    }

    @Transactional(readOnly = true)
    public List<T> getList(int count) {
        return getSession().createCriteria(modelClass).setMaxResults(count).list();

    }

    //FIND--------------------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public T findById(long id) {
        return getSession().get(modelClass, id);
    }

    @Transactional(readOnly = true)
    public List<T> findByProperty(String property, String value) {
        return (List<T>) getSession().createCriteria(modelClass).add(Restrictions.eq(property, value)).list();
    }

    @Transactional(readOnly = true)
    public List<T> findByCondition(List<Condition> lstCondition) {
//        CriteriaQuery criteriaQuery = (CriteriaQuery) getSession().getCriteriaBuilder().createQuery(modelClass);
        Criteria cr = getSession().createCriteria(modelClass);

        if (DataUtil.isListNullOrEmpty(lstCondition)) {
            return (List<T>) cr.list();
        }

        cr = FunctionUtils.initCriteria(cr, lstCondition);
        try {
            return (List<T>) cr.list();
        } catch (HibernateException e) {
            e.printStackTrace();
            return Lists.newArrayList();
        }
    }

    @Transactional(readOnly = true)
    public Long countByCondition(List<Condition> lstCondition) {
        Criteria cr = getSession().createCriteria(modelClass);

        if (DataUtil.isListNullOrEmpty(lstCondition)) {
            return 0L;
        }

        cr = FunctionUtils.initCriteria(cr, lstCondition);
        return (Long) cr.setProjection(Projections.rowCount()).uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<T> findByConditionSession(List<Condition> lstCondition, Session session) {
        Criteria cr = session.createCriteria(modelClass);
        if (DataUtil.isListNullOrEmpty(lstCondition)) {
            return Lists.newArrayList();
        }

        cr = FunctionUtils.initCriteria(cr, lstCondition);

        try {
            return (List<T>) cr.list();
        } catch (HibernateException e) {
            e.printStackTrace();
            return Lists.newArrayList();
        }
    }

    public void buildConditionQueryList(StringBuilder sql, List<Condition> lstCondition) {
        int index = 0;
        for (Condition con : lstCondition) {
            sql.append(Constants.SQL_LOGIC.AND);
            sql.append(con.getProperty());
            sql.append(con.getOperator());
            if (con.getPropertyType().equals(Constants.SQL_PRO_TYPE.LONG)) {
                if (!con.getOperator().equals(Constants.SQL_OP.OP_IN)) {
                    sql.append(" :idx").append(String.valueOf(index++));
                } else {
                    sql.append("( :idx").append(String.valueOf(index++)).append(" )");
                }
            } else if (con.getPropertyType().equals(Constants.SQL_PRO_TYPE.DATE)) {
                sql.append(" to_date(:idx").append(String.valueOf(index++))
                        .append(", '").append(Constants.DATETIME_FORMAT.ddMMyyyy).append("')");
            } else if (con.getPropertyType().equals(Constants.SQL_PRO_TYPE.STRING)) {
                sql.append(":idx").append(String.valueOf(index++));
                if (con.getOperator().equals(Constants.SQL_OP.OP_LIKE)) {
                    sql.append(" ESCAPE '\\' ");
                }
            }

        }
    }


    public void fillConditionQuery(Query query, List<Condition> lstCondition) {
        int index = 0;
        for (Condition con : lstCondition) {
            if (con.getPropertyType().equals(Constants.SQL_PRO_TYPE.LONG)) {
                if (!con.getOperator().equals(Constants.SQL_OP.OP_IN)) {
                    query.setParameter("idx" + String.valueOf(index++), Long.parseLong(con.getValue().toString()));
                } else {
                    query.setParameterList("idx" + String.valueOf(index++), DataUtil.parseInputListLong(con.getValue().toString()));
                }

            } else if (con.getPropertyType().equals(Constants.SQL_PRO_TYPE.STRING)) {
                if (con.getOperator().equals(Constants.SQL_OP.OP_IN)) {
                    query.setParameterList("idx" + String.valueOf(index++), DataUtil.parseInputListString(con.getValue().toString()));
                } else {
                    query.setParameter("idx" + String.valueOf(index++), con.getValue().toString());
                }
            } else {
                query.setParameter("idx" + String.valueOf(index++), con.getValue().toString());
            }

        }
    }

    public String deleteByCondition(List<Condition> lstCondition) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("delete from ");
            sql.append(modelClass.getSimpleName());
            sql.append(" where 1=1 ");

            if (lstCondition != null) {
                buildConditionQueryList(sql, lstCondition);
                Query query = getSession().createQuery(sql.toString());
                fillConditionQuery(query, lstCondition);
                query.executeUpdate();
            }
            return Responses.SUCCESS.getName();

        } catch (HibernateException he) {
            log.error(he.getMessage(), he);
            return Responses.ERROR.getName();
        }
    }
}
