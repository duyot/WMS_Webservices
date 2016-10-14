package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
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
public class AdminUserBusinessImpl extends BaseBusinessImpl<AdminUserDTO,AdminUserDAO> {
    @Autowired
    AdminUserDAO adminUserDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = adminUserDAO;
        this.entityClass = AdminUserDTO.class;
        this.adminUserDAO.setModelClass(AdminUser.class);
        this.tDTO = new AdminUserDTO();
    }

//    @Override
//    public List getAlls() {
//        List<AdminUser> lst = Lists.newArrayList();
//        lst.add(adminUserDAO.findById(11L,AdminUser.class));
//        return lst;
//    }
}
