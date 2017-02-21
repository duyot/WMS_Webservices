package com.wms.services;

/**
 * Created by doanlv4 on 2/17/2017.
 */

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
@RestController
@RequestMapping("/services/catStockServices")

public class CatStockServices extends BaseServices<CatStockDTO> {
    Logger log = LoggerFactory.getLogger(CatStockServices.class);
    @Autowired
    BaseBusinessInterface catStockBusiness;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = catStockBusiness;
    }
}
