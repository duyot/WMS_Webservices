package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.SysStatisticTopGoodsDTO;
import com.wms.persistents.dao.SysStatisticTopGoodsDAO;
import com.wms.persistents.model.SysStatisticTopGoods;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duyot on 5/19/2017.
 */
@Service("sysStatisticTopGoodsBusiness")
public class SysStatisticTopGoodsBusinessImpl extends BaseBusinessImpl<SysStatisticTopGoodsDTO, SysStatisticTopGoodsDAO> {
    @Autowired
    SysStatisticTopGoodsDAO sysStatisticTopGoodsDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = sysStatisticTopGoodsDAO;
        this.entityClass = SysStatisticTopGoodsDTO.class;
        this.sysStatisticTopGoodsDAO.setModelClass(SysStatisticTopGoods.class);
        this.tDTO = new SysStatisticTopGoodsDTO();
    }
}
