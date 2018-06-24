package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.business.interfaces.CatStockBusinessInterface;
import com.wms.dto.CatStockDTO;
import com.wms.persistents.dao.CatStockDAO;
import com.wms.persistents.model.CatStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.List;

@Service("catStockBusiness")
public class CatStockBusinessImpl extends BaseBusinessImpl<CatStockDTO, CatStockDAO> implements CatStockBusinessInterface {
    @Autowired
    CatStockDAO catStockDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = catStockDAO;
        this.entityClass = CatStockDTO.class;
        this.catStockDAO.setModelClass(CatStock.class);
        this.tDTO = new CatStockDTO();
    }

    @Override
    public List<CatStockDTO> getStockByUser(Long userId) {
        return catStockDAO.getStockByUser(userId);
    }
}
