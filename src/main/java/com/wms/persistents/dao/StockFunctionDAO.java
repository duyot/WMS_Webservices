package com.wms.persistents.dao;

import com.google.common.collect.Lists;
import com.wms.base.BaseDAOImpl;
import com.wms.business.impl.StockManagementBusinessImpl;
import com.wms.dto.*;
import com.wms.enums.Responses;
import com.wms.persistents.model.SysMenu;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.FunctionUtils;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.result.Output;
import org.hibernate.result.ResultSetOutput;
import org.hibernate.type.DateType;
import org.hibernate.type.FloatType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by duyot on 3/6/2017.
 */
@Repository
@Transactional
public class StockFunctionDAO extends BaseDAOImpl<SysMenu, Long> {
    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    MjrStockGoodsTotalDAO mjrStockGoodsTotalDAO;

    @Autowired
    MjrStockGoodsSerialDAO mjrStockGoodsSerialDAO;

    @Autowired
    MjrStockGoodsDAO mjrStockGoodsDAO;

    @Autowired
    MjrStockTransDAO mjrStockTransDAO;

    @Autowired
    MjrStockTransDetailDAO mjrStockTransDetailDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Logger log = LoggerFactory.getLogger(StockManagementBusinessImpl.class);

    @Transactional
    public List<MjrStockTransDetailDTO> getTransGoodsDetail(String custId, String stockId, String transId, String transType) {
        Session session;
        List<Object[]> lstResult = null;
        try {
            session = getSession();
            ProcedureCall pc = session.createStoredProcedureCall("pkg_stock_info.p_get_trans_goods_details");
            pc.registerParameter("p_cust_id", Integer.class, ParameterMode.IN).bindValue(Integer.parseInt(custId));
            pc.registerParameter("p_stock_id", Integer.class, ParameterMode.IN).bindValue(Integer.parseInt(stockId));
            pc.registerParameter("p_stock_trans_id", Integer.class, ParameterMode.IN).bindValue(Integer.parseInt(transId));
            pc.registerParameter("p_trans_type", Integer.class, ParameterMode.IN).bindValue(Integer.parseInt(transType));
            pc.registerParameter("p_result", Class.class, ParameterMode.REF_CURSOR);
            Output output = pc.getOutputs().getCurrent();
            if (output.isResultSet()) {
                lstResult = ((ResultSetOutput) output).getResultList();
            }
            return convertToDetail(lstResult);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public List<MjrStockTransDetailDTO> getListTransGoodsDetail(String transId) {
        Session session = getSession();
        String[] lstTransId = transId.split("or");
        StringBuilder idConditionStr = new StringBuilder();
        if(transId == null || "".equals(transId)){
            idConditionStr.append("a.id in (-1)");
        }else{
            for(int i=0; i< lstTransId.length; i++){
                idConditionStr.append("a.id in (").append(lstTransId[i]).append(")");
                if (i != lstTransId.length - 1) {
                    idConditionStr.append(" or ");
                }
            }
        }
        //
        StringBuilder str = new StringBuilder();
        str.append(" select a.code,c.name, a.type, b.goods_code, b.goods_id, b.goods_state,b.amount, b.unit_name, to_char(a.created_date,'dd/MM/yyyy') created_date, a.CREATED_USER, b.input_price, b.output_price, b.cell_code, b.serial, b.total_money, b.weight, b.volume, d.name as goods_name, a.description, to_char(b.produce_date,'dd/MM/yyyy') produce_date , to_char(b.expire_date,'dd/MM/yyyy') expire_date, b.content, a.order_code, a.reason_name, a.receive_name, a.partner_name ")
                .append(" from mjr_stock_trans a, mjr_stock_trans_detail b, cat_stock c, cat_goods d")
                .append(" WHERE 1 = 1 ")
                .append(" and (  ")
                .append(idConditionStr)
                .append(" ) ")
                .append(" and a.id= b.STOCK_TRANS_ID")
                .append(" and a.stock_id = c.id")
                .append(" and b.goods_id = d.id")
                .append(" order by a.id desc");
        Query ps = session.createSQLQuery(str.toString())
                .addScalar("code", StringType.INSTANCE)
                .addScalar("name", StringType.INSTANCE)
                .addScalar("type", LongType.INSTANCE)
                .addScalar("goods_code", StringType.INSTANCE)
                .addScalar("goods_id", LongType.INSTANCE)
                .addScalar("goods_state", StringType.INSTANCE)
                .addScalar("amount", FloatType.INSTANCE)
                .addScalar("unit_name", StringType.INSTANCE)
                .addScalar("created_date", StringType.INSTANCE)
                .addScalar("CREATED_USER", StringType.INSTANCE)
                .addScalar("input_price", FloatType.INSTANCE)
                .addScalar("output_price", FloatType.INSTANCE)
                .addScalar("cell_code", StringType.INSTANCE)
                .addScalar("serial", StringType.INSTANCE)
                .addScalar("total_money", StringType.INSTANCE)
                .addScalar("weight", FloatType.INSTANCE)
                .addScalar("volume", FloatType.INSTANCE)
                .addScalar("goods_name", StringType.INSTANCE)
                .addScalar("description", StringType.INSTANCE)
                .addScalar("produce_date", StringType.INSTANCE)
                .addScalar("expire_date", StringType.INSTANCE)
                .addScalar("content", StringType.INSTANCE)
                .addScalar("order_code", StringType.INSTANCE)
                .addScalar("reason_name", StringType.INSTANCE)
                .addScalar("receive_name", StringType.INSTANCE)
                .addScalar("partner_name", StringType.INSTANCE)
                ;
        //
        return convertToStockTransDetail(ps.list());
    }

    @Transactional
    public List<MjrStockTransDTO> getStockTransInfo(String lstStockTransId) {

        Session session = getSession();
        String[] ids = lstStockTransId.split(",");
        int size = ids.length;
        StringBuilder lstParamIds = new StringBuilder();
        if ("".equalsIgnoreCase(lstStockTransId.trim())) {
            lstParamIds.append("?");
        } else {
            for (int i = 0; i < size; i++) {
                lstParamIds.append("?");
                if (i != size - 1) {
                    lstParamIds.append(",");
                }
            }
        }

        StringBuilder str = new StringBuilder();
        str.append(" select d.name as customer_name, a.code as trans_code,c.name as stock_name, c.code as stock_code, a.created_date, a.description,\n" +
                " b.code as partner_code, b.name as partner_name, b.tel_number, b.address , a.CUST_ID , a.stock_id, a.CREATED_USER , a.type,a.receive_name , a.ORDER_CODE orderCode")
                .append(" from mjr_stock_trans a " +
                        " left join CAT_PARTNER b on a.partner_id = b.id " +
                        " left join cat_stock c on a.stock_id = c.id " +
                        " left join CAT_CUSTOMER d on a.CUST_ID = d.id ")
                .append(" WHERE 1=1 ")
                .append(" and a.id in( ")
                .append(lstParamIds)
                .append(" )")
                .append(" order by a.id desc ");
        Query ps = session.createSQLQuery(str.toString())
                .addScalar("customer_name", StringType.INSTANCE)
                .addScalar("trans_code", StringType.INSTANCE)
                .addScalar("stock_name", StringType.INSTANCE)
                .addScalar("stock_code", StringType.INSTANCE)
                .addScalar("created_date", DateType.INSTANCE)
                .addScalar("description", StringType.INSTANCE)
                .addScalar("partner_code", StringType.INSTANCE)
                .addScalar("partner_name", StringType.INSTANCE)
                .addScalar("tel_number", StringType.INSTANCE)
                .addScalar("address", StringType.INSTANCE)
                .addScalar("cust_id", StringType.INSTANCE)
                .addScalar("stock_id", StringType.INSTANCE)
                .addScalar("created_user", StringType.INSTANCE)
                .addScalar("type", StringType.INSTANCE)
                .addScalar("receive_name", StringType.INSTANCE)
                .addScalar("orderCode", StringType.INSTANCE);
        if ("".equalsIgnoreCase(lstStockTransId.trim())) {
            ps.setString("1", "-1");
        } else {
            for (int i = 0; i < size; i++) {
                ps.setString(i, ids[i]);
            }
        }

        return convertToStockTransInfo(ps.list());
    }

    @Transactional
    public List<ChartDTO> getTotalStockTrans(String custId, int type, String userId) {

        Session session = getSession();
        Date date = new Date();
        String currentDate = DateTimeUtils.convertDateToString(date, "dd/MM/yyyy");
        String beforeDate;

        if (type == 7) {
            beforeDate = DateTimeUtils.convertDateToString(DateTimeUtils.getDateCompareToCurrent(-type + 1), "dd/MM/yyyy");
        } else {
            beforeDate = DateTimeUtils.convertDateToString(DateTimeUtils.getFirstDateInMonth(), "dd/MM/yyyy");
        }

        StringBuilder str = new StringBuilder();
        str.append(" select a.partner_permission, a.stock_permission ")
                .append(" from cat_user a")
                .append(" where a.status=1 and a.block =0")
                .append(" and a.id = ? ");
        Query ps = session.createSQLQuery(str.toString())
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
        str = new StringBuilder();
        str.append(" select to_char(a.created_date,'dd/MM/yyyy') as created_date, a.type, count(*) as so_luong ")
                .append(" from mjr_stock_trans a");
        if ("1".equals(partnerPermission)) {
            str.append(" join MAP_USER_PARTNER b on a.partner_id = b.partner_id ")
                    .append(" and b.user_id = ").append(userId);
        }
        if ("1".equals(stockPermission)) {
            str.append(" join MAP_USER_STOCK c on a.stock_id = c.stock_id ")
                    .append(" and c.user_id = ").append(userId);
        }
        str.append(" where 1=1")
                .append(" and a.cust_id = ? ")
                .append(" and a.created_date >= ? ")
                .append(" group by to_char(a.created_date,'dd/MM/yyyy'), a.type")
                .append(" order by to_char(a.created_date,'dd/MM/yyyy'), a.type");

        ps = session.createSQLQuery(str.toString())
                .addScalar("created_date", StringType.INSTANCE)
                .addScalar("type", StringType.INSTANCE)
                .addScalar("so_luong", StringType.INSTANCE);
        ps.setString(0, custId);
        ps.setDate(1, DateTimeUtils.convertStringToDate(beforeDate));

        return convertToTotalStockTrans(ps.list(), DateTimeUtils.convertStringToDate(beforeDate), type);
    }

    @Transactional
    public List<ChartDTO> getKPIStorage(String custId, int type, String userId) {

        Session session = getSession();
        Date date = new Date();
        String beforeDate = DateTimeUtils.convertDateToString(DateTimeUtils.getDateCompareToCurrent(-30), "dd/MM/yyyy");

        StringBuilder str = new StringBuilder();
        str.append(" select a.partner_permission, a.stock_permission ")
                .append(" from cat_user a")
                .append(" where a.status=1 and a.block =0")
                .append(" and a.id = ? ");
        Query ps = session.createSQLQuery(str.toString())
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
        str = new StringBuilder();
        str.append(" select gr.* from ( ")
                .append(" select to_char(a.IMPORT_DATE,'dd/MM/yyyy') as IMPORT_DATE , a.goods_id, a.stock_id, a.partner_id, a.cust_id from MJR_STOCK_GOODS a where a.status =1 and a.IMPORT_DATE <= ?")
                .append(" union all")
                .append(" select to_char(b.IMPORT_DATE,'dd/MM/yyyy') as IMPORT_DATE , b.goods_id, b.stock_id, b.partner_id, b.cust_id from MJR_STOCK_GOODS_serial b where b.status =1 and b.IMPORT_DATE <= ?")
                .append("   ) gr ")
        ;
        if ("1".equals(partnerPermission)) {
            str.append(" join MAP_USER_PARTNER c on gr.partner_id = c.partner_id ")
                    .append(" and c.user_id = ").append(userId);
        }
        if ("1".equals(stockPermission)) {
            str.append(" join MAP_USER_STOCK d on gr.stock_id = d.stock_id ")
                    .append(" and d.user_id = ").append(userId);
        }
        str.append(" where 1=1")
                .append(" and gr.cust_id = ? ")
                .append(" order by gr.IMPORT_DATE");

        ps = session.createSQLQuery(str.toString())
                .addScalar("IMPORT_DATE", StringType.INSTANCE)
                .addScalar("goods_id", StringType.INSTANCE)
                .addScalar("stock_id", StringType.INSTANCE)
                .addScalar("partner_id", StringType.INSTANCE)
                .addScalar("cust_id", StringType.INSTANCE)
        ;
        ps.setDate(0, DateTimeUtils.convertStringToDate(beforeDate));
        ps.setDate(1, DateTimeUtils.convertStringToDate(beforeDate));
        ps.setString(2, custId);

        if (DataUtil.isListNullOrEmpty(ps.list())) {
            return Lists.newArrayList();
        }
        return getKPIStorageChartFromData(ps.list());
    }

    private List<ChartDTO> getKPIStorageChartFromData(List<Object[]> lstData) {
        List<ChartDTO> lstResult = Lists.newArrayList();
        Session session = getSession();
        Date date = new Date();
        long diffInMillies = 0;
        long diff = 0;
        Date importDate = null;
        Map<String, String> map1month = new HashMap<String, String>();
        Map<String, String> map3month = new HashMap<String, String>();
        Map<String, String> map6month = new HashMap<String, String>();
        Map<String, String> map12month = new HashMap<String, String>();
        Map<String, String> map36month = new HashMap<String, String>();
        Double amount1month = 0.0;
        Double amount3month = 0.0;
        Double amount6month = 0.0;
        Double amount12month = 0.0;
        Double amount36month = 0.0;

        for (Object[] i : lstData) {
            importDate = DateTimeUtils.convertStringToTime(String.valueOf(i[0]), "dd/MM/yyyy");
            diffInMillies = Math.abs(date.getTime() - importDate.getTime());
            diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (diff >= 1095) {
                if (!map36month.containsKey(String.valueOf(i[1]))) {
                    amount36month++;
                    map36month.put(String.valueOf(i[1]), String.valueOf(i[1]));
                }
            } else if (diff >= 365) {
                if (!map12month.containsKey(String.valueOf(i[1]))) {
                    amount12month++;
                    map12month.put(String.valueOf(i[1]), String.valueOf(i[1]));
                }
            } else if (diff >= 180) {
                if (!map6month.containsKey(String.valueOf(i[1]))) {
                    amount6month++;
                    map6month.put(String.valueOf(i[1]), String.valueOf(i[1]));
                }
            } else if (diff >= 90) {
                if (!map3month.containsKey(String.valueOf(i[1]))) {
                    amount3month++;
                    map3month.put(String.valueOf(i[1]), String.valueOf(i[1]));
                }
            } else {
                if (!map1month.containsKey(String.valueOf(i[1]))) {
                    amount1month++;
                    map1month.put(String.valueOf(i[1]), String.valueOf(i[1]));
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            ChartDTO data = new ChartDTO();
            if (i == 0) {
                data.setName(">=1 tháng");
                data.setY(amount1month);
            } else if (i == 1) {
                data.setName(">=3 tháng");
                data.setY(amount3month);
            } else if (i == 2) {
                data.setName(">=6 tháng");
                data.setY(amount6month);
            } else if (i == 3) {
                data.setName(">=1 năm");
                data.setY(amount12month);
            } else {
                data.setName(">=3 năm");
                data.setY(amount36month);
            }
            lstResult.add(data);
        }
        return lstResult;
    }

    private List<ChartDTO> convertToTotalStockTrans(List<Object[]> lstData, Date beforeDate, int type) {
        List<ChartDTO> lstChart = Lists.newArrayList();
        Double[] dataExport = new Double[type];
        Double[] dataImport = new Double[type];
        Date curDate = null;
        int count = 0;
        for (int j = 0; j < type; j++) {
            curDate = DateTimeUtils.getNextDate(beforeDate, j);
            for (Object[] i : lstData) {
                if (dataImport[j] != null && dataExport[j] != null) {
                    break;
                }
                if (curDate.compareTo(DateTimeUtils.convertStringToTime(String.valueOf(i[0]), "dd/MM/yyyy")) == 0) {
                    if (String.valueOf(i[1]).equals("1")) {
                        dataImport[j] = Double.valueOf(String.valueOf(i[2]));
                    } else {
                        dataExport[j] = Double.valueOf(String.valueOf(i[2]));
                    }
                }
            }
            if (dataImport[j] == null) {
                dataImport[j] = 0.0;
            }
            if (dataExport[j] == null) {
                dataExport[j] = 0.0;
            }
        }
        lstChart.add(new ChartDTO("Nhập", dataImport));
        lstChart.add(new ChartDTO("Xuất", dataExport));
        return lstChart;
    }


    private List<MjrStockTransDTO> convertToStockTransInfo(List<Object[]> lstData) {
        List<MjrStockTransDTO> lstResult = Lists.newArrayList();
        for (Object[] i : lstData) {
            MjrStockTransDTO temp = new MjrStockTransDTO();
            temp.setCustomerName(i[0] == null ? "" : String.valueOf(i[0]));
            temp.setCode(i[1] == null ? "" : String.valueOf(i[1]));
            temp.setStockName(i[2] == null ? "" : String.valueOf(i[2]));
            temp.setStockCode(i[3] == null ? "" : String.valueOf(i[3]));
            temp.setCreatedDate(i[4] == null ? "" : String.valueOf(i[4]));
            temp.setDescription(i[5] == null ? "" : String.valueOf(i[5]));
            temp.setPartnerCode(i[6] == null ? "" : String.valueOf(i[6]));
            temp.setPartnerName(i[7] == null ? "" : String.valueOf(i[7]));
            temp.setPartnerTelNumber(i[8] == null ? "" : String.valueOf(i[8]));
            temp.setPartnerAddress(i[9] == null ? "" : String.valueOf(i[9]));
            temp.setCustId(i[10] == null ? "" : String.valueOf(i[10]));
            temp.setStockId(i[11] == null ? "" : String.valueOf(i[11]));
            temp.setCreatedUser(i[12] == null ? "" : String.valueOf(i[12]));
            temp.setType(i[13] == null ? "" : String.valueOf(i[13]));
            temp.setReceiveName(i[14] == null ? "" : String.valueOf(i[14]));
            temp.setOrderCode(i[15] == null ? "" : String.valueOf(i[15]));
            lstResult.add(temp);
        }
        return lstResult;
    }

    private List<MjrStockTransDetailDTO> convertToDetail(List<Object[]> lstData) {
        List<MjrStockTransDetailDTO> lstResult = Lists.newArrayList();
        for (Object[] i : lstData) {
            MjrStockTransDetailDTO temp = new MjrStockTransDetailDTO();
            temp.setGoodsId((i[0] + ""));
            temp.setGoodsState((String) i[1]);
            temp.setCellCode((String) i[2]);
            temp.setAmount(i[3] + "");
            temp.setSerial((String) i[4]);
            temp.setInputPrice(i[5] == null ? "" : i[5] + "");
            temp.setOutputPrice(i[6] == null ? "" : i[6] + "");
            //
            lstResult.add(temp);
        }
        return lstResult;
    }


    private List<MjrStockTransDetailDTO> convertToStockTransDetail(List<Object[]> lstData) {
        List<MjrStockTransDetailDTO> lstResult = Lists.newArrayList();
        for (Object[] i : lstData) {
            MjrStockTransDetailDTO temp = new MjrStockTransDetailDTO();
            temp.setStockTransCode(i[0] == null ? "" : String.valueOf(i[0]));
            temp.setStockName(i[1] == null ? "" : String.valueOf(i[1]));
            temp.setStockTransType(String.valueOf(i[2]).equals("1") ? "Nhập" : "Xuất");
            temp.setGoodsCode(i[3] == null ? "" : String.valueOf(i[3]));
            temp.setGoodsId(i[4] == null ? "" : String.valueOf(i[4]));
            temp.setGoodsState(String.valueOf(i[5]).equals("1") ? "Bình thường" : "Hỏng");
            temp.setAmount(i[6] == null ? "" : FunctionUtils.formatNumber(String.valueOf(i[6])));
            temp.setUnitName(i[7] == null ? "" : String.valueOf(i[7]));
            temp.setStockTransCreatedDate(i[8] == null ? "" : String.valueOf(i[8]));
            temp.setStockTransCreatedUser(i[9] == null ? "" : String.valueOf(i[9]));
            temp.setInputPrice(i[10] == null ? null : FunctionUtils.formatNumber(String.valueOf(i[10])));
            temp.setOutputPrice(i[11] == null ? null : FunctionUtils.formatNumber(String.valueOf(i[11])));
            temp.setCellCode(i[12] == null ? "" : String.valueOf(i[12]));
            temp.setSerial(i[13] == null ? "" : String.valueOf(i[13]));
            temp.setTotalMoney(i[14] == null ? "" : FunctionUtils.formatNumber(String.valueOf(i[14])));
            temp.setWeight(i[15] == null ? "" : FunctionUtils.formatNumber(String.valueOf(i[15])));
            temp.setVolume(i[16] == null ? "" : FunctionUtils.formatNumber(String.valueOf(i[16])));
            temp.setGoodsName(i[17] == null ? "" : String.valueOf(i[17]));
            temp.setDescription(i[18] == null ? "" : String.valueOf(i[18]));
            temp.setProduceDate(i[19] == null ? "" : String.valueOf(i[19]));
            temp.setExpireDate(i[20] == null ? "" : String.valueOf(i[20]));
            temp.setContent(i[21] == null ? "" : String.valueOf(i[21]));
            temp.setOrderCode(i[22] == null ? "" : String.valueOf(i[22]));
            temp.setReasonName(i[23] == null ? "" : String.valueOf(i[23]));
            temp.setReceiveName(i[24] == null ? "" : String.valueOf(i[24]));
            temp.setPartnerName(i[25] == null ? "" : String.valueOf(i[25]));

            //
            lstResult.add(temp);
        }
        return lstResult;
    }


    @Transactional
    public Long getCountGoodsDetail(String custId, String stockId, String goodsId, String goodsState, String isSerial) {
        Session session;
        try {
            session = getSession();
            ProcedureCall pc = session.createStoredProcedureCall("pkg_stock_info.count_goods_details");
            pc.registerParameter("p_cust_id", Integer.class, ParameterMode.IN).bindValue(Integer.parseInt(custId));
            pc.registerParameter("p_stock_id", Integer.class, ParameterMode.IN).bindValue(Integer.parseInt(stockId));
            pc.registerParameter("p_goods_id", Integer.class, ParameterMode.IN).bindValue(Integer.parseInt(goodsId));
            pc.registerParameter("p_goods_state", Integer.class, ParameterMode.IN).bindValue(Integer.parseInt(goodsState));
            pc.registerParameter("p_is_serial", Integer.class, ParameterMode.IN).bindValue(Integer.parseInt(isSerial));
            pc.registerParameter("p_total", Long.class, ParameterMode.OUT);

            return (Long) pc.getOutputs().getOutputParameterValue("p_total");
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    //
    @Transactional
    public List<String> getListSerialInStock(String custId, String stockId, String goodsId, String goodsState) {
        Session session = getSession();
        StringBuilder str = new StringBuilder();
        str.append("SELECT serial FROM mjr_stock_goods_serial a")
                .append(" WHERE ")
                .append("     a.cust_id  = :custId ")
                .append(" and a.stock_id = :stockId ")
                .append(" and a.goods_id = :goodsId ")
                .append(" and a.goods_state = :goodsState ")
                .append(" and a.status = 1 ")
                .append(" order by serial desc ");
        Query ps = session.createSQLQuery(str.toString());
        ps.setInteger("custId", Integer.parseInt(custId));
        ps.setInteger("stockId", Integer.parseInt(stockId));
        ps.setInteger("goodsId", Integer.parseInt(goodsId));
        ps.setInteger("goodsState", Integer.parseInt(goodsState));

        return ps.list();
    }


    @Transactional
    public ResponseObject cancelTransaction(String transId) {
        Session session;
        try {
            session = getSession();
            ProcedureCall pc = session.createStoredProcedureCall("pkg_stock_transaction.f_destroy_command");
            pc.registerParameter("p_command_id", Integer.class, ParameterMode.IN).bindValue(Integer.parseInt(transId));
            pc.registerParameter("v_result", String.class, ParameterMode.OUT);

            String result = (String) pc.getOutputs().getOutputParameterValue("v_result");
            return getResponseFromResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseObject(Responses.ERROR.getName(), Responses.ERROR.getName(), "");

    }

    public ResponseObject getResponseFromResult(String result) {
        ResponseObject responseObject = new ResponseObject();
        String[] resultArr = result.split("\\|");
        String resultCode = resultArr[0];
        String resultName = resultArr[1];
        if ("1".equalsIgnoreCase(resultCode)) {
            responseObject.setStatusCode(Responses.SUCCESS.getName());
        } else {
            responseObject.setStatusCode(Responses.ERROR.getName());
            responseObject.setStatusName(resultName);
        }
        return responseObject;
    }

    //------------------------------------------------------------------------------------------------------------------
    public String getTotalStockTransaction(String stockId, Connection connection) {
        String sql = " select count(*) trans_num from mjr_stock_trans where type = 1 and stock_id = ? ";
        int count = 0;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(stockId));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("trans_num");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            count = 0;
        } finally {
            FunctionUtils.closeStatement(ps);
        }
        return String.format("%08d", count);
    }

    //
    public String getTotalStockTransaction(String stockId, Session session) {
        String sql = " select count(*) from MJR_STOCK_TRANS where  type = 2 and stock_id = :stockId ";
        Query ps = session.createSQLQuery(sql);
        ps.setInteger("stockId", Integer.parseInt(stockId));
        BigDecimal countNumber = (BigDecimal) ps.uniqueResult();

        return String.format("%08d", countNumber.intValue());
    }

    //------------------------------------------------------------------------------------------------------------------
    public ResponseObject exportStockGoodsDetail(MjrStockTransDTO mjrStockTransDTO, MjrStockTransDetailDTO goodsDetail, Session session) {
        //
        ResponseObject exportResult = new ResponseObject();
        //1. save mjr_trans_detail
        goodsDetail.setStockTransId(mjrStockTransDTO.getId());
        String savedStockTranDetailId = mjrStockTransDetailDAO.saveBySession(goodsDetail.toModel(), session);
        if (!DataUtil.isInteger(savedStockTranDetailId)) {
            exportResult.setStatusCode(Responses.ERROR.getName());
            exportResult.setStatusName(Responses.ERROR_CREATE_STOCK_TRANS_DETAIL.getName());
            return exportResult;
        }
        //
        if (goodsDetail.isSerial()) {
            exportResult = mjrStockGoodsSerialDAO.exportStockGoodsSerial(mjrStockTransDTO, goodsDetail, session);
        } else {
            exportResult = mjrStockGoodsDAO.exportStockGoods(mjrStockTransDTO, goodsDetail, session);
        }
        //
        return exportResult;
    }


    public ResponseObject updateExportStockGoodsTotal(MjrStockTransDTO mjrStockTransDTO, Map<String, Float> mapGoodsNumber, Session session) {
        Iterator iterator = mapGoodsNumber.entrySet().iterator();
        String goodsInfo;
        Float amount;
        MjrStockGoodsTotalDTO total = new MjrStockGoodsTotalDTO();
        total.setCustId(mjrStockTransDTO.getCustId());
        total.setStockId(mjrStockTransDTO.getStockId());
        total.setChangeDate(mjrStockTransDTO.getCreatedDate());
        //
        ResponseObject updateTotalResult;
        //
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            //
            goodsInfo = (String) pair.getKey();
            amount = (Float) pair.getValue();
            String[] goodsInfoArr = goodsInfo.split(",");
            String goodsId = goodsInfoArr[0];
            String goodsState = goodsInfoArr[1];
            total.setAmount(amount + "");
            total.setGoodsId(goodsId);
            total.setGoodsState(goodsState);
            updateTotalResult = mjrStockGoodsTotalDAO.updateExportTotal(total, isNeedUpdateIssueAmount(mjrStockTransDTO) , session);
            if (!Responses.SUCCESS.getName().equalsIgnoreCase(updateTotalResult.getStatusCode())) {
                return updateTotalResult;
            }
        }
        return new ResponseObject(Responses.SUCCESS.getName(), Responses.SUCCESS.getName());
    }

    private boolean isNeedUpdateIssueAmount(MjrStockTransDTO mjrStockTransDTO){
        return DataUtil.isStringNullOrEmpty(mjrStockTransDTO.getOrderId());
    }

    public String saveStockTransByConnection(MjrStockTransDTO mjrStockTransDTO, Connection connection) {
        return mjrStockTransDAO.saveStockTransByConnection(mjrStockTransDTO, connection);
    }


    public String saveStockGoodsTotalByConnection(MjrStockTransDTO mjrStockTransDTO, Map<String, Float> mapGoodsNumber,
                                                  Map<String, CatGoodsDTO> mapGoods, Connection connection) {
        Iterator it = mapGoodsNumber.entrySet().iterator();
        String updateResult;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            //
            String key = (String) pair.getKey();
            Float amount = (Float) pair.getValue();
            String goodsCode = key.split(",")[0];
            String goodsState = key.split(",")[1];
            CatGoodsDTO goods = mapGoods.get(goodsCode);
            MjrStockGoodsTotalDTO stockGoodsTotal = new MjrStockGoodsTotalDTO();
            stockGoodsTotal.setCustId(mjrStockTransDTO.getCustId());
            stockGoodsTotal.setStockId(mjrStockTransDTO.getStockId());
            stockGoodsTotal.setGoodsId(goods.getId());
            stockGoodsTotal.setGoodsCode(goods.getCode());
            stockGoodsTotal.setGoodsName(goods.getName());
            stockGoodsTotal.setGoodsState(goodsState);
            stockGoodsTotal.setAmount(amount + "");
            stockGoodsTotal.setIssueAmount(amount + "");
            stockGoodsTotal.setChangeDate(mjrStockTransDTO.getCreatedDate());

            updateResult = mjrStockGoodsTotalDAO.updateImportTotal(stockGoodsTotal, connection);
            if (!Responses.SUCCESS.getName().equalsIgnoreCase(updateResult)) {
                return updateResult;
            }
            //
        }

        return Responses.SUCCESS.getName();
    }

    public String importStockGoods(MjrStockTransDTO mjrStockTransDTO, List<MjrStockTransDetailDTO> lstGoodsDetail, Connection connection) {
        //
        List paramsStockTransDetail;
        List paramsStockGoodsSerial;
        List paramsStockGoods;
        //PREPARE STATEMENTS
        PreparedStatement prstmtInsertStockTransDetail = null;
        PreparedStatement prstmtInsertStockGoodsSerial = null;
        PreparedStatement prstmtInsertStockGoods = null;
        //SQL
        StringBuilder sqlStockTransDetail = new StringBuilder();
        StringBuilder sqlStockGoodsSerial = new StringBuilder();
        StringBuilder sqlStockGoods = new StringBuilder();
        //STOCK_TRANS_DETAIL
        sqlStockTransDetail.append(" Insert into MJR_STOCK_TRANS_DETAIL ");
        sqlStockTransDetail.append(" (ID,STOCK_TRANS_ID,GOODS_ID,GOODS_CODE,GOODS_STATE,IS_SERIAL,AMOUNT,SERIAL,INPUT_PRICE,CELL_CODE, total_money, unit_name, volume, weight,PRODUCE_DATE,EXPIRE_DATE,DESCRIPTION  )  ");
        sqlStockTransDetail.append(" values  (SEQ_MJR_STOCK_TRANS_DETAIL.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),to_date(?,'dd/MM/yyyy hh24:mi:ss'),?) ");
        //STOCK_GOODS_SERIAL
        sqlStockGoodsSerial.append(" Insert into MJR_STOCK_GOODS_SERIAL  ");
        sqlStockGoodsSerial.append(" (ID,CUST_ID,STOCK_ID,GOODS_ID,GOODS_STATE,CELL_CODE,AMOUNT,SERIAL,IMPORT_DATE,CHANGED_DATE,STATUS,PARTNER_ID,IMPORT_STOCK_TRANS_ID,INPUT_PRICE, volume, weight, PRODUCE_DATE,EXPIRE_DATE,DESCRIPTION )  ");
        sqlStockGoodsSerial.append(" values (SEQ_MJR_STOCK_GOODS_SERIAL.nextval,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),to_date(?,'dd/MM/yyyy hh24:mi:ss'),?,?,?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),to_date(?,'dd/MM/yyyy hh24:mi:ss'),?) ");
        sqlStockGoodsSerial.append(" LOG ERRORS INTO ERR$_MJR_STOCK_GOODS_SERIAL REJECT LIMIT UNLIMITED ");
        //STOCK_GOODS
        sqlStockGoods.append(" Insert into MJR_STOCK_GOODS ");
        sqlStockGoods.append(" (ID,CUST_ID,STOCK_ID,GOODS_ID,GOODS_STATE,CELL_CODE,AMOUNT,IMPORT_DATE,CHANGED_DATE,STATUS,PARTNER_ID,IMPORT_STOCK_TRANS_ID,INPUT_PRICE, volume, weight, PRODUCE_DATE,EXPIRE_DATE,DESCRIPTION)  ");
        sqlStockGoods.append(" values  (SEQ_MJR_STOCK_GOODS.nextval,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),to_date(?,'dd/MM/yyyy hh24:mi:ss'),?,?,?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),to_date(?,'dd/MM/yyyy hh24:mi:ss'),?)  ");
        sqlStockGoods.append(" LOG ERRORS INTO ERR$_MJR_STOCK_GOODS_SERIAL REJECT LIMIT UNLIMITED ");
        //3. TAO PREPARE STATEMENT
        try {
            prstmtInsertStockTransDetail = connection.prepareStatement(sqlStockTransDetail.toString());
            prstmtInsertStockGoodsSerial = connection.prepareStatement(sqlStockGoodsSerial.toString());
            prstmtInsertStockGoods = connection.prepareStatement(sqlStockGoods.toString());
            //
            int count = 0;
            //
            for (MjrStockTransDetailDTO goods : lstGoodsDetail) {
                //
                count++;
                //DETAIL
                paramsStockTransDetail = setParamsStockTransDetail(mjrStockTransDTO, goods);
                //SET PARAMS AND ADD TO BATCH
                for (int idx = 0; idx < paramsStockTransDetail.size(); idx++) {
                    prstmtInsertStockTransDetail.setString(idx + 1, DataUtil.nvl(paramsStockTransDetail.get(idx), "").toString());
                }
                prstmtInsertStockTransDetail.addBatch();
                //
                if (Constants.IS_SERIAL.equalsIgnoreCase(goods.getIsSerial())) {
                    paramsStockGoodsSerial = setParamsStockGoodsSerial(mjrStockTransDTO, goods);
                    for (int idx = 0; idx < paramsStockGoodsSerial.size(); idx++) {
                        prstmtInsertStockGoodsSerial.setString(idx + 1, DataUtil.nvl(paramsStockGoodsSerial.get(idx), "").toString());
                    }
                    prstmtInsertStockGoodsSerial.addBatch();
                } else {
                    paramsStockGoods = setParamsStockGoods(mjrStockTransDTO, goods);
                    for (int idx = 0; idx < paramsStockGoods.size(); idx++) {
                        prstmtInsertStockGoods.setString(idx + 1, DataUtil.nvl(paramsStockGoods.get(idx), "").toString());
                    }
                    prstmtInsertStockGoods.addBatch();
                }
                //
                if (count >= Constants.COMMIT_NUMBER) {
                    try {
                        prstmtInsertStockTransDetail.executeBatch();
                        prstmtInsertStockGoodsSerial.executeBatch();
                        prstmtInsertStockGoods.executeBatch();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    count = 0;
                }
            }
            if (count > 0) {
                try {
                    prstmtInsertStockTransDetail.executeBatch();
                    prstmtInsertStockGoodsSerial.executeBatch();
                    prstmtInsertStockGoods.executeBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            log.info(e.toString());
            return Responses.ERROR.getName();
        } finally {
            //
            FunctionUtils.closeStatement(prstmtInsertStockTransDetail);
            FunctionUtils.closeStatement(prstmtInsertStockGoodsSerial);
            FunctionUtils.closeStatement(prstmtInsertStockGoods);
        }

        return Responses.SUCCESS.getName();
    }

    private List setParamsStockTransDetail(MjrStockTransDTO transDetail, MjrStockTransDetailDTO goods) {
        List<String> paramsStockTrans = Lists.newArrayList();
        paramsStockTrans.add(transDetail.getId());
        paramsStockTrans.add(goods.getGoodsId());
        paramsStockTrans.add(goods.getGoodsCode());
        paramsStockTrans.add(goods.getGoodsState());
        paramsStockTrans.add(goods.getIsSerial());
        paramsStockTrans.add(goods.getAmount());
        paramsStockTrans.add(goods.getSerial());
        paramsStockTrans.add(goods.getInputPrice());
        paramsStockTrans.add(goods.getCellCode() != null ? goods.getCellCode() : "");
        paramsStockTrans.add(goods.getTotalMoney());
        paramsStockTrans.add(goods.getUnitName());
        paramsStockTrans.add(goods.getVolume());
        paramsStockTrans.add(goods.getWeight());
        paramsStockTrans.add(goods.getProduceDate());
        paramsStockTrans.add(goods.getExpireDate());
        paramsStockTrans.add(goods.getDescription());
        return paramsStockTrans;
    }

    private List setParamsStockGoodsSerial(MjrStockTransDTO transDetail, MjrStockTransDetailDTO goods) {
        List<String> paramsStockTrans = Lists.newArrayList();
        paramsStockTrans.add(transDetail.getCustId());
        paramsStockTrans.add(transDetail.getStockId());
        paramsStockTrans.add(goods.getGoodsId());
        paramsStockTrans.add(goods.getGoodsState());
        paramsStockTrans.add(goods.getCellCode());
        paramsStockTrans.add(goods.getAmount());
        paramsStockTrans.add(goods.getSerial());
        paramsStockTrans.add(transDetail.getCreatedDate());
        paramsStockTrans.add(transDetail.getCreatedDate());
        paramsStockTrans.add(Constants.STATUS.ACTIVE);
        paramsStockTrans.add(transDetail.getPartnerId());
        paramsStockTrans.add(transDetail.getId());
        paramsStockTrans.add(goods.getInputPrice());
        if (!DataUtil.isNullOrEmpty(goods.getVolume())) {
            paramsStockTrans.add(String.valueOf(Math.round(1000000 * Double.valueOf(goods.getVolume()) / Double.valueOf(goods.getAmount())) / 1000000D));
        } else {
            paramsStockTrans.add(goods.getVolume());
        }
        if (!DataUtil.isNullOrEmpty(goods.getWeight())) {
            paramsStockTrans.add(String.valueOf(Math.round(1000000 * Double.valueOf(goods.getWeight()) / Double.valueOf(goods.getAmount())) / 1000000D));
        } else {
            paramsStockTrans.add(goods.getWeight());
        }
        paramsStockTrans.add(goods.getProduceDate());
        paramsStockTrans.add(goods.getExpireDate());
        paramsStockTrans.add(goods.getDescription());

        return paramsStockTrans;
    }

    private List setParamsStockGoods(MjrStockTransDTO transDetail, MjrStockTransDetailDTO goods) {
        List<String> paramsStockTrans = Lists.newArrayList();
        paramsStockTrans.add(transDetail.getCustId());
        paramsStockTrans.add(transDetail.getStockId());
        paramsStockTrans.add(goods.getGoodsId());
        paramsStockTrans.add(goods.getGoodsState());
        paramsStockTrans.add(goods.getCellCode());
        paramsStockTrans.add(goods.getAmount());
        paramsStockTrans.add(transDetail.getCreatedDate());
        paramsStockTrans.add(transDetail.getCreatedDate());
        paramsStockTrans.add(Constants.STATUS.ACTIVE);
        paramsStockTrans.add(transDetail.getPartnerId());
        paramsStockTrans.add(transDetail.getId());
        paramsStockTrans.add(goods.getInputPrice());
        if (!DataUtil.isNullOrEmpty(goods.getVolume())) {
            paramsStockTrans.add(String.valueOf(Math.round(1000000 * Double.valueOf(goods.getVolume()) / Double.valueOf(goods.getAmount())) / 1000000D));
        } else {
            paramsStockTrans.add(goods.getVolume());
        }
        if (!DataUtil.isNullOrEmpty(goods.getWeight())) {
            paramsStockTrans.add(String.valueOf(Math.round(1000000 * Double.valueOf(goods.getWeight()) / Double.valueOf(goods.getAmount())) / 1000000D));
        } else {
            paramsStockTrans.add(goods.getWeight());
        }
        paramsStockTrans.add(goods.getProduceDate());
        paramsStockTrans.add(goods.getExpireDate());
        paramsStockTrans.add(goods.getDescription());

        return paramsStockTrans;
    }


}
