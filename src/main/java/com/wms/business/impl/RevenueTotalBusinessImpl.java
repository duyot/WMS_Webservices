package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.RevenueTotalDTO;
import com.wms.persistents.dao.RevenueTotalDAO;
import com.wms.persistents.model.ReceivableHistory;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("revenueTotalBusiness")
public class RevenueTotalBusinessImpl extends BaseBusinessImpl<RevenueTotalDTO, RevenueTotalDAO> {
    @Autowired
    RevenueTotalDAO revenueTotalDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = revenueTotalDAO;
        this.entityClass = RevenueTotalDTO.class;
        this.revenueTotalDAO.setModelClass(ReceivableHistory.class);
        this.tDTO = new RevenueTotalDTO();
    }
}
