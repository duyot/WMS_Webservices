package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.AppParamsDTO;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by duyot on 12/7/2016.
 */
@RestController
@RequestMapping("/services/appParamsServices")
public class AppParamsServices extends BaseServices<AppParamsDTO> {
    @Autowired
    BaseBusinessInterface appParamsBusiness;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = appParamsBusiness;
    }
}


