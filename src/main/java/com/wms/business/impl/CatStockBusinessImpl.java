package com.wms.business.impl;

<<<<<<< HEAD
/**
 * Created by doanlv4 on 2/17/2017.
 */

=======
>>>>>>> f97d1f5817603b7b5087e101d6f6579d2945e4eb
import com.wms.base.BaseBusinessImpl;
import com.wms.dto.CatStockDTO;
import com.wms.persistents.dao.CatStockDAO;
import com.wms.persistents.model.CatStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
<<<<<<< HEAD
=======

/**
 * Created by duyot on 2/17/2017.
 */
>>>>>>> f97d1f5817603b7b5087e101d6f6579d2945e4eb
@Service("catStockBusiness")
public class CatStockBusinessImpl extends BaseBusinessImpl<CatStockDTO, CatStockDAO> {
    @Autowired
    CatStockDAO catStockDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = catStockDAO;
        this.entityClass = CatStockDTO.class;
        this.catStockDAO.setModelClass(CatStock.class);
        this.tDTO = new CatStockDTO();
    }
}
