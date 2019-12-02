package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.base.BaseBusinessInterface;
import com.wms.business.interfaces.MjrOrderBusinessInterface;
import com.wms.business.interfaces.StockFunctionInterface;
import com.wms.dto.*;
import com.wms.enums.Responses;
import com.wms.persistents.dao.*;
import com.wms.persistents.model.*;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import com.wms.utils.FunctionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by truongbx.
 */
@Service("mjrOrderBusiness")
public class MjrOrderBusinessImpl extends BaseBusinessImpl<MjrOrderDTO, MjrOrderDAO> implements MjrOrderBusinessInterface {
	@Autowired
	MjrOrderDAO mjrOrderDAO;

	@Autowired
	MjrStockGoodsDAO mjrStockGoodsDAO;

	@Autowired
	MjrStockGoodsSerialDAO mjrStockGoodsSerialDAO;
	@Autowired
	MjrOrderDetailDAO mjrOrderDetailDAO;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	StockFunctionInterface stockFunctionBusiness;

	@Autowired
	BaseBusinessInterface catStockBusiness;

	@PostConstruct
	public void setupService() {
		this.tdao = mjrOrderDAO;
		this.entityClass = MjrOrderDTO.class;
		this.mjrOrderDAO.setModelClass(MjrOrder.class);
		this.tDTO = new MjrOrderDTO();
	}

	@Override
	public ResponseObject orderExport(MjrOrderDTO mjrOrder, List<MjrOrderDetailDTO> lstMjrOrderDetails) {
		ResponseObject responseObject = new ResponseObject();
		responseObject.setStatusName(Responses.SUCCESS.getName());
		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		List<MjrOrderDetail> mjrOrderDetails = new ArrayList<>();
		try {
			String id = null;
			if (!DataUtil.isNullOrEmpty(mjrOrder.getId())) {
				id = mjrOrder.getId();
				mjrOrderDAO.updateBySession(mjrOrder.toModel(), session);
				List<MjrOrderDetail> mjrOrderDetails1 = mjrOrderDetailDAO.findByProperty("orderId", Long.parseLong(mjrOrder.getId()));
				for (MjrOrderDetail mjrOrderDetail : mjrOrderDetails1) {
					mjrOrderDetailDAO.deleteByObjectSession(mjrOrderDetail, session);
				}
			} else {
				String createdDatePartition = getSysDate("ddMMyyyy");
				mjrOrder.setCode(initTransCode(mjrOrder, createdDatePartition, session, null));
				id = mjrOrderDAO.saveBySession(mjrOrder.toModel(), session);
			}


			for (MjrOrderDetailDTO mjrOrderDetailDTO : lstMjrOrderDetails) {
				MjrOrderDetail mjrOrderDetail = mjrOrderDetailDTO.toModel();
				mjrOrderDetail.setOrderId(Long.parseLong(id));
				mjrOrderDetails.add(mjrOrderDetail);
			}

			mjrOrderDetailDAO.saveBySession(mjrOrderDetails, session);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			responseObject.setStatusName(Responses.ERROR.getName());
		} finally {
			session.close();
		}
		return responseObject;
	}

	@Override
	public ResponseObject deleteOrder(Long orderId) {
		ResponseObject responseObject = new ResponseObject();
		responseObject.setStatusName(Responses.SUCCESS.getName());
		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			mjrOrderDAO.deleteByIdSession(orderId, session);
			List<MjrOrderDetail> mjrOrderDetails1 = mjrOrderDetailDAO.findByProperty("orderId", orderId);
			for (MjrOrderDetail mjrOrderDetail : mjrOrderDetails1) {
				mjrOrderDetailDAO.deleteByObjectSession(mjrOrderDetail, session);
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			responseObject.setStatusName(Responses.ERROR.getName());
			responseObject.setKey("FAIL");
		} finally {
			session.close();
		}
		return responseObject;
	}

	//
	private String initTransCode(MjrOrderDTO mjrOrderDTO, String createdTime, Session session, Connection con) {
		//
		boolean isImport = Constants.TRANSACTION_TYPE.IMPORT.equalsIgnoreCase(mjrOrderDTO.getType());
		//
		String stockCode = "";
		CatStockDTO stock = (CatStockDTO) catStockBusiness.findById(Long.parseLong(mjrOrderDTO.getStockId()));
		if (stock != null) {
			stockCode = stock.getCode();
		}
		//
		StringBuilder stringBuilder = new StringBuilder();
		if (isImport) {
			stringBuilder.append("YCNK");
		} else {
			stringBuilder.append("YCXK");
		}
		stringBuilder.append("/").append(stockCode)
				.append("/")
				.append(createdTime)
				.append("/");
		stringBuilder.append(mjrOrderDAO.getTotalOrder(mjrOrderDTO.getStockId(), Integer.parseInt(mjrOrderDTO.getType())));
		return stringBuilder.toString();
	}

