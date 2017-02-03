package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.MapUserCustomerDTO;
import com.wms.persistents.dao.MapUserCustomerDAO;
import com.wms.persistents.model.MapUserCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by duyot on 1/24/2017.
 */
@Service("mapUserCustomerBusiness")
public class MapUserCustomerBusinessImpl extends BaseBusinessImpl<MapUserCustomerDTO, MapUserCustomerDAO> {
    @Autowired
    MapUserCustomerDAO mapUserCustomerDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = mapUserCustomerDAO;
        this.entityClass = MapUserCustomerDTO.class;
        this.mapUserCustomerDAO.setModelClass(MapUserCustomer.class);
        this.tDTO = new MapUserCustomerDTO();
    }
}
