package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.CatCustomerDTO;
import com.wms.persistents.dao.CatCustomerDAO;
import com.wms.persistents.model.CatCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 12/6/2016.
 */
@Service("catCustomerBusiness")
public class CatCustomerBusinessImpl extends BaseBusinessImpl<CatCustomerDTO, CatCustomerDAO> {
    @Autowired
    CatCustomerDAO catCustomerDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = catCustomerDAO;
        this.entityClass = CatCustomerDTO.class;
        this.catCustomerDAO.setModelClass(CatCustomer.class);
        this.tDTO = new CatCustomerDTO();
    }
}
