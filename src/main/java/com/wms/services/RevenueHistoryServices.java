package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.RevenueHistoryDTO;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services/revenueHistoryServices")
public class RevenueHistoryServices extends BaseServices<RevenueHistoryDTO> {
    @Autowired
    BaseBusinessInterface revenueHistoryBusiness;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = revenueHistoryBusiness;
    }
}
