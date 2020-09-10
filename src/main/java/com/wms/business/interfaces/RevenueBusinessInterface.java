package com.wms.business.interfaces;

import com.wms.base.BaseBusinessInterface;
import com.wms.dto.RevenueDTO;

import java.util.List;

public interface RevenueBusinessInterface extends BaseBusinessInterface<RevenueDTO> {
    List<RevenueDTO> getSumRevenue(String custId, String partnerId, String startDate, String endDate);

}
