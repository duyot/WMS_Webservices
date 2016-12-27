package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.business.interfaces.MjrStockGoodsTotalBusinessInterface;
import com.wms.dto.MjrStockGoodsTotalDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 12/19/2016.
 */
@RestController
@RequestMapping("/mjrStockGoodsTotalservices")
public class MjrStockGoodsTotalServices extends BaseServices<MjrStockGoodsTotalDTO> {
    Logger log = LoggerFactory.getLogger(MjrStockGoodsTotalServices.class);

    @Autowired
    BaseBusinessInterface mjrStockGoodsTotalBusiness;

    @Autowired
    MjrStockGoodsTotalBusinessInterface mjrStockGoodsTotalAdvanced;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = mjrStockGoodsTotalBusiness;
    }
}
