package com.wms.business.impl;

/**
 * Created by doanlv4 on 25/03/2017.
 */

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.AppParamsDTO;
import com.wms.persistents.dao.AppParamsDAO;
import com.wms.persistents.model.AppParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
@Service("appParamsBusiness")
public class AppParamsBusinessImpl extends BaseBusinessImpl<AppParamsDTO, AppParamsDAO> {
    @Autowired
    AppParamsDAO appParamsDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = appParamsDAO;
        this.entityClass = AppParamsDTO.class;
        this.appParamsDAO.setModelClass(AppParams.class);
        this.tDTO = new AppParamsDTO();
    }
}
