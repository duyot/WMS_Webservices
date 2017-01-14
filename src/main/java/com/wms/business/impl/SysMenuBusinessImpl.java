package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.SysMenuDTO;
import com.wms.persistents.dao.SysMenuDAO;
import com.wms.persistents.model.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 11/2/2016.
 */
@Service("sysMenuBusiness")
public class SysMenuBusinessImpl extends BaseBusinessImpl<SysMenuDTO, SysMenuDAO> {
    @Autowired
    SysMenuDAO sysMenuDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = sysMenuDAO;
        this.entityClass = SysMenuDTO.class;
        this.sysMenuDAO.setModelClass(SysMenu.class);
        this.tDTO = new SysMenuDTO();
    }
}

