package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.CatStockCellDTO;
import com.wms.persistents.dao.CatStockCellDAO;
import com.wms.persistents.model.CatStockCell;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duyot on 4/19/2017.
 */
@Service("catStockCellBusiness")
public class CatStockCellBusinessImpl extends BaseBusinessImpl<CatStockCellDTO, CatStockCellDAO> {
    @Autowired
    CatStockCellDAO catStockCellDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = catStockCellDAO;
        this.entityClass = CatStockCellDTO.class;
        this.catStockCellDAO.setModelClass(CatStockCell.class);
        this.tDTO = new CatStockCellDTO();
    }
}
