package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.business.interfaces.SysMenuBusinessInterface;
import com.wms.dto.MapUserStockDTO;
import com.wms.dto.SysMenuDTO;
import com.wms.persistents.dao.MapUserStockDAO;
import com.wms.persistents.dao.SysMenuDAO;
import com.wms.persistents.model.MapUserStock;
import com.wms.persistents.model.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service("mapUserStockImpl")
public class MapUserStockImpl extends BaseBusinessImpl<MapUserStockDTO, MapUserStockDAO>  {
    @Autowired
    MapUserStockDAO mapUserStockDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = mapUserStockDAO;
        this.entityClass = MapUserStockDTO.class;
        this.mapUserStockDAO.setModelClass(MapUserStock.class);
        this.tDTO = new MapUserStockDTO();
    }
}

