package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.CatGoods;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 12/9/2016.
 */
@Repository
@Transactional
public class CatGoodsDAO extends BaseDAOImpl<CatGoods, Long> {
}
