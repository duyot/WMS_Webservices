package com.wms.services;

import com.wms.business.interfaces.CatUserBusinessInterface;
import com.wms.dto.CatUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by duyot on 1/17/2017.
 */
@RestController
@RequestMapping("/")
public class Home {
    Logger log = LoggerFactory.getLogger(Home.class);

    @Autowired
    CatUserBusinessInterface userBusinessAdvanced;


    @RequestMapping
    public @ResponseBody String home(){
        return "Services is running!";
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/login",produces = "application/json")
    public CatUserDTO login(@RequestBody CatUserDTO catUserDTO){
        log.info("CatUserDTO login: "+ catUserDTO.toString());
        CatUserDTO loggedUser = userBusinessAdvanced.login(catUserDTO);
        log.info("Return info: "+ loggedUser.getCode());
        return loggedUser;
    }
}
