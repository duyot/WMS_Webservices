package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.CatStockCellDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 4/19/2017.
 */
@RestController
@RequestMapping("/services/catStockCellServices")
public class CatStockCellServices extends BaseServices<CatStockCellDTO> {
    Logger log = LoggerFactory.getLogger(CatStockCellServices.class);
    @Autowired
    BaseBusinessInterface catStockCellBusiness;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = catStockCellBusiness;
    }
}
