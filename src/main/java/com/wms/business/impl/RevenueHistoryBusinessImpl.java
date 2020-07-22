package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.RevenueHistoryDTO;
import com.wms.persistents.dao.RevenueHistoryDAO;
import com.wms.persistents.model.ReceivableHistory;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("revenueHistoryBusiness")
public class RevenueHistoryBusinessImpl extends BaseBusinessImpl<RevenueHistoryDTO, RevenueHistoryDAO> {
    @Autowired
    RevenueHistoryDAO revenueHistoryDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = revenueHistoryDAO;
        this.entityClass = RevenueHistoryDTO.class;
        this.revenueHistoryDAO.setModelClass(ReceivableHistory.class);
        this.tDTO = new RevenueHistoryDTO();
    }
}
