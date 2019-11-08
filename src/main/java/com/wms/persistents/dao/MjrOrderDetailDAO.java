package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.MjrOrder;
import com.wms.persistents.model.MjrOrderDetail;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by truongbx
 */
@Repository
@Transactional
public class MjrOrderDetailDAO extends BaseDAOImpl<MjrOrderDetail,Long> {
}
