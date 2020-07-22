package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.RevenueTotal;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RevenueTotalDAO extends BaseDAOImpl<RevenueTotal, Long> {
}
