package com.wms.base;

import com.wms.dto.Condition;
import com.wms.dto.ErrorLogDTO;
import com.wms.dto.ResponseObject;
import com.wms.enums.Responses;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by duyot on 12/7/2016.
 */
@Service
public class BaseServices<T extends BaseDTO> {
    public Logger log = LoggerFactory.getLogger(BaseServices.class);

    public BaseBusinessInterface baseBusiness;

    @Autowired
    public BaseBusinessInterface errorLogBusiness;

    //SYSDATE-----------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/getSysDate",produces = "application/json",method = RequestMethod.GET)
    public String getSysDate(){
        return baseBusiness.getSysDate();
    }
    //SAVE-----------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/getSysDateWithPattern",produces = "application/json",method = RequestMethod.POST)
    public String getSysDateWithPattern(@RequestBody String pattern){
        return baseBusiness.getSysDate(pattern);
    }

    @RequestMapping(value = "/add",produces = "application/json",method = RequestMethod.POST)
    public ResponseObject add(@RequestBody T dto){
        String sysDate = errorLogBusiness.getSysDate();
        log.info("-------------------------------");
        log.info("Add: "+ dto.toString());
        try {
            String id = baseBusiness.save(dto);
            if(id == null || Responses.ERROR.getName().equalsIgnoreCase(id)){
                log.info("FAIL");
                return new ResponseObject(Responses.ERROR.getName(),Responses.ERROR.getName(),"");
            }
            log.info("SUCCESS id: "+ id);
            return new ResponseObject(Responses.SUCCESS.getName(),null,id);

        }catch (DataIntegrityViolationException e) {
            log.info(e.toString());
            errorLogBusiness.save(new ErrorLogDTO(null,"add","BaseServices",dto.toString(),sysDate, e.getMessage()));
            return new ResponseObject(Responses.ERROR.getName(),Responses.ERROR_CONSTRAINT.getName(),((ConstraintViolationException) e.getCause()).getConstraintName());
        }
        catch (Exception e) {
            log.info("ERROR: "+ e.toString());
            errorLogBusiness.save(new ErrorLogDTO(null,"add","BaseServices",dto.toString(),sysDate,e.toString()));
            return new ResponseObject(Responses.ERROR.getName(),Responses.ERROR.getName(),"");
        }
    }

    @RequestMapping(value = "/addList",produces = "application/json",method = RequestMethod.POST)
    public ResponseObject add(@RequestBody List<T> dto){
        String sysDate = errorLogBusiness.getSysDate();
        log.info("-------------------------------");
        log.info("Add: "+ dto.toString());

            String result = baseBusiness.save(dto);

            return new ResponseObject(result,result,null);
    }
    //UPDATE-----------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/update",produces = "application/json",method = RequestMethod.POST)
    public ResponseObject update(@RequestBody T dto){
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

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ResponseObject delete(@PathVariable("id") long id ){
        log.info("Id : "+ id);
        String result = baseBusiness.deleteById(id);
        log.info(result);
        if(!result.equalsIgnoreCase(Responses.SUCCESS.getName())){
            log.info("Fail");
            return new ResponseObject(Responses.ERROR.getName(),Responses.ERROR.getName(), "");
        }
        return new ResponseObject(Responses.SUCCESS.getName(),null,id+"");
    }

    @RequestMapping(value = "/find/{id}",method = RequestMethod.GET)
    public T find(@PathVariable("id") Long id){
        return (T) baseBusiness.findById(id);
    }

    @RequestMapping(value = "/findByCondition",produces = "application/json",method = RequestMethod.POST)
    public List<T> findByCondition(@RequestBody List<Condition> lstCondition){
        return baseBusiness.findByCondition(lstCondition);
    }
    @RequestMapping(value = "/deleteByCondition",produces = "application/json",method = RequestMethod.POST)
    public String deleteByCondition(@RequestBody List<Condition> lstCondition){
        return baseBusiness.deleteByCondition(lstCondition);
    }

    @RequestMapping(value = "/countByCondition",produces = "application/json",method = RequestMethod.POST)
    public Long countByCondition(@RequestBody List<Condition> lstCondition){
        return baseBusiness.countByCondition(lstCondition);
    }

}
