package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.CatGoodsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 12/9/2016.
 */
@RestController
@RequestMapping("/services/catGoodsServices")
public class CatGoodsServices extends BaseServices<CatGoodsDTO> {
    Logger log = LoggerFactory.getLogger(CatGoodsServices.class);
    @Autowired
    BaseBusinessInterface catGoodsBusiness;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = catGoodsBusiness;
    }
}
