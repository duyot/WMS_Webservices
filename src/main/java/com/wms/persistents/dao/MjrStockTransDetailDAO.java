package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.MjrStockTransDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 12/29/2016.
 */
@Repository
@Transactional
public class MjrStockTransDetailDAO extends BaseDAOImpl<MjrStockTransDetail,Long> {
    @Autowired
    SessionFactory sessionFactory;

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }
}
