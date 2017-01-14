package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.business.interfaces.CatUserBusinessInterface;
import com.wms.dto.CatUserDTO;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by duyot on 9/30/2016.
 */
@RestController
@RequestMapping("/catUserServices")
public class UserServices extends BaseServices<CatUserDTO>{

    Logger log = LoggerFactory.getLogger(UserServices.class);

    @Autowired
    BaseBusinessInterface catUserBusiness;
    @Autowired
    CatUserBusinessInterface userBusinessAdvanced;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = catUserBusiness;
    }
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/login",produces = "application/json")
    public CatUserDTO login(@RequestBody CatUserDTO catUserDTO){
        log.info("CatUserDTO login: "+ catUserDTO.toString());
        CatUserDTO loggedinUser = userBusinessAdvanced.login(catUserDTO);
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

