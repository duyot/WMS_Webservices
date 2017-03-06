package com.wms.services;

import com.wms.business.interfaces.StockManagementBusinessInterface;
import com.wms.dto.MjrStockTransDTO;
import com.wms.dto.MjrStockTransDetailDTO;
import com.wms.dto.ResponseObject;
import com.wms.dto.StockTransDTO;
import com.wms.enums.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by duyot on 12/19/2016.
 */
@RestController
@RequestMapping("/services/stockManagementServices")
public class StockManagementServices {
    Logger log = LoggerFactory.getLogger(StockManagementServices.class);

    @Autowired
    StockManagementBusinessInterface stockManagementBusiness;
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/import",produces = "application/json")
    public ResponseObject importStock(@RequestBody StockTransDTO stockTransDTO){
        log.info("-------------------------------");
        ResponseObject importResult = stockManagementBusiness.importStock(stockTransDTO.getMjrStockTransDTO(),stockTransDTO.getLstMjrStockTransDetail());
        log.info("Result: "+ importResult);
        return importResult;
    }
}
