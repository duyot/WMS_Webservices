package com.wms.business.interfaces;

import com.wms.base.BaseBusinessInterface;
import com.wms.dto.*;
import com.wms.persistents.model.MjrOrder;
import com.wms.persistents.model.MjrOrderDetail;

import java.util.List;

public interface MjrOrderBusinessInterface extends BaseBusinessInterface<MjrOrderDTO> {
	public ResponseObject orderExport(MjrOrderDTO mjrOrder, List<MjrOrderDetailDTO> lstMjrOrderDetails);
	public List<RealExportExcelDTO>  orderExportData(Long mjrOrderId );
	public List<MjrOrderDetailDTO>  getListOrderDetail(Long orderId );
}
