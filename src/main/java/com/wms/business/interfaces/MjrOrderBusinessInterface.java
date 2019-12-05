package com.wms.business.interfaces;

import com.wms.base.BaseBusinessInterface;
import com.wms.dto.MjrOrderDTO;
import com.wms.dto.MjrOrderDetailDTO;
import com.wms.dto.RealExportExcelDTO;
import com.wms.dto.ResponseObject;
import java.util.List;

public interface MjrOrderBusinessInterface extends BaseBusinessInterface<MjrOrderDTO> {
    ResponseObject orderExport(MjrOrderDTO mjrOrder, List<MjrOrderDetailDTO> lstMjrOrderDetails);

    List<RealExportExcelDTO> orderExportData(Long mjrOrderId);

    List<MjrOrderDetailDTO> getListOrderDetail(Long orderId);

    ResponseObject deleteOrder(Long orderId);
}
