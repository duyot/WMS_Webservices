package com.wms.business.interfaces;

import com.wms.dto.ChartDTO;

import java.util.List;

/**
 * Created by duyot on 5/18/2017.
 */
public interface StatisticBusinessInterface {
    List<ChartDTO> getRevenue(String custId, int type);
    List<ChartDTO> getTopGoods(String custId, int type);
    List<ChartDTO> getKPIStorage(String custId, int type);
    List<ChartDTO> getTransaction(String custId, int type);
}
