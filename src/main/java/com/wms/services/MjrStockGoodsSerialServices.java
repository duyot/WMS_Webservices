package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.MjrStockGoodsSerialDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 3/24/2017.
 */
@RestController
@RequestMapping("/services/mjrStockGoodsSerialServices")
public class MjrStockGoodsSerialServices extends BaseServices<MjrStockGoodsSerialDTO> {
    Logger log = LoggerFactory.getLogger(MjrStockGoodsSerialServices.class);
    @Autowired
    BaseBusinessInterface mjrStockGoodsSerialBusiness;

    @PostConstruct
    public void setupServices(){
        this.baseBusiness = mjrStockGoodsSerialBusiness;
    }
}
