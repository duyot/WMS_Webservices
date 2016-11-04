package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.ActionDTO;
import com.wms.dto.RoleActionDTO;
import com.wms.persistents.dao.ActionDAO;
import com.wms.persistents.dao.RoleActionDAO;
import com.wms.persistents.model.Action;
import com.wms.persistents.model.RoleAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 11/3/2016.
 */
@Service("roleActionBusiness")
public class RoleActionbussinessImpl extends BaseBusinessImpl<RoleActionDTO, RoleActionDAO> {
    @Autowired
    RoleActionDAO roleActionDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = roleActionDAO;
        this.entityClass = RoleActionDTO.class;
        this.roleActionDAO.setModelClass(RoleAction.class);
        this.tDTO = new RoleActionDTO();
    }
}
