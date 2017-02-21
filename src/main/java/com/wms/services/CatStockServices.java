package com.wms.services;

<<<<<<< HEAD
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

=======
import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.CatStockDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 2/17/2017.
 */
@RestController
@RequestMapping("/services/catStockServices")
>>>>>>> f97d1f5817603b7b5087e101d6f6579d2945e4eb
public class CatStockServices extends BaseServices<CatStockDTO> {
    Logger log = LoggerFactory.getLogger(CatStockServices.class);
    @Autowired
    BaseBusinessInterface catStockBusiness;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = catStockBusiness;
    }
}
