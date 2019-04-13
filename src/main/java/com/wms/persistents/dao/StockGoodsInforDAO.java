package com.wms.persistents.dao;


import com.wms.dto.StockGoodsInfor;
import com.wms.utils.DataUtil;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by truongbx on 7/7/2018.
 */
@Repository
@Transactional
public class StockGoodsInforDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    private Logger log = LoggerFactory.getLogger(StockGoodsInforDAO.class);

    public List<StockGoodsInfor> getStockGoodsInfor(String partnerId, String custId) {
        List<StockGoodsInfor> lstStockGoodsInfor = new ArrayList<>();

        StringBuilder stockGoodsInforQuery = new StringBuilder();
        stockGoodsInforQuery.append(
                "  SELECT a.cust_id as custId,\n" +
                "         (SELECT c.name\n" +
                "            FROM cat_partner c\n" +
                "           WHERE c.id = a.partner_id)\n" +
                "             AS custName,\n" +
                "         a.stock_id stockId,\n" +
                "          (SELECT cs.name\n" +
                "            FROM cat_stock cs\n" +
                "           WHERE cs.id = a.stock_id)\n" +
                "             AS stockName,\n" +
                "         a.goods_id goodsId,\n" +
                "         (SELECT e.name\n" +
                "            FROM cat_goods e\n" +
                "           WHERE e.id = a.goods_id)\n" +
                "             AS goodName,\n" +
                "         a.goods_state goodsState,\n" +
                "         (SELECT d.name\n" +
                "            FROM app_params d\n" +
                "           WHERE d.TYPE = 'GOODS_STATE' AND d.code = a.goods_state)\n" +
                "             AS goodsStateName,\n" +
                "         a.partner_id partnerId,\n" +
                "         '0' AS serial,\n" +
                "         SUM (a.amount) AS amount\n" +
                "    FROM mjr_stock_goods a\n" +
                "   WHERE  a.partner_id = ? AND a.cust_id = ?  AND a.status = '1' \n" +
                "GROUP BY a.goods_id,\n" +
                "         a.stock_id,\n" +
                "         a.cust_id,\n" +
                "         a.goods_state,\n" +
                "         a.partner_id\n" +
                "         \n" +
                "         union all\n" +
                "         \n" +
                "           SELECT a.cust_id,\n" +
                "         (SELECT c.name\n" +
                "            FROM cat_partner c\n" +
                "           WHERE c.id = a.partner_id)\n" +
                "             AS cust_name,\n" +
                "         a.stock_id,\n" +
                "          (SELECT cs.name\n" +
                "            FROM cat_stock cs\n" +
                "           WHERE cs.id = a.stock_id)\n" +
                "             AS stock_name,\n" +
                "         a.goods_id,\n" +
                "         (SELECT e.name\n" +
                "            FROM cat_goods e\n" +
                "           WHERE e.id = a.goods_id)\n" +
                "             AS good_name,\n" +
                "         a.goods_state,\n" +
                "         (SELECT d.name\n" +
                "            FROM app_params d\n" +
                "           WHERE d.TYPE = 'GOODS_STATE' AND d.code = a.goods_state)\n" +
                "             AS goods_state_name,\n" +
                "         a.partner_id,\n" +
                "         '1' AS serial,\n" +
                "         SUM (a.amount) AS amount\n" +
                "    FROM mjr_stock_goods_serial a\n" +
                "   WHERE  a.partner_id = ? AND a.cust_id = ? AND a.status = '1' \n" +
                "GROUP BY a.goods_id,\n" +
                "         a.stock_id,\n" +
                "         a.cust_id,\n" +
                "         a.goods_state,\n" +
                "         a.partner_id\n" +
                "         \n" +
                "         ");

        Query ps = getSession().createSQLQuery(stockGoodsInforQuery.toString())
                .addScalar("custId",      StringType.INSTANCE)
                .addScalar("custName",      StringType.INSTANCE)
                .addScalar("stockId",      StringType.INSTANCE)
                .addScalar("stockName",      StringType.INSTANCE)
                .addScalar("goodsId",      StringType.INSTANCE)
                .addScalar("goodName",      StringType.INSTANCE)
                .addScalar("goodsState",      StringType.INSTANCE)
                .addScalar("goodsStateName",      StringType.INSTANCE)
                .addScalar("partnerId",      StringType.INSTANCE)
                .addScalar("serial",      StringType.INSTANCE)
                .addScalar("amount",      StringType.INSTANCE);
        ps.setResultTransformer(Transformers.aliasToBean(StockGoodsInfor.class));
        ps.setLong(0,Long.valueOf(partnerId));
        ps.setLong(1,Long.valueOf(custId));
        ps.setLong(2,Long.valueOf(partnerId));
        ps.setLong(3,Long.valueOf(custId));


        try {

            lstStockGoodsInfor = ps.list();
           return lstStockGoodsInfor;
        } catch (Exception e) {
            log.info(e.toString());
            return lstStockGoodsInfor ;
        }
    }

    public List<StockGoodsInfor> getStockGoodsDetailInfor(String partnerId, String custId,String stockId, String goodsId,String serial) {
        List<StockGoodsInfor> lstStockGoodsInfor = new ArrayList<>();

        StringBuilder stockGoodsDetailInforQuery = new StringBuilder();
        stockGoodsDetailInforQuery.append("  SELECT a.cust_id as custId,\n" +
                "         (SELECT c.name\n" +
                "            FROM cat_partner c\n" +
                "           WHERE c.id = a.partner_id)\n" +
                "             AS custName,\n" +
                "         a.stock_id stockId,\n" +
                "          (SELECT cs.name\n" +
                "            FROM cat_stock cs\n" +
                "           WHERE cs.id = a.stock_id)\n" +
                "             AS stockName,\n" +
                "         a.goods_id goodsId,\n" +
                "         (SELECT e.name\n" +
                "            FROM cat_goods e\n" +
                "           WHERE e.id = a.goods_id)\n" +
                "             AS goodName,\n" +
                "         a.goods_state goodsState,\n" +
                "         (SELECT d.name\n" +
                "            FROM app_params d\n" +
                "           WHERE d.TYPE = 'GOODS_STATE' AND d.code = a.goods_state)\n" +
                "             AS goodsStateName,\n" +
                "         a.partner_id partnerId,\n" +
                "         a.cell_code cellCode,\n" +
                "         a.import_date importDate,\n" +
                "         a.changed_date changeDate,\n" +
                "         a.amount amount,\n" +
                "         a.import_stock_trans_id importStockTransId,\n" +
                "         a.input_price imputPrice\n" );
        if (!DataUtil.isNullOrEmpty(serial) && serial.equalsIgnoreCase("1")){
            stockGoodsDetailInforQuery.append("   , a.serial \n");
            stockGoodsDetailInforQuery.append("   FROM mjr_stock_goods_serial a \n");
        }else{
            stockGoodsDetailInforQuery.append("   FROM mjr_stock_goods a \n");
        }
        stockGoodsDetailInforQuery.append("  WHERE  a.partner_id = ? AND a.cust_id = ? and a.stock_id = ? and a.goods_id = ? and a.status = '1'");

        SQLQuery ps = getSession().createSQLQuery(stockGoodsDetailInforQuery.toString())
                .addScalar("custId",      StringType.INSTANCE)
                .addScalar("custName",      StringType.INSTANCE)
                .addScalar("stockId",      StringType.INSTANCE)
                .addScalar("stockName",      StringType.INSTANCE)
                .addScalar("goodsId",      StringType.INSTANCE)
                .addScalar("goodName",      StringType.INSTANCE)
                .addScalar("goodsState",      StringType.INSTANCE)
                .addScalar("goodsStateName",      StringType.INSTANCE)
                .addScalar("partnerId",      StringType.INSTANCE)
                .addScalar("amount",      StringType.INSTANCE)
                .addScalar("cellCode",      StringType.INSTANCE)
                .addScalar("importDate",      StringType.INSTANCE)
                .addScalar("changeDate",      StringType.INSTANCE)
                .addScalar("importStockTransId",      StringType.INSTANCE)
                .addScalar("imputPrice",      StringType.INSTANCE);
        if (!DataUtil.isNullOrEmpty(serial) && serial.equalsIgnoreCase("1")){
            ps.addScalar("serial",      StringType.INSTANCE);
        }
        ps.setResultTransformer(Transformers.aliasToBean(StockGoodsInfor.class));
        ps.setLong(0,Long.valueOf(partnerId));
        ps.setLong(1,Long.valueOf(custId));
        ps.setLong(2,Long.valueOf(stockId));
        ps.setLong(3,Long.valueOf(goodsId));


        try {

            lstStockGoodsInfor = ps.list();
            return lstStockGoodsInfor;
        } catch (Exception e) {
            log.info(e.toString());
            return lstStockGoodsInfor ;
        }
    }
}
