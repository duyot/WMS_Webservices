package com.wms.business.impl;

/**
 * Created by doanlv4 on 2/17/2017.
 */

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.CatDepartmentDTO;
import com.wms.persistents.dao.CatDepartmentDAO;
import com.wms.persistents.model.CatDepartment;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("catDepartmentBusiness")
public class CatDepartmentBusinessImpl extends BaseBusinessImpl<CatDepartmentDTO, CatDepartmentDAO> {
    @Autowired
    CatDepartmentDAO catDepartmentDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = catDepartmentDAO;
        this.entityClass = CatDepartmentDTO.class;
        this.catDepartmentDAO.setModelClass(CatDepartment.class);
        this.tDTO = new CatDepartmentDTO();
    }
}
