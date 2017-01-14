package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.ErrorLogDTO;
import com.wms.persistents.dao.ErrorLogDAO;
import com.wms.persistents.model.CatCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 12/23/2016.
 */
@Service("errorLogBusiness")
public class ErrorLogBusinessImpl extends BaseBusinessImpl<ErrorLogDTO, ErrorLogDAO> {
    @Autowired
    ErrorLogDAO errorLogDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = errorLogDAO;
        this.entityClass = ErrorLogDTO.class;
        this.errorLogDAO.setModelClass(CatCustomer.class);
        this.tDTO = new ErrorLogDTO();
    }
}
