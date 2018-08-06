package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.CatGoodsGroup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 12/7/2016.
 */
@Repository
@Transactional
public class CatGoodsGroupDAO  extends BaseDAOImpl<CatGoodsGroup,Long> {
}
