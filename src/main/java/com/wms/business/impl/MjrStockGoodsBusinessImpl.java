package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.MjrStockGoodsDTO;
import com.wms.persistents.dao.MjrStockGoodsDAO;
import com.wms.persistents.model.MjrStockGoods;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duyot on 1/3/2017.
 */
@Service("mjrStockGoodsBusiness")
public class MjrStockGoodsBusinessImpl extends BaseBusinessImpl<MjrStockGoodsDTO, MjrStockGoodsDAO> {
    @Autowired
    MjrStockGoodsDAO mjrStockGoodsDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = mjrStockGoodsDAO;
        this.entityClass = MjrStockGoodsDTO.class;
        this.mjrStockGoodsDAO.setModelClass(MjrStockGoods.class);
        this.tDTO = new MjrStockGoodsDTO();
    }
}
