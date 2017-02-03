package com.wms.persistents.dao;

import com.google.common.collect.Lists;
import com.wms.base.BaseDAOImpl;
import com.wms.dto.Condition;
import com.wms.persistents.model.CatUser;
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
public class CatUserDAO extends BaseDAOImpl<CatUser,Long> {
    @Autowired
    SessionFactory sessionFactory;

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public CatUser login(CatUser loginAdminCatUser){
        List<Condition> lstCondition = Lists.newArrayList();
        String email = loginAdminCatUser.getEmail();
        if(!DataUtil.isStringNullOrEmpty(email)){
            lstCondition.add(new Condition("email",Constants.SQL_OPERATOR.EQUAL,email));
        }else{
            lstCondition.add(new Condition("code",Constants.SQL_OPERATOR.EQUAL, loginAdminCatUser.getCode()));
        }
        lstCondition.add(new Condition("status",Constants.SQL_OPERATOR.EQUAL,Constants.STATUS.ACTIVE));

        List<CatUser> lstAdminCatUser = findByCondition(lstCondition);
        if(DataUtil.isListNullOrEmpty(lstAdminCatUser)){
            return new CatUser();
        }else{
            return lstAdminCatUser.get(0);
        }
    }
//
//
//    public static void main(String[] args) {
//    }
}
