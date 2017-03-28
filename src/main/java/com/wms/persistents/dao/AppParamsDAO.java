package com.wms.persistents.dao;

/**
 * Created by doanlv4 on 25/03/2017.
 */
import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.AppParams;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by doanlv4 on 2/17/2017.
 */
@Repository
@Transactional
public class AppParamsDAO extends BaseDAOImpl<AppParams,Long> {
    @Autowired
    SessionFactory sessionFactory;

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }
}
