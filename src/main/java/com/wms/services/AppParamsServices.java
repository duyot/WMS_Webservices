package com.wms.services;

/**
 * Created by doanlv4 on 25/03/2017.
 */
import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 12/7/2016.
 */
@RestController
@RequestMapping("/services/appParamsServices")
public class AppParamsServices extends BaseServices<AppParamsDTO> {
    Logger log = LoggerFactory.getLogger(AppParamsServices.class);
    @Autowired
    BaseBusinessInterface appParamsBusiness;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = appParamsBusiness;
    }
}


