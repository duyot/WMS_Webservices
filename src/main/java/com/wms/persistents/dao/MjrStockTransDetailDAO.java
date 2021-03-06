package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.MjrStockTransDetail;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 12/29/2016.
 */
@Repository
@Transactional
public class MjrStockTransDetailDAO extends BaseDAOImpl<MjrStockTransDetail, Long> {
}
