package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.CatCustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 12/6/2016.
 */
@RestController
@RequestMapping("/customerServices")
public class CustomerServices extends BaseServices<CatCustomerDTO>{
    Logger log = LoggerFactory.getLogger(CustomerServices.class);
    @Autowired
    BaseBusinessInterface customerBusiness;

    @PostConstruct
    public void setupServices(){
        this.baseBusiness = customerBusiness;
    }

}
