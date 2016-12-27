package com.wms.business.interfaces;

import com.wms.dto.MjrStockGoodsTotalDTO;

/**
 * Created by duyot on 12/19/2016.
 */
public interface StockManagermentBusinessInterface {
    public String importStock(MjrStockGoodsTotalDTO stockGoodsTotal);
    public String exportStock(MjrStockGoodsTotalDTO stockGoodsTotal);
}
