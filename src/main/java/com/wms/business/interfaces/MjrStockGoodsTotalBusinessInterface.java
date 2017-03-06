package com.wms.business.interfaces;

import com.wms.dto.Condition;
import com.wms.dto.MjrStockGoodsTotalDTO;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by duyot on 12/19/2016.
 */
public interface MjrStockGoodsTotalBusinessInterface {
    public List<MjrStockGoodsTotalDTO> findByCondition(List<Condition> lstCondition,Session session);
}
