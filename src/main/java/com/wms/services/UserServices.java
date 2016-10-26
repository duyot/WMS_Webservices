package com.wms.services;

import com.google.common.collect.Lists;
import com.wms.base.BaseBusinessInterface;
import com.wms.business.interfaces.AdminUserBusinessInterface;
import com.wms.dto.AdminUserDTO;
import com.wms.dto.ResponseObject;
import com.wms.dto.User;
import com.wms.enums.Responses;
import com.wms.persistents.model.AdminUser;
import com.wms.utils.Constants;
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
    BaseBusinessInterface adminUserBusiness;

    @Autowired
    AdminUserBusinessInterface adminUserBusinessAdvanced;

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

    @RequestMapping(value = "/findUser/{userId}",method = RequestMethod.GET)
    public AdminUserDTO find(@PathVariable("userId") Long userId,HttpServletRequest request){
        return (AdminUserDTO) adminUserBusiness.findById(userId);
    }

    @CrossOrigin
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public List<AdminUserDTO> getAll(){
        log.info("findAll");
        List<AdminUserDTO> result = adminUserBusiness.getAll();
        return result;
    }

    @RequestMapping(value = "/deleteUser/{userId}")
    public ResponseObject delete(@PathVariable("userId") Long userId ){
        log.info("Deleting : "+ userId);
        String result = adminUserBusiness.deleteById(userId);
        log.info("Delete result : "+ result);
        return new ResponseObject(result,Responses.getCodeByName(result),userId+"");
    }

    @RequestMapping(value = "/saveUser",produces = "application/json")
    public ResponseObject  saveUser(@RequestBody AdminUserDTO adminUserDTO){
        log.info("Register user: "+ adminUserDTO.toString());
        adminUserDTO.setCreateDate(adminUserBusiness.getSysDate());
        adminUserDTO.setStatus(Constants.STATUS.ACTIVE);

        String id = adminUserBusiness.save(adminUserDTO);

        if(id == null){
            log.info("FAIL");
            return new ResponseObject(Responses.ERROR.getCode(),Responses.ERROR.getName(),id);
        }

        log.info("SUCCESS with id: "+ id);
        return new ResponseObject(Responses.SUCCESS.getCode(),Responses.SUCCESS.getName(),id);
    }

    @RequestMapping(value = "/login",produces = "application/json")
    public AdminUserDTO  login(@RequestBody AdminUserDTO adminUserDTO){
        log.info("User login: "+ adminUserDTO.toString());
        AdminUserDTO loggedinUser = adminUserBusinessAdvanced.login(adminUserDTO);
        log.info("Return info: "+ loggedinUser.toString());
        return loggedinUser;

    }


}

