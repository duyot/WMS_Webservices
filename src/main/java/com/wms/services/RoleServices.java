package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.dto.RoleDTO;
import com.wms.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by duyot on 11/9/2016.
 */
@RestController
@RequestMapping("/roleservices")
public class RoleServices {
    Logger log = LoggerFactory.getLogger(RoleServices.class);
    @Autowired
    BaseBusinessInterface roleBusiness;

    @CrossOrigin
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public List<RoleDTO> getAll(){
        return roleBusiness.getAll();
    }

}
