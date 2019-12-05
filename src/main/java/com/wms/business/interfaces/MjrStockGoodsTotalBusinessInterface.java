package com.wms.business.interfaces;

import com.wms.dto.CatGoodsDTO;
import com.wms.dto.Condition;
import com.wms.dto.MjrStockGoodsTotalDTO;
import java.util.List;
import org.hibernate.Session;

/**
 * Created by duyot on 12/19/2016.
 */
public interface MjrStockGoodsTotalBusinessInterface {
    List<MjrStockGoodsTotalDTO> findByCondition(List<Condition> lstCondition, Session session);

    int updateGoods(CatGoodsDTO goods);

    List<MjrStockGoodsTotalDTO> findMoreCondition(MjrStockGoodsTotalDTO searchGoodsTotalDTO);
}
