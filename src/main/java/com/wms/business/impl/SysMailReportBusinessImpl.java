package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.SysMailReportDTO;
import com.wms.persistents.dao.SysMailReportDAO;
import com.wms.persistents.model.SysMailReport;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duyot on 5/18/2017.
 */
@Service("sysMailReportBusiness")
public class SysMailReportBusinessImpl extends BaseBusinessImpl<SysMailReportDTO, SysMailReportDAO> {
    @Autowired
    SysMailReportDAO sysMailReportDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = sysMailReportDAO;
        this.entityClass = SysMailReportDTO.class;
        this.sysMailReportDAO.setModelClass(SysMailReport.class);
        this.tDTO = new SysMailReportDTO();
    }
}
