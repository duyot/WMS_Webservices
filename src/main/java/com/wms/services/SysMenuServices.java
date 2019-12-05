package com.wms.services;

import com.wms.base.BaseServices;
import com.wms.business.interfaces.SysMenuBusinessInterface;
import com.wms.dto.SysMenuDTO;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by duyot on 12/7/2016.
 */
@RestController
@RequestMapping("/services/sysMenuServices")
public class SysMenuServices extends BaseServices<SysMenuDTO> {
    @Autowired
    SysMenuBusinessInterface sysMenuBusinessImpl;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = sysMenuBusinessImpl;
    }
}