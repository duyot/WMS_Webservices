package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.business.interfaces.CatUserBusinessInterface;
import com.wms.dto.CatUserDTO;
import com.wms.persistents.dao.CatUserDAO;
import com.wms.persistents.model.CatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 8/24/2016.
 */
@Service("catUserBusiness")
public class CatUserBusinessImpl extends BaseBusinessImpl<CatUserDTO, CatUserDAO> implements CatUserBusinessInterface {
    @Autowired
    CatUserDAO catUserDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = catUserDAO;
        this.entityClass = CatUserDTO.class;
        this.catUserDAO.setModelClass(CatUser.class);
        this.tDTO = new CatUserDTO();
    }


    @Override
    public CatUserDTO login(CatUserDTO loginUser) {
        CatUser adminCatUser = catUserDAO.login(loginUser.toModel());
        return  adminCatUser.toDTO();
    }
}
