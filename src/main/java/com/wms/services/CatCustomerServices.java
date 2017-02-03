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
@RequestMapping("/services/catCustomerServices")
public class CatCustomerServices extends BaseServices<CatCustomerDTO>{
    Logger log = LoggerFactory.getLogger(CatCustomerServices.class);
    @Autowired
    BaseBusinessInterface catCustomerBusiness;

    @PostConstruct
    public void setupServices(){
        this.baseBusiness = catCustomerBusiness;
    }

}
