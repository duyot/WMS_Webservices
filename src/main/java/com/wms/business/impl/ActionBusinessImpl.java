package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.ActionDTO;
import com.wms.dto.RoleDTO;
import com.wms.persistents.dao.ActionDAO;
import com.wms.persistents.dao.RoleDAO;
import com.wms.persistents.model.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 11/2/2016.
 */
@Service("actionBusiness")
public class ActionBusinessImpl extends BaseBusinessImpl<ActionDTO, ActionDAO> {
    @Autowired
    ActionDAO actionDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = actionDAO;
        this.entityClass = ActionDTO.class;
        this.actionDAO.setModelClass(Action.class);
        this.tDTO = new ActionDTO();
    }
}

