package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.SysMenu;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 11/2/2016.
 */
@Repository
@Transactional
public class MapUserStockDAO extends BaseDAOImpl<SysMenu, Long> {
}
