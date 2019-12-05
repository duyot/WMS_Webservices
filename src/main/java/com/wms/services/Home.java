package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseDTO;
import com.wms.business.interfaces.CatUserBusinessInterface;
import com.wms.dto.CatCustomerDTO;
import com.wms.dto.CatUserDTO;
import com.wms.dto.ErrorLogDTO;
import com.wms.dto.ResponseObject;
import com.wms.enums.Responses;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @Autowired
    BaseBusinessInterface catUserBusiness;
    @Autowired
    BaseBusinessInterface catCustomerBusiness;

    @Autowired
    public BaseBusinessInterface errorLogBusiness;

    @RequestMapping
    public @ResponseBody
    String home() {
        return "Services is running!";
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/login", produces = "application/json")
    public CatUserDTO login(@RequestBody CatUserDTO catUserDTO) {
        CatUserDTO loggedUser = userBusinessAdvanced.login(catUserDTO);
        log.info("Return info: " + loggedUser.getCode());
        return loggedUser;//
    }

    @RequestMapping(value = "/register/addUser", produces = "application/json", method = RequestMethod.POST)
    public ResponseObject addUser(@RequestBody CatUserDTO catUserDTO) {
        return add(catUserDTO, catUserBusiness);
    }

    @RequestMapping(value = "/register/addCustomer", produces = "application/json", method = RequestMethod.POST)
    public ResponseObject addCustomer(@RequestBody CatCustomerDTO catCustomerDTO) {
        return add(catCustomerDTO, catCustomerBusiness);
    }

    public ResponseObject add(BaseDTO user, BaseBusinessInterface baseBusiness) {
        String sysDate = errorLogBusiness.getSysDate();
        try {
            String id = baseBusiness.save(user);
            if (id == null || Responses.ERROR.getName().equalsIgnoreCase(id)) {
                log.info("Guess add fail");
                return new ResponseObject(Responses.ERROR.getName(), Responses.ERROR.getName(), "");
            }
            log.info("Guess add success id: " + id);
            return new ResponseObject(Responses.SUCCESS.getName(), null, id);

        } catch (DataIntegrityViolationException e) {
            log.info(e.toString());
            errorLogBusiness.save(new ErrorLogDTO(null, "add", "BaseServices", user.toString(), sysDate, "Guess add :" + e.getMessage()));
            return new ResponseObject(Responses.ERROR.getName(), Responses.ERROR_CONSTRAINT.getName(), ((ConstraintViolationException) e.getCause()).getConstraintName());
        } catch (Exception e) {
            log.info("ERROR: " + e.toString());
            errorLogBusiness.save(new ErrorLogDTO(null, "add", "BaseServices", user.toString(), sysDate, "Guess add :" + e.toString()));
            return new ResponseObject(Responses.ERROR.getName(), Responses.ERROR.getName(), "");
        }
    }
}
