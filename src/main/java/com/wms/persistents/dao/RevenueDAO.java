package com.wms.persistents.dao;

import com.google.common.collect.Lists;
import com.wms.base.BaseDAOImpl;
import com.wms.dto.CatReasonDTO;
import java.util.ArrayList;
import java.util.List;

import com.wms.persistents.model.Revenue;
import org.hibernate.Query;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by doanlv4 on 2/17/2017.
 */
@Repository
@Transactional
public class RevenueDAO extends BaseDAOImpl<Revenue, Long> {

}
