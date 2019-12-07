package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.MjrStockTransDetailDTO;
import com.wms.persistents.dao.MjrStockTransDetailDAO;
import com.wms.persistents.model.MjrStockTransDetail;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duyot on 12/29/2016.
 */
@Service("mjrStockTransDetailBusiness")
public class MjrStockTransDetailBusinessImpl extends BaseBusinessImpl<MjrStockTransDetailDTO, MjrStockTransDetailDAO> {
    @Autowired
    MjrStockTransDetailDAO mjrStockTransDetailDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = mjrStockTransDetailDAO;
        this.entityClass = MjrStockTransDetailDTO.class;
        this.mjrStockTransDetailDAO.setModelClass(MjrStockTransDetail.class);
        this.tDTO = new MjrStockTransDetailDTO();
    }
}
