package com.wms.base;

import com.wms.dto.Condition;
import com.wms.dto.ResponseObject;
import com.wms.enums.Responses;
import com.wms.enums.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by duyot on 12/7/2016.
 */
public class BaseServices<T extends BaseDTO> {
    Logger log = LoggerFactory.getLogger(BaseServices.class);
    public BaseBusinessInterface baseBusiness;

    @RequestMapping(value = "/add",produces = "application/json",method = RequestMethod.POST)
    public ResponseObject add(@RequestBody T dto){
        log.info("-------------------------------");
        log.info("Add: "+ dto.toString());
        try {
            String id = baseBusiness.save(dto);
            if(id == null){
                log.info("FAIL");
                return new ResponseObject(Responses.ERROR.getCode(),Responses.ERROR.getName(),"");
            }
            log.info("SUCCESS with id: "+ id);
            return new ResponseObject(Responses.SUCCESS.getCode(),Responses.SUCCESS.getName(),id);

        } catch (Exception e) {
            log.info("ERROR: "+ e.toString());
            return new ResponseObject(Responses.ERROR_CONSTRAINT.getCode(),Responses.ERROR_CONSTRAINT.getName(),"");
        }
    }

    @RequestMapping(value = "/update",produces = "application/json",method = RequestMethod.POST)
    public ResponseObject update(@RequestBody T dto){
        log.info("-------------------------------");
        log.info("Update: "+ dto.toString());
        try {
            String result = baseBusiness.update(dto);
            if(!result.equalsIgnoreCase(Result.SUCCESS.getName())){
                log.info("Update fail");
                return new ResponseObject(Responses.ERROR.getCode(),Responses.ERROR.getName(), "");
            }
            log.info("Update Success");
            return new ResponseObject(Responses.SUCCESS.getCode(),Responses.SUCCESS.getName(), "");
        } catch (Exception e) {
            log.error("ERROR: "+ e.toString());
            return new ResponseObject(Responses.ERROR_CONSTRAINT.getCode(),Responses.ERROR_CONSTRAINT.getName(),"");
        }

    }

    @RequestMapping(value = "/delete")
    public ResponseObject delete(@RequestBody Long id ){
        log.info("Deleting : "+ id);
        String result = baseBusiness.deleteById(id);
        log.info("Delete result : "+ result);
        return new ResponseObject(Responses.getCodeByName(result),result,id+"");
    }

    @RequestMapping(value = "/find/{id}",method = RequestMethod.GET)
    public T find(@PathVariable("id") Long id, HttpServletRequest request){
        return (T) baseBusiness.findById(id);
    }

    @RequestMapping(value = "/findByCondition",produces = "application/json")
    public List<T> findByCondition(@RequestBody List<Condition> lstCondition){
        return baseBusiness.findByCondition(lstCondition);
    }

    @CrossOrigin
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public List<T> getAll(){
        log.info("getAll");
        return baseBusiness.getAll();
    }
}
