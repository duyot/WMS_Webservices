package com.wms.business.impl;

import com.wms.base.BaseBusinessImpl;
import com.wms.base.BaseBusinessInterface;
import com.wms.business.interfaces.MjrOrderBusinessInterface;
import com.wms.business.interfaces.StockFunctionInterface;
import com.wms.dto.*;
import com.wms.enums.Responses;
import com.wms.persistents.dao.MjrOrderDAO;
import com.wms.persistents.dao.MjrOrderDetailDAO;
import com.wms.persistents.dao.SysRoleMenuDAO;
import com.wms.persistents.model.MjrOrder;
import com.wms.persistents.model.MjrOrderDetail;
import com.wms.persistents.model.SysRoleMenu;
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
		mjrOrder.setCode(initTransCode(mjrOrder,getSysDate(),session,null));
		List<MjrOrderDetail> mjrOrderDetails = new ArrayList<>();
		try {
			String id = mjrOrderDAO.saveBySession(mjrOrder.toModel(),session);

			lstMjrOrderDetails.forEach(e -> {
				MjrOrderDetail mjrOrderDetail = e.toModel();
//				mjrOrderDetail.setOrderId(Long.parseLong(id));
				mjrOrderDetails.add(mjrOrderDetail);
			});

			mjrOrderDetailDAO.saveBySession(mjrOrderDetails,session);
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
			stringBuilder.append("PNK");
		} else {
			stringBuilder.append("PXK");
		}
		stringBuilder.append("/").append(stockCode)
				.append("/")
				.append(createdTime)
				.append("/");
		if (isImport) {
			stringBuilder.append(stockFunctionBusiness.getTotalStockTransaction(mjrOrderDTO.getStockId(), con));
		} else {
			stringBuilder.append(stockFunctionBusiness.getTotalStockTransaction(mjrOrderDTO.getStockId(), session));
		}
		return stringBuilder.toString();
	}
}
