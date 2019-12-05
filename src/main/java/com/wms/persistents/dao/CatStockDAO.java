package com.wms.persistents.dao;

import com.google.common.collect.Lists;
import com.wms.base.BaseDAOImpl;
import com.wms.dto.CatStockDTO;
import com.wms.persistents.model.CatStock;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class CatStockDAO extends BaseDAOImpl<CatStock, Long> {
    private Logger log = LoggerFactory.getLogger(CatStockDAO.class);


    public List<CatStockDTO> getStockByUser(long userId) {
        try {
            //
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT b.id, b.cust_id, b.code, b.name, b.address, b.status FROM map_user_stock a, cat_stock b ");
            sql.append(" WHERE  a.user_id      = ? ");
            sql.append("    and a.stock_id     = b.id ");
            sql.append(" ORDER BY b.name ");
            //
            Query query = getSession().createSQLQuery(sql.toString())
                    .addScalar("id", StringType.INSTANCE)
                    .addScalar("cust_id", StringType.INSTANCE)
                    .addScalar("code", StringType.INSTANCE)
                    .addScalar("name", StringType.INSTANCE)
                    .addScalar("address", StringType.INSTANCE)
                    .addScalar("status", StringType.INSTANCE);
            query.setLong(0, userId);
            //
            return convertToStockDTO(query.list());
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
        }
        //
        return new ArrayList<>();
    }

    private List<CatStockDTO> convertToStockDTO(List<Object[]> lstData) {
        List<CatStockDTO> lstResult = Lists.newArrayList();
        for (Object[] i : lstData) {
            CatStockDTO temp = new CatStockDTO();
            temp.setId(i[0] == null ? "" : String.valueOf(i[0]));
            temp.setCustId(i[1] == null ? "" : String.valueOf(i[1]));
            temp.setCode(i[2] == null ? "" : String.valueOf(i[2]));
            temp.setName(i[3] == null ? "" : String.valueOf(i[3]));
            temp.setAddress(i[4] == null ? "" : String.valueOf(i[4]));
            temp.setStatus(i[5] == null ? "" : String.valueOf(i[5]));
            //
            lstResult.add(temp);
        }
        return lstResult;
    }
}

