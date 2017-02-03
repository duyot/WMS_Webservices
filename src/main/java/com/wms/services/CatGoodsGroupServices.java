package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 12/7/2016.
 */
@RestController
@RequestMapping("/services/catGoodsGroupServices")
public class CatGoodsGroupServices  extends BaseServices<CatGoodsGroupDTO> {
    Logger log = LoggerFactory.getLogger(CatGoodsGroupServices.class);
    @Autowired
    BaseBusinessInterface catGoodsGroupBusiness;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = catGoodsGroupBusiness;
    }
}
