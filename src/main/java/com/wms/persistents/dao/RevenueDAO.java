package com.wms.persistents.dao;

import com.google.common.collect.Lists;
import com.wms.base.BaseDAOImpl;
import com.wms.dto.CatReasonDTO;
import java.util.ArrayList;
import java.util.List;

import com.wms.dto.RevenueDTO;
import com.wms.persistents.model.Revenue;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import org.hibernate.Query;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

/**
 * Created by doanlv4 on 2/17/2017.
 */
@Repository
@Transactional
public class RevenueDAO extends BaseDAOImpl<Revenue, Long> {
    private Logger log = LoggerFactory.getLogger(RevenueDAO.class);

    public List<RevenueDTO> getSumRevenue(String custId, String partnerId, String startDate, String endDate) {

        StringBuilder str = new StringBuilder();
        StringBuilder initJoinQuery = new StringBuilder();
        Session session = getSession();

        List<RevenueDTO> lstResult = new ArrayList<>();
        StringBuilder initSelectQuery = new StringBuilder();
        StringBuilder initWhereQuery = new StringBuilder(" WHERE 1=1 ");

        initSelectQuery.append("  select a.partner_id as partnerId, sum(nvl(a.amount,0) + a.charge + nvl(decode(a.vat, -1,0, a.vat),0)*nvl(a.amount,0)/100) as amount, sum(a.payment_amount) as paymentAmount\n" +
                " from REVENUE_HISTORY a\n");

        if (!DataUtil.isStringNullOrEmpty(custId) && !custId.equals(Constants.STATS_ALL)) {
            initWhereQuery.append(" and a.cust_id = ").append(custId);
        }

        if (!DataUtil.isStringNullOrEmpty(partnerId) && !partnerId.equals(Constants.STATS_ALL)) {
            initWhereQuery.append(" and a.partner_id = ").append(partnerId);
        }
        if (!DataUtil.isStringNullOrEmpty(startDate)) {
            initWhereQuery.append(" and a.created_date >= to_date('").append(startDate).append("','dd/mm/yyyy')");
        }
        if (!DataUtil.isStringNullOrEmpty(endDate)) {
            initWhereQuery.append(" and a.created_date <= to_date('").append(endDate).append("','dd/mm/yyyy')");
        }

        initSelectQuery.append(initWhereQuery).append(" \n group by a.PARTNER_ID");


        Query ps = getSession().createSQLQuery(initSelectQuery.toString())
                .addScalar("partnerId", StringType.INSTANCE)
                .addScalar("amount", StringType.INSTANCE)
                .addScalar("paymentAmount", StringType.INSTANCE)
                ;

        ps.setResultTransformer(Transformers.aliasToBean(RevenueDTO.class));
        try {
            lstResult = ps.list();
            return lstResult;
        } catch (Exception e) {
            log.info(e.toString());
            return lstResult;
        }
    }

}
