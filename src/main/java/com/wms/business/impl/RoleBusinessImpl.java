package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.RoleDTO;
import com.wms.dto.UserDTO;
import com.wms.persistents.dao.RoleDAO;
import com.wms.persistents.dao.UserDAO;
import com.wms.persistents.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 11/2/2016.
 */
@Service("roleBusiness")
public class RoleBusinessImpl  extends BaseBusinessImpl<RoleDTO, RoleDAO> {
    @Autowired
    RoleDAO roleDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = roleDAO;
        this.entityClass = RoleDTO.class;
        this.roleDAO.setModelClass(Role.class);
        this.tDTO = new RoleDTO();
    }
}
