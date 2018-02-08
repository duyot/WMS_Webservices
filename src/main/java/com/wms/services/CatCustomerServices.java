package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.CatCustomerDTO;
import com.wms.dto.ResponseObject;
import com.wms.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 12/6/2016.
 */
@RestController
@RequestMapping("/services/catCustomerServices")
public class CatCustomerServices extends BaseServices<CatCustomerDTO>{
    @Autowired
    BaseBusinessInterface catCustomerBusiness;

    @PostConstruct
    public void setupServices(){
        this.baseBusiness = catCustomerBusiness;
    }

    @RequestMapping(value = "/updateCustomer",produces = "application/json",method = RequestMethod.POST)
    public ResponseObject updateCustomer(@RequestBody CatCustomerDTO updateCustomer){
        ResponseObject responseObject = new ResponseObject();
        //
        if (updateCustomer == null) {
            return  responseObject;
        }
        log.info("Updating customer: "+ updateCustomer.toString());
        //
        long customerId = Long.parseLong(updateCustomer.getId());
        CatCustomerDTO originalCustomer = (CatCustomerDTO) catCustomerBusiness.findById(customerId);
        //
        originalCustomer = setUpdatedCustomerInfor(updateCustomer,originalCustomer);
        //
        String updateResult = catCustomerBusiness.update(originalCustomer);
        //
        responseObject.setStatusCode(updateResult);
        responseObject.setStatusName(updateResult);
        log.info("Finished update with response: "+responseObject.toString());
        return responseObject;
    }

    private CatCustomerDTO setUpdatedCustomerInfor(CatCustomerDTO updatedCustomer, CatCustomerDTO originalCustomer){
        if (!DataUtil.isStringNullOrEmpty(updatedCustomer.getName())) {
            originalCustomer.setName(updatedCustomer.getName());
        }
        if (!DataUtil.isStringNullOrEmpty(updatedCustomer.getEmail())) {
            originalCustomer.setEmail(updatedCustomer.getEmail());
        }
        if (!DataUtil.isStringNullOrEmpty(updatedCustomer.getTelNumber())) {
            originalCustomer.setTelNumber(updatedCustomer.getTelNumber());
        }
        if (!DataUtil.isStringNullOrEmpty(updatedCustomer.getAddress())) {
            originalCustomer.setAddress(updatedCustomer.getAddress());
        }
        if (!DataUtil.isStringNullOrEmpty(updatedCustomer.getMailReport())) {
            originalCustomer.setMailReport(updatedCustomer.getMailReport());
        }
        //
        return originalCustomer;
    }

}
