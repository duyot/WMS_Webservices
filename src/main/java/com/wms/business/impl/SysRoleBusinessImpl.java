package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.business.interfaces.SysRoleBusinessInterface;
import com.wms.dto.SysRoleDTO;
import com.wms.persistents.dao.SysRoleDAO;
import com.wms.persistents.model.SysRole;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duyot on 11/2/2016.
 */
@Service("sysRoleBusinessImpl")
public class SysRoleBusinessImpl extends BaseBusinessImpl<SysRoleDTO, SysRoleDAO> implements SysRoleBusinessInterface {
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
