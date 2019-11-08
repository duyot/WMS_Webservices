package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.business.interfaces.MjrOrderBusinessInterface;
import com.wms.business.interfaces.MjrOrderDetailBusinessInterface;
import com.wms.dto.MjrOrderDTO;
import com.wms.dto.MjrOrderDetailDTO;
import com.wms.persistents.dao.MjrOrderDAO;
import com.wms.persistents.dao.MjrOrderDetailDAO;
import com.wms.persistents.model.MjrOrder;
import com.wms.persistents.model.MjrOrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by truongbx.
 */
@Service("mjrOrderDetailBusiness")
public class MjrOrderDetailBusinessImpl extends BaseBusinessImpl<MjrOrderDetailDTO, MjrOrderDetailDAO> implements MjrOrderDetailBusinessInterface {
    @Autowired
    MjrOrderDetailDAO mjrOrderDetailDAO;

    @PostConstruct
    public void setupService(){
        this.tdao = mjrOrderDetailDAO;
        this.entityClass = MjrOrderDetailDTO.class;
        this.mjrOrderDetailDAO.setModelClass(MjrOrderDetail.class);
        this.tDTO = new MjrOrderDetailDTO();
    }
}
