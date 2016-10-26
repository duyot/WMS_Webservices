package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.business.interfaces.AdminUserBusinessInterface;
import com.wms.dto.AdminUserDTO;
import com.wms.persistents.dao.AdminUserDAO;
import com.wms.persistents.model.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 8/24/2016.
 */
@Service("adminUserBusiness")
public class AdminUserBusinessImpl extends BaseBusinessImpl<AdminUserDTO,AdminUserDAO> implements AdminUserBusinessInterface {
    @Autowired
    AdminUserDAO adminUserDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = adminUserDAO;
        this.entityClass = AdminUserDTO.class;
        this.adminUserDAO.setModelClass(AdminUser.class);
        this.tDTO = new AdminUserDTO();
    }


    @Override
    public AdminUserDTO login(AdminUserDTO loginUser) {
        AdminUser  adminUser = adminUserDAO.login(loginUser.toModel());
        return  adminUser.toDTO();
    }
}
