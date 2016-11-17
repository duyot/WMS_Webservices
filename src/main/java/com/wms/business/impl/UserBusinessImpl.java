package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.business.interfaces.UserBusinessInterface;
import com.wms.dto.UserDTO;
import com.wms.persistents.dao.UserDAO;
import com.wms.persistents.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 8/24/2016.
 */
@Service("userBusiness")
public class UserBusinessImpl extends BaseBusinessImpl<UserDTO, UserDAO> implements UserBusinessInterface {
    @Autowired
    UserDAO adminUserDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = adminUserDAO;
        this.entityClass = UserDTO.class;
        this.adminUserDAO.setModelClass(User.class);
        this.tDTO = new UserDTO();
    }


    @Override
    public UserDTO login(UserDTO loginUser) {
        User adminUser = adminUserDAO.login(loginUser.toModel());
        return  adminUser.toDTO();
    }
}
