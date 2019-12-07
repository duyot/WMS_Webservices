package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.CatGoodsGroupDTO;
import com.wms.persistents.dao.CatGoodsGroupDAO;
import com.wms.persistents.model.CatGoodsGroup;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duyot on 12/7/2016.
 */
@Service("catGoodsGroupBusiness")
public class CatGoodsGroupBusinessImpl extends BaseBusinessImpl<CatGoodsGroupDTO, CatGoodsGroupDAO> {
    @Autowired
    CatGoodsGroupDAO catGoodsGroupDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = catGoodsGroupDAO;
        this.entityClass = CatGoodsGroupDTO.class;
        this.catGoodsGroupDAO.setModelClass(CatGoodsGroup.class);
        this.tDTO = new CatGoodsGroupDTO();
    }
}
