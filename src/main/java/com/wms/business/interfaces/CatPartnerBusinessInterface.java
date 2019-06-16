package com.wms.business.interfaces;

import com.wms.dto.CatPartnerDTO;

import java.util.List;

public interface CatPartnerBusinessInterface {
    List<CatPartnerDTO> getPartnerByUser(Long userId, Long partnerPermission);
}
