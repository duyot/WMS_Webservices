package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.SysRoleDTO;
import com.wms.persistents.dao.SysRoleDAO;
import com.wms.persistents.model.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 11/2/2016.
 */
@Service("sysRoleBusiness")
public class SysRoleBusinessImpl extends BaseBusinessImpl<SysRoleDTO, SysRoleDAO> {
    @Autowired
    SysRoleDAO sysRoleDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = sysRoleDAO;
        this.entityClass = SysRoleDTO.class;
        this.sysRoleDAO.setModelClass(SysRole.class);
        this.tDTO = new SysRoleDTO();
    }
}
