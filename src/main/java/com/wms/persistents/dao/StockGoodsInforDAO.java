package com.wms.persistents.dao;


import com.wms.base.BaseDAOImpl;
import com.wms.dto.Condition;
import com.wms.dto.MjrStockTransDetailDTO;
import com.wms.dto.StockGoodsInfor;
import com.wms.persistents.model.SysMenu;
import com.wms.utils.DataUtil;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.wms.utils.Constants;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by truongbx on 7/7/2018.
 */
@Repository
@Transactional
public class StockGoodsInforDAO extends BaseDAOImpl<SysMenu, Long> {
    @PersistenceContext
    private EntityManager entityManager;

    /*public Session getSession() {
        return entityManager.unwrap(Session.class);
    }*/

    @Autowired
    SessionFactory sessionFactory;

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

    public List<MjrStockTransDetailDTO> getAllStockGoodsDetail(String userId, String custId, String stockId,  String partnerId, String goodsId, String status) {
        //Lay thong tin cua user
        StringBuilder str = new StringBuilder();
        StringBuilder initJoinQuery = new StringBuilder("");
        Session session = getSession();
        str.append(" select a.partner_permission, a.stock_permission ")
                .append(" from cat_user a")
                .append(" where a.status=1 and a.block =0")
                .append(" and a.id = ? ");
        Query ps = getSession().createSQLQuery(str.toString())
                .addScalar("partner_permission", StringType.INSTANCE)
                .addScalar("stock_permission", StringType.INSTANCE);
        ps.setString(0, userId);
        List<Object[]> lstData = ps.list();
        String partnerPermission = "";
        String stockPermission = "";
        for (Object[] i : lstData) {
            partnerPermission = (i[0] == null ? "" : String.valueOf(i[0]));
            stockPermission = (i[1] == null ? "" : String.valueOf(i[1]));
            break;
        }

        if ("1".equals(partnerPermission)) {
            initJoinQuery.append(" join MAP_USER_PARTNER b on a.partner_id = b.partner_id ")
                    .append(" and b.user_id = ").append(userId);
        }
        if ("1".equals(stockPermission)) {
            initJoinQuery.append(" join MAP_USER_STOCK c on a.stock_id = c.stock_id ")
                    .append(" and c.user_id = ").append(userId);
        }


        List<MjrStockTransDetailDTO> lstStockGoodsInfor = new ArrayList<>();
        StringBuilder allStockGoodsQuery = new StringBuilder("");
        StringBuilder stockGoodsQuery = new StringBuilder(" , null as serial \n FROM mjr_stock_goods a ");
        StringBuilder stockGoodsSerialQuery = new StringBuilder(", a.serial as serial \n FROM mjr_stock_goods_serial a ");

        StringBuilder initSelectQuery = new StringBuilder();
        StringBuilder initWhereQuery = new StringBuilder(" WHERE 1=1 and a.status =1 ");
        initSelectQuery.append("  SELECT a.cust_id as custId,\n" +
                "         a.stock_id stockId,\n" +
                "         a.goods_id goodsId,\n" +
                "         a.goods_state goodsState,\n" +
                "         a.partner_id partnerId,\n" +
                "         a.cell_code cellCode,\n" +
                "         a.import_date importDate,\n" +
                "         a.changed_date changeDate,\n" +
                "         a.amount amount,\n" +
                "         a.weight weight,\n" +
                "         a.volume volume,\n" +
                "         a.input_price inputPrice\n" );

        if(!DataUtil.isStringNullOrEmpty(custId) && !custId.equals(Constants.STATS_ALL)){
            initWhereQuery.append(" and a.cust_id = ").append(custId);
        }
        if(!DataUtil.isStringNullOrEmpty(stockId) && !stockId.equals(Constants.STATS_ALL)){
            initWhereQuery.append(" and a.stock_id = ").append(stockId);
        }
        if(!DataUtil.isStringNullOrEmpty(partnerId) && !partnerId.equals(Constants.STATS_ALL)){
            initWhereQuery.append(" and a.partner_id = ").append(partnerId);
        }
        if(!DataUtil.isStringNullOrEmpty(goodsId) && !goodsId.equals(Constants.STATS_ALL)){
            initWhereQuery.append(" and a.goods_id = ").append(goodsId);
        }
        if(!DataUtil.isStringNullOrEmpty(status) && !status.equals(Constants.STATS_ALL)){
            initWhereQuery.append(" and a.goods_state = ").append(status);
        }

        allStockGoodsQuery.append("select gt.* from (\n")
                .append(initSelectQuery)
                .append(stockGoodsQuery)
                .append(initWhereQuery)
                .append(initJoinQuery)
                .append(" \n union all \n")
                .append(initSelectQuery)
                .append(stockGoodsSerialQuery)
                .append(initWhereQuery)
                .append(initJoinQuery)
                .append(" \n)gt order by gt.custid, gt.stockid, gt.goodsid desc");

        ps = getSession().createSQLQuery(allStockGoodsQuery.toString())
                .addScalar("custId",      StringType.INSTANCE)
                .addScalar("stockId",      StringType.INSTANCE)
                .addScalar("goodsId",      StringType.INSTANCE)
                .addScalar("goodsState",      StringType.INSTANCE)
                .addScalar("partnerId",      StringType.INSTANCE)
                .addScalar("amount",      StringType.INSTANCE)
                .addScalar("volume",      StringType.INSTANCE)
                .addScalar("weight",      StringType.INSTANCE)
                .addScalar("cellCode",      StringType.INSTANCE)
                .addScalar("importDate",      StringType.INSTANCE)
                .addScalar("changeDate",      StringType.INSTANCE)
                .addScalar("inputPrice",      StringType.INSTANCE)
                .addScalar("serial",      StringType.INSTANCE);
        ps.setResultTransformer(Transformers.aliasToBean(MjrStockTransDetailDTO.class));
        try {
            lstStockGoodsInfor = ps.list();
            return lstStockGoodsInfor;
        } catch (Exception e) {
            log.info(e.toString());
            return lstStockGoodsInfor ;
        }
    }
}
