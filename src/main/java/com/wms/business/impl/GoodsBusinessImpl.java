package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.CatGoodsGroupDTO;
import com.wms.dto.GoodsDTO;
import com.wms.persistents.dao.CatGoodsGroupDAO;
import com.wms.persistents.dao.GoodsDAO;
import com.wms.persistents.model.CatGoodsGroup;
import com.wms.persistents.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 12/9/2016.
 */
@Service("goodsBusiness")
public class GoodsBusinessImpl extends BaseBusinessImpl<GoodsDTO, GoodsDAO> {
    @Autowired
    GoodsDAO goodsDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = goodsDAO;
        this.entityClass = GoodsDTO.class;
        this.goodsDAO.setModelClass(Goods.class);
        this.tDTO = new GoodsDTO();
    }
}
