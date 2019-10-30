package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.MjrStockGoodsDTO;
import com.wms.dto.MjrStockGoodsSerialDTO;
import com.wms.dto.ResponseObject;
import com.wms.enums.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 3/24/2017.
 */
@RestController
@RequestMapping("/services/mjrStockGoodsSerialServices")
public class MjrStockGoodsSerialServices extends BaseServices<MjrStockGoodsSerialDTO> {
    @Autowired
    BaseBusinessInterface mjrStockGoodsSerialBusiness;

    @PostConstruct
    public void setupServices(){
        this.baseBusiness = mjrStockGoodsSerialBusiness;
    }

    @RequestMapping(value = "/updateByProperties",produces = "application/json",method = RequestMethod.POST)
    public ResponseObject updateByProperties(@RequestBody MjrStockGoodsSerialDTO mjrStockGoodsSerialDTO){
        String [] updateProperties = {"produceDate", "expireDate", "description", "cellCode", "changeDate"};
        //
        String result = mjrStockGoodsSerialBusiness.updateByProperties(mjrStockGoodsSerialDTO, Long.parseLong(mjrStockGoodsSerialDTO.getId()), updateProperties);
        if(!result.equalsIgnoreCase(Responses.SUCCESS.getName())){
            log.info("Fail");
            return new ResponseObject(Responses.ERROR.getName(),Responses.ERROR.getName(), "");
        }
        log.info("Success");
        return new ResponseObject(Responses.SUCCESS.getName(),null, "");
    }
}
