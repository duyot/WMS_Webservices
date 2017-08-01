package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.CatPartnerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 12/7/2016.
 */
@RestController
@RequestMapping("/services/catPartnerServices")
public class CatPartnerServices extends BaseServices<CatPartnerDTO> {
    @Autowired
    BaseBusinessInterface catPartnerBusiness;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = catPartnerBusiness;
    }
}