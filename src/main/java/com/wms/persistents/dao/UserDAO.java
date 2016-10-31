package com.wms.persistents.dao;

import com.google.common.collect.Lists;
import com.wms.base.BaseDAOImpl;
import com.wms.dto.Condition;
import com.wms.persistents.model.User;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by duyot on 8/24/2016.
 */
@Repository
@Transactional
public class UserDAO extends BaseDAOImpl<User,Long> {
    @Autowired
    SessionFactory sessionFactory;

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public User login(User loginAdminUser){
        List<Condition> lstCondition = Lists.newArrayList();
        String email = loginAdminUser.getEmail();
        if(!DataUtil.isStringNullOrEmpty(email)){
            lstCondition.add(new Condition("email",Constants.SQL_OPERATOR.EQUAL,email));
        }else{
            lstCondition.add(new Condition("username",Constants.SQL_OPERATOR.EQUAL,loginAdminUser.getUsername()));
        }
        lstCondition.add(new Condition("password",Constants.SQL_OPERATOR.EQUAL,loginAdminUser.getPassword()));
        lstCondition.add(new Condition("status",Constants.SQL_OPERATOR.EQUAL,Constants.STATUS.ACTIVE));

        List<User> lstAdminUser = findByCondition(lstCondition);
        if(DataUtil.isListNullOrEmpty(lstAdminUser)){
            return new User();
        }else{
            return lstAdminUser.get(0);
        }
    }


    public static void main(String[] args) {
    }
}
