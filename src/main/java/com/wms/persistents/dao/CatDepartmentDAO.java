package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.CatDepartment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by doanlv4 on 2/17/2017.
 */
@Repository
@Transactional
public class CatDepartmentDAO extends BaseDAOImpl<CatDepartment,Long> {
}
