package com.wms.services;

import com.wms.business.interfaces.StockManagementBusinessInterface;
import com.wms.dto.ResponseObject;
import com.wms.dto.StockTransDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/import",produces = "application/json",method = RequestMethod.POST)
    public ResponseObject importStock(@RequestBody StockTransDTO stockTransDTO){
        log.info("-------------------------------");
        ResponseObject importResult = stockManagementBusiness.importStock(stockTransDTO.getMjrStockTransDTO(),stockTransDTO.getLstMjrStockTransDetail());
        log.info("Import result: "+ importResult);
        return importResult;
    }
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/export",produces = "application/json",method = RequestMethod.POST)
    public ResponseObject exportStock(@RequestBody StockTransDTO stockTransDTO){
        log.info("-------------------------------");
        ResponseObject importResult = stockManagementBusiness.exportStock(stockTransDTO.getMjrStockTransDTO(),stockTransDTO.getLstMjrStockTransDetail());
        log.info("Export result: "+ importResult);
        return importResult;
    }
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/cancelTransaction",produces = "application/json",method = RequestMethod.POST)
    public ResponseObject cancelTransaction(@RequestParam("transId") String transId){
        log.info("-------------------------------");
        ResponseObject importResult = stockManagementBusiness.cancelTransaction(transId);
        log.info("Cancel result: "+ importResult);
        return importResult;
    }
    //
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/getListSerialInStock",produces = "application/json",method = RequestMethod.GET)
    public List<String> getListSerialInStock(@RequestParam("custId") String custId, @RequestParam("stockId") String stockId,
                                             @RequestParam("goodsId") String goodsId, @RequestParam("goodsState") String goodsState
                                             ){
        log.info("-------------------------------");
        List<String> lstSerial = stockManagementBusiness.getListSerialInStock(custId,stockId,goodsId,goodsState);
        return lstSerial;
    }

}
