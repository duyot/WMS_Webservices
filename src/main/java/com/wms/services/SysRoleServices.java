package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.dto.SysRoleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by duyot on 11/9/2016.
 */
@RestController
@RequestMapping("/sysRoleServices")
public class SysRoleServices {
    Logger log = LoggerFactory.getLogger(SysRoleServices.class);
    @Autowired
    BaseBusinessInterface sysRoleBusiness;

    @CrossOrigin
    @GetMapping(value = "/getAll")
    public List<SysRoleDTO> getAll(){
        return sysRoleBusiness.getAll();
    }

}
