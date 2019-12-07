package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.CatStockCell;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 4/19/2017.
 */
@Repository
@Transactional
public class CatStockCellDAO extends BaseDAOImpl<CatStockCell, Long> {
}
