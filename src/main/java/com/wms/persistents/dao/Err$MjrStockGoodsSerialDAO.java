package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.Err$MjrStockGoodsSerial;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 3/6/2017.
 */
@Transactional
@Repository
public class Err$MjrStockGoodsSerialDAO extends BaseDAOImpl<Err$MjrStockGoodsSerial, Long> {
}
