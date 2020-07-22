package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.ReceivableHistoryDTO;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services/receivableHistoryServices")
public class ReceivableHistoryServices extends BaseServices<ReceivableHistoryDTO> {
    @Autowired
    BaseBusinessInterface receivableHistoryBusiness;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = receivableHistoryBusiness;
    }
}
