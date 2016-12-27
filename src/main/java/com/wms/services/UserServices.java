package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
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

import javax.annotation.PostConstruct;
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
public class UserServices extends BaseServices<UserDTO>{

    Logger log = LoggerFactory.getLogger(UserServices.class);

    @Autowired
    BaseBusinessInterface userBusiness;
    @Autowired
    UserBusinessInterface userBusinessAdvanced;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = userBusiness;
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

