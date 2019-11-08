package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.business.interfaces.MjrOrderBusinessInterface;
import com.wms.dto.*;
import com.wms.persistents.model.MjrOrder;
import com.wms.persistents.model.MjrOrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 4/4/2017.
 */
@RestController
@RequestMapping("/services/mjrOrderServices")
public class MjrOrderServices extends BaseServices<MjrOrderDTO> {
    @Autowired
    MjrOrderBusinessInterface mjrOrderBusiness;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = mjrOrderBusiness;
    }

    @RequestMapping(value = "/orderExport",produces = "application/json",method = RequestMethod.POST)
    public ResponseObject orderExport(@RequestBody OrderExportDTO orderExportDTO){
        log.info("-------------------------------");
        ResponseObject exportResult = mjrOrderBusiness.orderExport(orderExportDTO.getMjrOrderDTO(),orderExportDTO.getLstMjrOrderDetailDTOS());
        log.info("Export result: "+ exportResult);
        return exportResult;
    }
}
