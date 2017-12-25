package com.wms.services;

import com.wms.business.interfaces.StockFunctionInterface;
import com.wms.business.interfaces.StockManagementBusinessInterface;
import com.wms.dto.Condition;
import com.wms.dto.MjrStockTransDetailDTO;
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
    private Logger log = LoggerFactory.getLogger(StockManagementServices.class);

    @Autowired
    StockManagementBusinessInterface stockManagementBusiness;
    @Autowired
    StockFunctionInterface stockFunctionBusiness;

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
    public ResponseObject cancelTransaction(@RequestBody String transId){
        log.info("-------------------------------");
        ResponseObject importResult = stockFunctionBusiness.cancelTransaction(transId);
        log.info("Cancel "+transId+" result: "+ importResult);
        return importResult;
    }
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/getListSerialInStock",produces = "application/json",method = RequestMethod.GET)
    public List<String> getListSerialInStock(@RequestParam("custId") String custId, @RequestParam("stockId") String stockId,
                                             @RequestParam("goodsId") String goodsId, @RequestParam("goodsState") String goodsState){
        return stockFunctionBusiness.getListSerialInStock(custId,stockId,goodsId,goodsState);
    }
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/getCountGoodsDetail",produces = "application/json",method = RequestMethod.GET)
    public Long getCountGoodsDetail(String custId,  String stockId,String goodsId, String goodsState, String isSerial){
        return stockFunctionBusiness.getCountGoodsDetail(custId,stockId,goodsId,goodsState,isSerial);
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/getTransGoodsDetail",produces = "application/json",method = RequestMethod.GET)
    public List<MjrStockTransDetailDTO> getTransGoodsDetail(String custId, String stockId, String transId, String transType){
        return stockFunctionBusiness.getTransGoodsDetail(custId,stockId,transId,transType);
    }
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/getListTransGoodsDetail",produces = "application/json",method = RequestMethod.GET)
    public List<MjrStockTransDetailDTO> getListTransGoodsDetail(String lstStockTransId){
        return stockFunctionBusiness.getListTransGoodsDetail(lstStockTransId);
    }

}
