package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.business.interfaces.UserBusinessInterface;
import com.wms.dto.UserDTO;
import com.wms.dto.Condition;
import com.wms.dto.ResponseObject;
import com.wms.enums.Responses;
import com.wms.enums.Result;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by duyot on 9/30/2016.
 */
@RestController
@RequestMapping("/userservices")
public class UserServices {

    Logger log = LoggerFactory.getLogger(UserServices.class);

    @Autowired
    BaseBusinessInterface userBusiness;
    @Autowired
    BaseBusinessInterface roleBusiness;
    @Autowired
    UserBusinessInterface userBusinessAdvanced;


    @RequestMapping(value = "/add",produces = "application/json",method = RequestMethod.POST)
    public ResponseObject  add(@RequestBody UserDTO userDTO){
        log.info("-------------------------------");
        userDTO.setCreateDate(userBusiness.getSysDate());
        try {
            String id = userBusiness.save(userDTO);
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
    public ResponseObject  update(@RequestBody UserDTO userDTO){
        log.info("Update user: "+ userDTO.toString());
        String result = userBusiness.update(userDTO);

        if(!result.equalsIgnoreCase(Result.SUCCESS.getName())){
            log.info("FAIL");
            return new ResponseObject(Responses.ERROR.getCode(),Responses.ERROR.getName(), userDTO.getUserId());
        }

        log.info("SUCCESS");
        return new ResponseObject(Responses.SUCCESS.getCode(),Responses.SUCCESS.getName(), userDTO.getUserId());
    }

    @RequestMapping(value = "/delete")
    public ResponseObject delete(@RequestBody Long userId ){
        log.info("Deleting : "+ userId);
        String result = userBusiness.deleteById(userId);
        log.info("Delete result : "+ result);
        return new ResponseObject(Responses.getCodeByName(result),result,userId+"");
    }

    @RequestMapping(value = "/find/{userId}",method = RequestMethod.GET)
    public UserDTO find(@PathVariable("userId") Long userId, HttpServletRequest request){
        return (UserDTO) userBusiness.findById(userId);
    }

    @RequestMapping(value = "/findByCondition",produces = "application/json")
    public List<UserDTO> login(@RequestBody List<Condition> lstCondition){
        log.info("Condition info: "+ lstCondition.toString());
        return userBusiness.findByCondition(lstCondition);
    }

    @CrossOrigin
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public List<UserDTO> getAll(){
        log.info("getAll");
        return userBusiness.getAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/getAlls",method = RequestMethod.GET)
        public List<UserDTO> getAlls(@RequestParam("userId") String userId){
            try {
                Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("findAll"+ userId);
        return userBusiness.getAll();
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/login",produces = "application/json")
    public UserDTO login(@RequestBody UserDTO userDTO){
        log.info("UserDTO login: "+ userDTO.toString());
        UserDTO loggedinUser = userBusinessAdvanced.login(userDTO);
        log.info("Return info: "+ loggedinUser.toString());
        return loggedinUser;
    }

    @RequestMapping(value = "/getFile/{fileName:.+}")
    public void find(@PathVariable("fileName") String fileName,HttpServletResponse response){
        log.info("fileName: "+ fileName);
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
            IOUtils.copy(is,response.getOutputStream());
            response.flushBuffer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

