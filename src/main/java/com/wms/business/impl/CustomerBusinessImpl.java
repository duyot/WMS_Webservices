package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.CatCustomerDTO;
import com.wms.persistents.dao.CustomerDAO;
import com.wms.persistents.model.CatCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 12/6/2016.
 */
@Service("customerBusiness")
public class CustomerBusinessImpl extends BaseBusinessImpl<CatCustomerDTO, CustomerDAO> {
    @Autowired
    CustomerDAO customerDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = customerDAO;
        this.entityClass = CatCustomerDTO.class;
        this.customerDAO.setModelClass(CatCustomer.class);
        this.tDTO = new CatCustomerDTO();
    }
}
