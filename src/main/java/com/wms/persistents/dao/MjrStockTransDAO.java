package com.wms.persistents.dao;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.MjrStockGoodsTotal;
import com.wms.persistents.model.MjrStockTrans;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 12/28/2016.
 */
@Repository
@Transactional
public class MjrStockTransDAO extends BaseDAOImpl<MjrStockTrans,Long> {
    @Autowired
    SessionFactory sessionFactory;

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }
}

