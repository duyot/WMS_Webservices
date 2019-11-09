package com.wms.persistents.model;

import com.wms.base.BaseModel;
import com.wms.dto.MjrOrderDetailDTO;
import com.wms.dto.MjrStockTransDetailDTO;
import com.wms.utils.Constants;
import com.wms.utils.DateTimeUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by truongbx
 */
@Entity
@Table(name = "MJR_ORDER_DETAIL")
public class MjrOrderDetail extends BaseModel {
	private Long id;
	private Long orderId;
	private Long goodsId;
	private String goodsCode;
	private String goodsState;
	private Long isSerial;
	private Float amount;
	private String serial;
	private String unitName;
	private Long partnerId;
	private Float totalMoney;
	private Float volume;
	private Float weight;
	private String description;

	public MjrOrderDetail() {
	}

	public MjrOrderDetail(Long id, Long orderId, Long goodsId, String goodsCode, String goodsState, Long isSerial, Float amount, String serial, String unitName, Long partnerId, Float totalMoney, Float volume, Float weight, String description) {
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

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MJR_STOCK_TRANS_DETAIL")
	@SequenceGenerator(
			name = "SEQ_MJR_STOCK_TRANS_DETAIL",
			sequenceName = "SEQ_MJR_STOCK_TRANS_DETAIL",
			allocationSize = 1,
			initialValue = 1000
	)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ORDER_ID")
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}


	@Column(name = "GOODS_ID")
	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	@Column(name = "GOODS_CODE")
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	@Column(name = "GOODS_STATE")
	public String getGoodsState() {
		return goodsState;
	}

	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}

	@Column(name = "IS_SERIAL")
	public Long getIsSerial() {
		return isSerial;
	}

	public void setIsSerial(Long isSerial) {
		this.isSerial = isSerial;
	}

	@Column(name = "AMOUNT")
	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	@Column(name = "SERIAL")
	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	@Column(name = "UNIT_NAME")
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Column(name = "TOTAL_MONEY")
	public Float getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Float totalMoney) {
		this.totalMoney = totalMoney;
	}

	@Column(name = "PARTNER_ID")
	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	@Column(name = "VOLUME")
	public Float getVolume() {
		return volume;
	}

	public void setVolume(Float volume) {
		this.volume = volume;
	}

	@Column(name = "WEIGHT")
	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}


	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public MjrOrderDetailDTO toDTO() {
		return new MjrOrderDetailDTO(id == null ? "" : id + "", orderId == null ? "" : orderId + "", goodsId == null ? "" : goodsId + "",
				goodsCode, goodsState, isSerial == null ? "" : isSerial + "", amount == null ? "" : amount + "", serial,
				unitName, partnerId == null ? "" : partnerId + "", totalMoney == null ? "" : totalMoney + "",
				volume == null ? "" : volume + "", weight == null ? "" : weight + "",
				description
		);
	}
	@Transient
	public boolean isSerial() {
		if (isSerial == null || isSerial == 0){
			return false;
		}
		return true;
	}
}
