package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.Condition;
import com.wms.dto.CustomerDTO;
import com.wms.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by duyot on 12/6/2016.
 */
@RestController
@RequestMapping("/customerservices")
public class CustomerServices extends BaseServices<CustomerDTO>{
    Logger log = LoggerFactory.getLogger(CustomerServices.class);
    @Autowired
    BaseBusinessInterface customerBusiness;

    @PostConstruct
    public void setupServices(){
        this.baseBusiness = customerBusiness;
    }

}
