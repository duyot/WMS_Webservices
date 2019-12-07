package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.business.interfaces.MjrOrderDetailBusinessInterface;
import com.wms.dto.MjrOrderDetailDTO;
import com.wms.persistents.dao.MjrOrderDetailDAO;
import com.wms.persistents.model.MjrOrderDetail;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by truongbx.
 */
@Service("mjrOrderDetailBusiness")
public class MjrOrderDetailBusinessImpl extends BaseBusinessImpl<MjrOrderDetailDTO, MjrOrderDetailDAO> implements MjrOrderDetailBusinessInterface {
    @Autowired
    MjrOrderDetailDAO mjrOrderDetailDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = mjrOrderDetailDAO;
        this.entityClass = MjrOrderDetailDTO.class;
        this.mjrOrderDetailDAO.setModelClass(MjrOrderDetail.class);
        this.tDTO = new MjrOrderDetailDTO();
    }
}
