package com.wms.services;

import com.wms.business.interfaces.StockManagermentBusinessInterface;
import com.wms.dto.MjrStockGoodsTotalDTO;
import com.wms.dto.ResponseObject;
import com.wms.enums.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by duyot on 12/19/2016.
 */
@RestController
@RequestMapping("/stockmanagermentservices")
public class StockManagementServices {
    Logger log = LoggerFactory.getLogger(StockManagementServices.class);

    @Autowired
    StockManagermentBusinessInterface stockManagementBusiness;
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/import",produces = "application/json")
    public ResponseObject importStock(@RequestBody MjrStockGoodsTotalDTO stockGoodsTotalDTO){
        log.info("-------------------------------");
        log.info("Import info: "+ stockGoodsTotalDTO.getAmount());
        String importResult = stockManagementBusiness.importStock(stockGoodsTotalDTO);
        log.info("Return info: "+ importResult);
        return new ResponseObject(Responses.SUCCESS.getCode(),Responses.SUCCESS.getName(),stockGoodsTotalDTO.getId());
    }
}
