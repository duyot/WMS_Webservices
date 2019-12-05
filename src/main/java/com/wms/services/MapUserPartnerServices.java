package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.MapUserPartnerDTO;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/services/mapUserPartnerServices")
public class MapUserPartnerServices extends BaseServices<MapUserPartnerDTO> {
    @Autowired
    BaseBusinessInterface mapUserPartnerImpl;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = mapUserPartnerImpl;
    }
}