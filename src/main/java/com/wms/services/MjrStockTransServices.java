package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.MjrStockTransDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 4/4/2017.
 */
@RestController
@RequestMapping("/services/mjrStockTransServices")
public class MjrStockTransServices extends BaseServices<MjrStockTransDTO> {
    @Autowired
    BaseBusinessInterface mjrStockTransBusiness;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = mjrStockTransBusiness;
    }
}
