package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.SysRoleMenuDTO;
import com.wms.persistents.dao.SysRoleMenuDAO;
import com.wms.persistents.model.SysRoleMenu;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duyot on 11/3/2016.
 */
@Service("sysRoleMenuBusiness")
public class SysRoleMenuBusinessImpl extends BaseBusinessImpl<SysRoleMenuDTO, SysRoleMenuDAO> {
    @Autowired
    SysRoleMenuDAO sysRoleMenuDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = sysRoleMenuDAO;
        this.entityClass = SysRoleMenuDTO.class;
        this.sysRoleMenuDAO.setModelClass(SysRoleMenu.class);
        this.tDTO = new SysRoleMenuDTO();
    }
}
