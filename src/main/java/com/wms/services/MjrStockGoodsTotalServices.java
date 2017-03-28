package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.MjrStockGoodsTotalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 3/24/2017.
 */
@RestController
@RequestMapping("/services/mjrStockGoodsTotalServices")
public class MjrStockGoodsTotalServices extends BaseServices<MjrStockGoodsTotalDTO> {
    @Autowired
    BaseBusinessInterface mjrStockGoodsTotalBusiness;

    @PostConstruct
    public void setupServices(){
        this.baseBusiness = mjrStockGoodsTotalBusiness;
    }
}
