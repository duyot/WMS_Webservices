package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.ReceivableHistoryDTO;
import com.wms.persistents.dao.ReceivableHistoryDAO;
import com.wms.persistents.model.ReceivableHistory;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("receivableHistoryBusiness")
public class ReceivableHistoryBusinessImpl extends BaseBusinessImpl<ReceivableHistoryDTO, ReceivableHistoryDAO> {
    @Autowired
    ReceivableHistoryDAO receivableHistoryDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = receivableHistoryDAO;
        this.entityClass = ReceivableHistoryDTO.class;
        this.receivableHistoryDAO.setModelClass(ReceivableHistory.class);
        this.tDTO = new ReceivableHistoryDTO();
    }
}
