package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.business.interfaces.MjrStockGoodsTotalBusinessInterface;
import com.wms.dto.CatGoodsDTO;
import com.wms.dto.ErrorLogDTO;
import com.wms.dto.ResponseObject;
import com.wms.enums.Responses;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;

/**
 * Created by duyot on 12/9/2016.
 */
@RestController
@RequestMapping("/services/catGoodsServices")
public class CatGoodsServices extends BaseServices<CatGoodsDTO> {
    @Autowired
    BaseBusinessInterface catGoodsBusiness;
    @Autowired
    MjrStockGoodsTotalBusinessInterface totalService;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = catGoodsBusiness;
    }

    @RequestMapping(value = "/update",produces = "application/json",method = RequestMethod.POST)
    public ResponseObject update(@RequestBody CatGoodsDTO dto){
        String sysDate = errorLogBusiness.getSysDate();
        log.info("-------------------------------");
        log.info("Update: "+ dto.toString());
        try {
            String result = baseBusiness.update(dto);
            if(!result.equalsIgnoreCase(Responses.SUCCESS.getName())){
                log.info("Fail");
                return new ResponseObject(Responses.ERROR.getName(),Responses.ERROR.getName(), "");
            }
            log.info("Success");
            //update goods name in total
            int updatedTotalNum = totalService.updateGoods(dto);
            log.info("Updated "+ updatedTotalNum + " goods name "+ dto.getName()+ " in total.");
            //
            return new ResponseObject(Responses.SUCCESS.getName(),null, "");
        }
        catch (DataIntegrityViolationException e) {
            log.info(e.toString());
            errorLogBusiness.save(new ErrorLogDTO(null,"update","BaseServices",dto.toString(),sysDate, e.getMessage()));
            return new ResponseObject(Responses.ERROR.getName(),Responses.ERROR_CONSTRAINT.getName(),((ConstraintViolationException) e.getCause()).getConstraintName());
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("ERROR: "+ e.toString());
            errorLogBusiness.save(new ErrorLogDTO(null,"update","BaseServices",dto.toString(),sysDate,e.toString()));
            return new ResponseObject(Responses.ERROR.getName(),Responses.ERROR.getName(),"");
        }

    }
}
