package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.CatGoodsGroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 12/7/2016.
 */
@RestController
@RequestMapping("/services/catGoodsGroupServices")
public class CatGoodsGroupServices  extends BaseServices<CatGoodsGroupDTO> {
    @Autowired
    BaseBusinessInterface catGoodsGroupBusiness;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = catGoodsGroupBusiness;
    }
}
