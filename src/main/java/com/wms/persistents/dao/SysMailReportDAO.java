package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.SysMailReport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 5/18/2017.
 */
@Repository
@Transactional
public class SysMailReportDAO extends BaseDAOImpl<SysMailReport, Long> {
}
