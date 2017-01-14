package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.MjrStockGoodsTotalDTO;
import com.wms.dto.MjrStockTransDTO;
import com.wms.persistents.dao.MjrStockGoodsTotalDAO;
import com.wms.persistents.dao.MjrStockTransDAO;
import com.wms.persistents.model.MjrStockGoodsTotal;
import com.wms.persistents.model.MjrStockTrans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 12/28/2016.
 */
@Service("mjrStockTransBusiness")
public class MjrStockTransBusinessImpl extends BaseBusinessImpl<MjrStockTransDTO, MjrStockTransDAO> {
    @Autowired
    MjrStockTransDAO mjrStockTransDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = mjrStockTransDAO;
        this.entityClass = MjrStockTransDTO.class;
        this.mjrStockTransDAO.setModelClass(MjrStockTrans.class);
        this.tDTO = new MjrStockTransDTO();
    }

}
