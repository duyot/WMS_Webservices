package com.wms.business.interfaces;

import com.wms.dto.CatStockDTO;

import java.util.List;

public interface CatStockBusinessInterface {
    List<CatStockDTO> getStockByUser(Long userId);
}
