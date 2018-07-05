package com.wms.services;

import com.google.common.collect.Lists;
import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseDTO;
import com.wms.base.BaseServices;
import com.wms.business.interfaces.CatUserBusinessInterface;
import com.wms.dto.*;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duyot on 9/30/2016.
 */
@RestController
@RequestMapping("/services/catUserServices")
public class CatUserServices extends BaseServices<CatUserDTO>{

    Logger log = LoggerFactory.getLogger(CatUserServices.class);

    @Autowired
    BaseBusinessInterface catUserBusiness;
    @Autowired
    BaseBusinessInterface catCustomerBusiness;

    @Autowired
    CatUserBusinessInterface userBusinessAdvanced;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = catUserBusiness;
    }


    @RequestMapping(value = "/updateUser",produces = "application/json",method = RequestMethod.POST)
    public ResponseObject updateUser(@RequestBody CatUserDTO updateUser){
        ResponseObject responseObject = new ResponseObject();
        log.info("Updating user: "+ updateUser.toString());
        //
        if (updateUser == null) {
            return  responseObject;
        }
        //check if username is available
        if (!DataUtil.isStringNullOrEmpty(updateUser.getCode())) {
            List<Condition> lstCon = Lists.newArrayList();
            lstCon.add(new Condition("code",Constants.SQL_OPERATOR.EQUAL,updateUser.getCode()));
            lstCon.add(new Condition("id",Constants.SQL_PRO_TYPE.LONG,Constants.SQL_OPERATOR.NOT_EQUAL,updateUser.getId()));
            //
            List sameUserWithCode = catUserBusiness.findByCondition(lstCon);
            if (!DataUtil.isListNullOrEmpty(sameUserWithCode)) {
                responseObject.setStatusCode("USER_AVAILABLE");
                return responseObject;
            }
        }
        //
        long userId = Long.parseLong(updateUser.getId());
        CatUserDTO originalUser = (CatUserDTO) catUserBusiness.findById(userId);
        //
        originalUser = setUpdatedUserInfor(updateUser,originalUser);
        //
        String updateResult = catUserBusiness.update(originalUser);
        //
        responseObject.setStatusCode(updateResult);
        responseObject.setStatusName(updateResult);
        log.info("Finished update with response: "+responseObject.toString());
        return responseObject;
    }

    private CatUserDTO setUpdatedUserInfor(CatUserDTO updatedUser, CatUserDTO originalUser){
        if (!DataUtil.isStringNullOrEmpty(updatedUser.getName())) {
            originalUser.setName(updatedUser.getName());
        }
        if (!DataUtil.isStringNullOrEmpty(updatedUser.getCode())) {
            originalUser.setCode(updatedUser.getCode());
        }
        if (!DataUtil.isStringNullOrEmpty(updatedUser.getPassword())) {
            originalUser.setPassword(updatedUser.getPassword());
        }
        if (!DataUtil.isStringNullOrEmpty(updatedUser.getEmail())) {
            originalUser.setEmail(updatedUser.getEmail());
        }
        if (!DataUtil.isStringNullOrEmpty(updatedUser.getTelNumber())) {
            originalUser.setTelNumber(updatedUser.getTelNumber());
        }
        //
        return originalUser;
    }


    @RequestMapping(value = "/getUserByCustomerId/{customerId}",produces = "application/json",method = RequestMethod.GET)
    public List<CatUserDTO> getUserByCustomer(@PathVariable("customerId") String customerId){
        if(!DataUtil.isInteger(customerId)){
            log.info("Invalid customerId info");
            return new ArrayList<>();
        }
        //
        List<Condition> lstConditions = Lists.newArrayList();
        lstConditions.add(new Condition("customerId", Constants.SQL_PRO_TYPE.LONG,Constants.SQL_OPERATOR.EQUAL,customerId));
        lstConditions.add(new Condition("status",Constants.SQL_PRO_TYPE.BYTE,Constants.SQL_OPERATOR.EQUAL,Constants.STATUS.ACTIVE));

        //
        List<CatUserDTO> lstUser = catUserBusiness.findByCondition(lstConditions);
        //
        return lstUser;
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

    @RequestMapping(value = "/add",produces = "application/json",method = RequestMethod.POST)
    public ResponseObject add(@RequestBody CatUserDTO registerUser){
        ResponseObject responseObject = new ResponseObject();
        log.info("register user: "+ registerUser.toString());
        //
        if (registerUser == null) {
            return  responseObject;
        }
        //check if username is available
        if (!DataUtil.isStringNullOrEmpty(registerUser.getTelNumber())) {
            List<Condition> lstCon = Lists.newArrayList();
            lstCon.add(new Condition("telNumber",Constants.SQL_OPERATOR.EQUAL,registerUser.getTelNumber()));
            List sameUserWithCode = catUserBusiness.findByCondition(lstCon);
            if (!DataUtil.isListNullOrEmpty(sameUserWithCode)) {
                responseObject.setStatusCode("USER_AVAILABLE");
                return responseObject;
            }
        }

        String registerResult = catUserBusiness.save(registerUser);
        //
        responseObject.setStatusCode(registerResult);
        responseObject.setStatusName(registerResult);
        log.info("Finished register with response: "+responseObject.toString());
        return responseObject;
    }
}

