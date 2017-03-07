package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.Err$MjrStockGoodsSerialDTO;
import com.wms.persistents.dao.Err$MjrStockGoodsSerialDAO;
import com.wms.persistents.model.Err$MjrStockGoodsSerial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 3/6/2017.
 */
@Service("err$MjrStockGoodsSerialBusiness")
public class Err$MjrStockGoodsSerialImpl extends BaseBusinessImpl<Err$MjrStockGoodsSerialDTO, Err$MjrStockGoodsSerialDAO> {
    @Autowired
    Err$MjrStockGoodsSerialDAO err$MjrStockGoodsSerialDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = err$MjrStockGoodsSerialDAO;
        this.entityClass = Err$MjrStockGoodsSerialDTO.class;
        this.err$MjrStockGoodsSerialDAO.setModelClass(Err$MjrStockGoodsSerial.class);
        this.tDTO = new Err$MjrStockGoodsSerialDTO();
    }
}
