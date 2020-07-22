package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.ReceivableHistory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ReceivableHistoryDAO extends BaseDAOImpl<ReceivableHistory, Long> {
}
