package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.SysRoleMenu;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 11/3/2016.
 */
@Repository
@Transactional
public class SysRoleMenuDAO extends BaseDAOImpl<SysRoleMenu, Long> {
}
