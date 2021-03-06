package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.Err$MjrStockGoodsSerialDTO;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by duyot on 3/7/2017.
 */
@RestController
@RequestMapping("/services/err$MjrStockGoodsSerialServices")
public class Err$MjrStockGoodsSerialServices extends BaseServices<Err$MjrStockGoodsSerialDTO> {
    @Autowired
    BaseBusinessInterface err$MjrStockGoodsSerialBusiness;

    @PostConstruct
    public void setupServices() {
        this.baseBusiness = err$MjrStockGoodsSerialBusiness;
    }
}
