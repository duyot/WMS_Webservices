package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.CatGoodsDTO;
import com.wms.persistents.dao.CatGoodsDAO;
import com.wms.persistents.model.CatGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 12/9/2016.
 */
@Service("catGoodsBusiness")
public class CatGoodsBusinessImpl extends BaseBusinessImpl<CatGoodsDTO, CatGoodsDAO> {
    @Autowired
    CatGoodsDAO goodsDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = goodsDAO;
        this.entityClass = CatGoodsDTO.class;
        this.goodsDAO.setModelClass(CatGoods.class);
        this.tDTO = new CatGoodsDTO();
    }
}
