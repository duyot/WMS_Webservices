package com.wms.services;

import com.google.common.collect.Lists;
import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseDTO;
import com.wms.base.BaseServices;
import com.wms.business.interfaces.CatUserBusinessInterface;
import com.wms.dto.CatCustomerDTO;
import com.wms.dto.CatUserDTO;
import com.wms.dto.Condition;
import com.wms.dto.MapUserCustomerDTO;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    BaseBusinessInterface mapUserCustomerBusiness;

    @Autowired
    CatUserBusinessInterface userBusinessAdvanced;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = catUserBusiness;
    }

    @RequestMapping(value = "/getCustomer/{userId}",produces = "application/json",method = RequestMethod.GET)
    public List<CatCustomerDTO> getCustomer(@PathVariable("userId") String userId){
        if(!DataUtil.isInteger(userId)){
            log.info("Invalid userId info");
            return new ArrayList<>();
        }
        List<MapUserCustomerDTO> lstMap = getMapUserCustomer(userId);
        if(DataUtil.isListNullOrEmpty(lstMap)){
            log.info("User: "+ userId + "didn't map to any customer");
            return new ArrayList<>();
        }
        List<CatCustomerDTO> lstCatCustomers = Lists.newArrayList();
        for(MapUserCustomerDTO i: lstMap){
            BaseDTO temp = catCustomerBusiness.findById(Long.parseLong(i.getCustomerId()));
            if(temp != null){
                lstCatCustomers.add((CatCustomerDTO) temp);
            }
        }
        return lstCatCustomers;
    }

    private List<MapUserCustomerDTO> getMapUserCustomer(String userId){
        List<Condition> lstConditions = Lists.newArrayList();
        lstConditions.add(new Condition("userId", Constants.SQL_PRO_TYPE.LONG,Constants.SQL_OPERATOR.EQUAL,userId));
        return mapUserCustomerBusiness.findByCondition(lstConditions);
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

