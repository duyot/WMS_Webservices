package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.dto.MapUserStockDTO;
import com.wms.persistents.dao.MapUserStockDAO;
import com.wms.persistents.model.MapUserStock;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("mapUserStockImpl")
public class MapUserStockImpl extends BaseBusinessImpl<MapUserStockDTO, MapUserStockDAO> {
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

