package com.wms.business.impl;

import com.google.common.collect.Lists;
import com.wms.base.BaseBusinessInterface;
import com.wms.business.interfaces.StatisticBusinessInterface;
import com.wms.dto.ChartDTO;
import com.wms.dto.Condition;
import com.wms.dto.SysMailReportDTO;
import com.wms.dto.SysStatisticTopGoodsDTO;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import com.wms.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by duyot on 5/18/2017.
 */
@Service("statisticBusiness")
public class StatisticBusinessImpl implements StatisticBusinessInterface {

    @Autowired
    BaseBusinessInterface sysMailReportBusiness;
    @Autowired
    BaseBusinessInterface sysStatisticTopGoodsBusiness;

    /*
        type: 7 weekly, 30: monthly
     */
    @Override
    public List<ChartDTO> getRevenue(String custId,int type) {
        List<SysMailReportDTO> lstSysMailReport;
        List<Condition> lstCon = Lists.newArrayList();
        lstCon.add(new Condition("custId", Constants.SQL_PRO_TYPE.LONG,Constants.SQL_OPERATOR.EQUAL,custId));

        Date date = new Date();
        String currentDate = DateTimeUtils.convertDateToString(date,"dd/MM/yyyy");
        String beforeDate;

        if (type == 7) {
            beforeDate = DateTimeUtils.convertDateToString(DateTimeUtils.getDateCompareToCurrent(-7),"dd/MM/yyyy");
        }else{
            beforeDate = DateTimeUtils.convertDateToString(DateTimeUtils.getFirstDateInMonth(),"dd/MM/yyyy");
        }
        lstCon.add(new Condition("createdDate", Constants.SQL_OPERATOR.BETWEEN,beforeDate +"|"+ currentDate));
        lstCon.add(new Condition("createdDate", Constants.SQL_OPERATOR.ORDER,"asc"));

        lstSysMailReport = sysMailReportBusiness.findByCondition(lstCon);
        if(DataUtil.isListNullOrEmpty(lstSysMailReport)){
            return Lists.newArrayList();
        }

        return getChartFromData(lstSysMailReport);
    }

    @Override
    public List<ChartDTO> getTopGoods(String custId, int type) {
        List<SysStatisticTopGoodsDTO> lstStatisticTopGoods;
        List<Condition> lstCon = Lists.newArrayList();
        lstCon.add(new Condition("custId", Constants.SQL_PRO_TYPE.LONG,Constants.SQL_OPERATOR.EQUAL,custId));

        lstCon.add(new Condition("type", Constants.SQL_OPERATOR.EQUAL,type+""));

        lstStatisticTopGoods = sysStatisticTopGoodsBusiness.findByCondition(lstCon);
        if(DataUtil.isListNullOrEmpty(lstStatisticTopGoods)){
            return Lists.newArrayList();
        }

        return getTopGoodsChartFromData(lstStatisticTopGoods.get(0));
    }

    private List<ChartDTO> getTopGoodsChartFromData(SysStatisticTopGoodsDTO lstSysMailReport){
//        List<ChartDTO> lstChart = Lists.newArrayList();
//        //
//        int size = lstSysMailReport.size();
//        int[] goodsInfo = new int[size];
//        String[] xAxisData = new String[size];
//        //
//
//        goodsInfo = getGoodsValueFromData(i.getStatisticInfo());
//        //
//
//
//        lstChart.add(new ChartDTO("",goodsInfo,xAxisData));

        return null;
    }

    private int[] getGoodsValueFromData(String data){

        if(!DataUtil.isStringNullOrEmpty(data)){
            return new int[0];
        }
        String [] goodsValue = data.split(",");
        int [] result = new int[goodsValue.length];
        for(int i = 0;i< goodsValue.length;i++){
            result[i] =  Integer.parseInt(goodsValue[i].split("|")[0]);
        }

        return result;
    }

    private List<ChartDTO> getChartFromData(List<SysMailReportDTO> lstSysMailReport){
        List<ChartDTO> lstChart = Lists.newArrayList();
        //
        int size = lstSysMailReport.size();
        int[] dataExport = new int[size];
        int[] dataTotal  = new int[size];
        //
        int count = 0;
        for (SysMailReportDTO i: lstSysMailReport)
        {
            dataExport[count] = (DataUtil.parseIntWithEmptyValue(i.getTotalExportMoney()));
            dataTotal[count]  = (DataUtil.parseIntWithEmptyValue(i.getTotalMoney()));
            //
            count ++;
        }

        lstChart.add(new ChartDTO("Doanh thu",dataExport));
        lstChart.add(new ChartDTO("Lợi nhuận",dataTotal));

        return lstChart;
    }
}
