package com.wms.services;

import com.wms.base.BaseServices;
import com.wms.business.interfaces.MjrOrderBusinessInterface;
import com.wms.dto.MjrOrderDTO;
import com.wms.dto.MjrOrderDetailDTO;
import com.wms.dto.OrderExportDTO;
import com.wms.dto.RealExportExcelDTO;
import com.wms.dto.ResponseObject;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/orderExport", produces = "application/json", method = RequestMethod.POST)
    public ResponseObject orderExport(@RequestBody OrderExportDTO orderExportDTO) {
        log.info("-------------------------------");
        ResponseObject exportResult = mjrOrderBusiness.orderExport(orderExportDTO.getMjrOrderDTO(), orderExportDTO.getLstMjrOrderDetailDTOS());
        log.info("Export result: " + exportResult);
        return exportResult;
    }

    @RequestMapping(value = "/getExportData/{id}", produces = "application/json", method = RequestMethod.GET)
    public List<RealExportExcelDTO> getExportData(@PathVariable("id") Long id) {
        return mjrOrderBusiness.orderExportData(id);
    }

    @RequestMapping(value = "/getListOrderDetail/{orderId}", produces = "application/json", method = RequestMethod.GET)
    public List<MjrOrderDetailDTO> getListOrderDetail(@PathVariable("orderId") Long orderId) {
        return mjrOrderBusiness.getListOrderDetail(orderId);
    }

    @RequestMapping(value = "/deleteOrder/{orderId}", produces = "application/json", method = RequestMethod.GET)
    public ResponseObject deleteOrder(@PathVariable("orderId") Long orderId) {
        return mjrOrderBusiness.deleteOrder(orderId);
    }
}
