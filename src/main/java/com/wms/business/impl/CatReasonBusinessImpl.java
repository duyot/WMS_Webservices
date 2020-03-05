package com.wms.business.impl;

/**
 * Created by doanlv4 on 2/17/2017.
 */

import com.wms.base.BaseBusinessImpl;
import com.wms.business.interfaces.CatPartnerBusinessInterface;
import com.wms.dto.CatReasonDTO;
import com.wms.persistents.dao.CatReasonDAO;
import com.wms.persistents.model.CatReason;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("catReasonBusiness")
public class CatReasonBusinessImpl extends BaseBusinessImpl<CatReasonDTO, CatReasonDAO> {
    @Autowired
    CatReasonDAO catReasonDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = catReasonDAO;
        this.entityClass = CatReasonDTO.class;
        this.catReasonDAO.setModelClass(CatReason.class);
        this.tDTO = new CatReasonDTO();
    }

}
