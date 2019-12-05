package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.CatCustomer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 12/6/2016.
 */
@Repository
@Transactional
public class CatCustomerDAO extends BaseDAOImpl<CatCustomer, Long> {
}
