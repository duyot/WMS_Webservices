package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.MapUserStockDTO;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/services/mapUserStockServices")
public class MapUserStockServices extends BaseServices<MapUserStockDTO> {
    @Autowired
    BaseBusinessInterface mapUserStockImpl;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = mapUserStockImpl;
    }
}