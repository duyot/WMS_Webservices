package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.ActionDTO;
import com.wms.dto.CustomerDTO;
import com.wms.persistents.dao.ActionDAO;
import com.wms.persistents.dao.CustomerDAO;
import com.wms.persistents.model.Action;
import com.wms.persistents.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 12/6/2016.
 */
@Service("customerBusiness")
public class CustomerBusinessImpl extends BaseBusinessImpl<CustomerDTO, CustomerDAO> {
    @Autowired
    CustomerDAO customerDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = customerDAO;
        this.entityClass = CustomerDTO.class;
        this.customerDAO.setModelClass(Customer.class);
        this.tDTO = new CustomerDTO();
    }
}
