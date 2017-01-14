package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.MjrStockGoodsSerialDTO;
import com.wms.persistents.dao.MjrStockGoodsSerialDAO;
import com.wms.persistents.model.MjrStockGoodsSerial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

/**
 * Created by duyot on 1/3/2017.
 */
@Service("mjrStockGoodsSerialBusiness")
public class MjrStockGoodsSerialBusinessImpl extends BaseBusinessImpl<MjrStockGoodsSerialDTO, MjrStockGoodsSerialDAO> {
    @Autowired
    MjrStockGoodsSerialDAO mjrStockGoodsSerialDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = mjrStockGoodsSerialDAO;
        this.entityClass = MjrStockGoodsSerialDTO.class;
        this.mjrStockGoodsSerialDAO.setModelClass(MjrStockGoodsSerial.class);
        this.tDTO = new MjrStockGoodsSerialDTO();
    }
}