	@Override
	public List<RealExportExcelDTO> orderExportData(Long mjrOrderId) {
		List<RealExportExcelDTO> realExportExcelDTOS = new ArrayList<>();
		MjrOrder mjrOrder = mjrOrderDAO.findById(mjrOrderId);
		List<MjrOrderDetail> mjrOrderDetail = mjrOrderDetailDAO.findByProperty("orderId", mjrOrderId);
		for (MjrOrderDetail detail : mjrOrderDetail) {
			if (detail.isSerial()) {
				List<MjrStockGoodsSerialDTO> mjrStockGoodsSerials = mjrStockGoodsSerialDAO.exportOrderStockGoodsSerial(mjrOrder.toDTO(), detail.toDTO());
				realExportExcelDTOS.addAll(convertStockGoodsSerialToExcelData(mjrStockGoodsSerials, detail));
			} else {
				List<MjrStockGoodsDTO> mjrStockGoods = mjrStockGoodsDAO.exportOrderStockGoods(mjrOrder.toDTO(), detail.toDTO());
				realExportExcelDTOS.addAll(convertStockGoodsToExcelData(mjrStockGoods, detail));
			}
		}
		return realExportExcelDTOS;
	}

	@Override
	public List<MjrOrderDetailDTO> getListOrderDetail(Long orderId) {
		List<MjrOrderDetail> lstData = mjrOrderDetailDAO.findByProperty("orderId", orderId);
		List<MjrOrderDetailDTO> data = new ArrayList<>();
		lstData.forEach(e -> {
			data.add(e.toDTO());
		});
		return data;
	}

	public List<RealExportExcelDTO> convertStockGoodsToExcelData(List<MjrStockGoodsDTO> mjrStockGoods, MjrOrderDetail mjrOrderDetail) {
		List<RealExportExcelDTO> realExportExcelDTOS = new ArrayList<>();
		for (MjrStockGoodsDTO mjrStockGoods1 : mjrStockGoods) {
			RealExportExcelDTO realExportExcelDTO = new RealExportExcelDTO();
			realExportExcelDTO.setUnitName(mjrOrderDetail.getUnitName());
			realExportExcelDTO.setGoodsCode(mjrOrderDetail.getGoodsCode());
			realExportExcelDTO.setGoodsState(mjrOrderDetail.getGoodsState());
			realExportExcelDTO.setAmount(mjrStockGoods1.getAmount());
			realExportExcelDTO.setWeight(mjrStockGoods1.getWeight());
			realExportExcelDTO.setVolume(mjrStockGoods1.getVolume());
			realExportExcelDTO.setCellCode(mjrStockGoods1.getCellCode());
			realExportExcelDTO.setDescription(mjrStockGoods1.getDescription());
			realExportExcelDTOS.add(realExportExcelDTO);
		}

		return realExportExcelDTOS;
	}

	public List<RealExportExcelDTO> convertStockGoodsSerialToExcelData(List<MjrStockGoodsSerialDTO> mjrStockGoodsSerials, MjrOrderDetail mjrOrderDetail) {
		List<RealExportExcelDTO> realExportExcelDTOS = new ArrayList<>();
		for (MjrStockGoodsSerialDTO mjrStockGoodsSerial : mjrStockGoodsSerials) {
			RealExportExcelDTO realExportExcelDTO = new RealExportExcelDTO();
			realExportExcelDTO.setUnitName(mjrOrderDetail.getUnitName());
			realExportExcelDTO.setGoodsCode(mjrOrderDetail.getGoodsCode());
			realExportExcelDTO.setGoodsState(mjrOrderDetail.getGoodsState());
			realExportExcelDTO.setAmount(mjrStockGoodsSerial.getAmount());
			realExportExcelDTO.setWeight(mjrStockGoodsSerial.getWeight());
			realExportExcelDTO.setVolume(mjrStockGoodsSerial.getVolume());
			realExportExcelDTO.setCellCode(mjrStockGoodsSerial.getCellCode());
			realExportExcelDTO.setDescription(mjrStockGoodsSerial.getDescription());
			realExportExcelDTOS.add(realExportExcelDTO);
		}
		return realExportExcelDTOS;
	}
}
