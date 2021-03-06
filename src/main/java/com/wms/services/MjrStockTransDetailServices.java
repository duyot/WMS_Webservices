package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.MjrStockTransDetailDTO;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services/mjrStockTransDetailServices")
public class MjrStockTransDetailServices extends BaseServices<MjrStockTransDetailDTO> {
    @Autowired
    BaseBusinessInterface mjrStockTransDetailBusiness;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = mjrStockTransDetailBusiness;
    }
}
