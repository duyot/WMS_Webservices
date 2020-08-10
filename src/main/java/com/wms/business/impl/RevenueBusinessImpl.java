package com.wms.business.impl;

/**
 * Created by doanlv4 on 2/17/2017.
 */

import com.wms.base.BaseBusinessImpl;
import com.wms.business.interfaces.RevenueBusinessInterface;
import com.wms.dto.RevenueDTO;
import com.wms.persistents.dao.RevenueDAO;
import com.wms.persistents.model.Revenue;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("revenueBusiness")
public class RevenueBusinessImpl extends BaseBusinessImpl<RevenueDTO, RevenueDAO> implements RevenueBusinessInterface {
    @Autowired
    RevenueDAO revenueDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = revenueDAO;
        this.entityClass = RevenueDTO.class;
        this.revenueDAO.setModelClass(Revenue.class);
        this.tDTO = new RevenueDTO();
    }

}
