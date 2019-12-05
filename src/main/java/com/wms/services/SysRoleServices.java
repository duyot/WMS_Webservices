package com.wms.services;

import com.wms.base.BaseServices;
import com.wms.business.interfaces.SysRoleBusinessInterface;
import com.wms.dto.SysRoleDTO;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services/sysRoleServices")
public class SysRoleServices extends BaseServices<SysRoleDTO> {
    @Autowired
    SysRoleBusinessInterface sysRoleBusinessImpl;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = sysRoleBusinessImpl;
    }
}
