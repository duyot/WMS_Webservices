package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.persistents.model.MjrOrder;
import com.wms.persistents.model.MjrOrderDetail;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

import java.util.List;

/**
 * Created by truongbx
 */


public class OrderExportDTO {
	MjrOrderDTO mjrOrderDTO;
	List<MjrOrderDetailDTO> lstMjrOrderDetailDTOS;

	public MjrOrderDTO getMjrOrderDTO() {
		return mjrOrderDTO;
	}

	public void setMjrOrderDTO(MjrOrderDTO mjrOrderDTO) {
		this.mjrOrderDTO = mjrOrderDTO;
	}

	public List<MjrOrderDetailDTO> getLstMjrOrderDetailDTOS() {
		return lstMjrOrderDetailDTOS;
	}

	public void setLstMjrOrderDetailDTOS(List<MjrOrderDetailDTO> lstMjrOrderDetailDTOS) {
		this.lstMjrOrderDetailDTOS = lstMjrOrderDetailDTOS;
	}
}
