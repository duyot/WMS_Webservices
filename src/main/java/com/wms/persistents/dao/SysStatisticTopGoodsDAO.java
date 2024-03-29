package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.AppParams;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 5/19/2017.
 */
@Repository
@Transactional
public class SysStatisticTopGoodsDAO extends BaseDAOImpl<AppParams, Long> {
}
