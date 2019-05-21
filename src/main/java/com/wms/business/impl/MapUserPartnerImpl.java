package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.business.interfaces.SysMenuBusinessInterface;
import com.wms.dto.MapUserPartnerDTO;
import com.wms.dto.SysMenuDTO;
import com.wms.persistents.dao.MapUserPartnerDAO;
import com.wms.persistents.dao.SysMenuDAO;
import com.wms.persistents.model.MapUserPartner;
import com.wms.persistents.model.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service("mapUserPartnerImpl")
public class MapUserPartnerImpl extends BaseBusinessImpl<MapUserPartnerDTO, MapUserPartnerDAO>  {
    @Autowired
    MapUserPartnerDAO mapUserPartnerDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = mapUserPartnerDAO;
        this.entityClass = MapUserPartnerDTO.class;
        this.mapUserPartnerDAO.setModelClass(MapUserPartner.class);
        this.tDTO = new MapUserPartnerDTO();
    }
}

