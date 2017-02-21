package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.CatStock;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
<<<<<<< HEAD
 * Created by doanlv4 on 2/17/2017.
=======
 * Created by duyot on 2/17/2017.
>>>>>>> f97d1f5817603b7b5087e101d6f6579d2945e4eb
 */
@Repository
@Transactional
public class CatStockDAO extends BaseDAOImpl<CatStock,Long> {
    @Autowired
    SessionFactory sessionFactory;

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }
}
<<<<<<< HEAD

=======
>>>>>>> f97d1f5817603b7b5087e101d6f6579d2945e4eb
