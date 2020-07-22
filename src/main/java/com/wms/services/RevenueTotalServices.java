package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.RevenueTotalDTO;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services/revenueTotalServices")
public class RevenueTotalServices extends BaseServices<RevenueTotalDTO> {
    @Autowired
    BaseBusinessInterface revenueTotalBusiness;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = revenueTotalBusiness;
    }
}
