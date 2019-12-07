package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.ErrorLog;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 12/23/2016.
 */
@Repository
@Transactional
public class ErrorLogDAO extends BaseDAOImpl<ErrorLog, Long> {
}
