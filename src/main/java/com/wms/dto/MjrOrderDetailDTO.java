package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.persistents.model.MjrOrder;
import com.wms.persistents.model.MjrOrderDetail;
import com.wms.persistents.model.MjrStockTransDetail;
import com.wms.utils.Constants;
import com.wms.utils.DateTimeUtils;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 12/29/2016.
 */
public class MjrOrderDetailDTO extends BaseDTO {
	private String id;
	private String orderId;
	private String goodsId;
	private String goodsCode;
	private String goodsState;
	private String isSerial;
	private String amount;
	private String serial;
	private String unitName;
	private String partnerId;
	private String totalMoney;
	private String volume;
	private String weight;
	private String description;

	public MjrOrderDetailDTO() {
	}

	public MjrOrderDetailDTO(String id, String orderId, String goodsId, String goodsCode, String goodsState, String isSerial, String amount, String serial, String unitName, String partnerId, String totalMoney, String volume, String weight, String description) {
		this.id = id;
		this.orderId = orderId;
		this.goodsId = goodsId;
		this.goodsCode = goodsCode;
		this.goodsState = goodsState;
		this.isSerial = isSerial;
		this.amount = amount;
		this.serial = serial;
		this.unitName = unitName;
		this.partnerId = partnerId;
		this.totalMoney = totalMoney;
		this.volume = volume;
		this.weight = weight;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsState() {
		return goodsState;
	}

	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}

	public String getIsSerial() {
		return isSerial;
	}

	public void setIsSerial(String isSerial) {
		this.isSerial = isSerial;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public MjrOrderDetail toModel() {
		return new MjrOrderDetail(!StringUtils.validString(id) ? null : Long.valueOf(id),
				!StringUtils.validString(orderId) ? null : Long.valueOf(orderId),
				!StringUtils.validString(goodsId) ? null : Long.valueOf(goodsId), goodsCode, goodsState,
				!StringUtils.validString(isSerial) ? null : Long.valueOf(isSerial),
				!StringUtils.validString(amount) ? null : Float.valueOf(amount), serial,
				unitName,
				!StringUtils.validString(partnerId) ? null : Long.valueOf(partnerId),
				!StringUtils.validString(totalMoney) ? null : Float.valueOf(totalMoney),
				!StringUtils.validString(volume) ? null : Float.valueOf(volume),
				!StringUtils.validString(weight) ? null : Float.valueOf(weight), description
		);
	}


}