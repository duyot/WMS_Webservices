package com.wms.persistents.dao;

import com.wms.base.BaseDAOImpl;
import com.wms.persistents.model.MjrOrder;
import com.wms.persistents.model.SysRole;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by truongbx
 */
@Repository
@Transactional
public class MjrOrderDAO extends BaseDAOImpl<MjrOrder,Long> {

	//
	public String getTotalOrder(String stockId,Integer type) {
		String sql = " select count(*) from MJR_ORDER where  type = :type and stock_id = :stockId ";
		Query ps = getSession().createSQLQuery(sql);
		ps.setInteger("type", type);
		ps.setInteger("stockId", Integer.parseInt(stockId));
		BigDecimal countNumber = (BigDecimal) ps.uniqueResult();
		return String.format("%08d", countNumber.intValue());
	}

}
