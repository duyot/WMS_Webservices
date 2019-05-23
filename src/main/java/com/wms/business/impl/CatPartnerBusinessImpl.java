package com.wms.business.impl;

/**
 * Created by doanlv4 on 2/17/2017.
 */

import com.wms.base.BaseBusinessImpl;
import com.wms.business.interfaces.CatPartnerBusinessInterface;
import com.wms.dto.CatPartnerDTO;
import com.wms.dto.CatStockDTO;
import com.wms.persistents.dao.CatPartnerDAO;
import com.wms.persistents.model.CatPartner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service("catPartnerBusiness")
public class CatPartnerBusinessImpl extends BaseBusinessImpl<CatPartnerDTO, CatPartnerDAO> implements CatPartnerBusinessInterface {
    @Autowired
    CatPartnerDAO catPartnerDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = catPartnerDAO;
        this.entityClass = CatPartnerDTO.class;
        this.catPartnerDAO.setModelClass(CatPartner.class);
        this.tDTO = new CatPartnerDTO();
    }

    @Override
    public List<CatPartnerDTO> getPartnerByUser(Long userId, Long partnerPermission) {
        return catPartnerDAO.getPartnerByUser(userId,partnerPermission);
    }
}
