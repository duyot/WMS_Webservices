package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.MjrStockGoodsTotalDTO;
import com.wms.utils.DateTimeUtils;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by duyot on 12/19/2016.
 */
@Entity
@Table(name = "MJR_STOCK_GOODS_TOTAL")
public class MjrStockGoodsTotal extends BaseModel {
    private Long id;
    private Long custId;
    private String custName;
    private Long goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsState;
    private Long stockId;
    private String stockCode;
    private Double amount;
    private Date changeDate;

    public MjrStockGoodsTotal(Long id, Long custId, String custName, Long goodsId, String goodsCode, String goodsName, String goodsState, Long stockId, String stockCode, Double amount, Date changeDate) {
        this.id = id;
        this.custId = custId;
        this.custName = custName;
        this.goodsId = goodsId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsState = goodsState;
        this.stockId = stockId;
        this.stockCode = stockCode;
        this.amount = amount;
        this.changeDate = changeDate;
    }

    public MjrStockGoodsTotal() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_MJR_STOCK_GOODS_TOTAL")
    @SequenceGenerator(
            name="SEQ_MJR_STOCK_GOODS_TOTAL",
            sequenceName="SEQ_MJR_STOCK_GOODS_TOTAL",
            allocationSize = 1,
            initialValue= 1000
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CUST_ID")
    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    @Column(name = "CUST_NAME")
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
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

    @Column(name = "GOODS_NAME")
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Column(name = "GOODS_STATE")
    public String getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    @Column(name = "STOCK_ID")
    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    @Column(name = "STOCK_CODE")
    public String getStockCode() {
        return stockCode;
    }


    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    @Column(name = "AMOUNT")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Column(name = "CHANGE_DATE")
    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    @Override
    public MjrStockGoodsTotalDTO toDTO() {
        return new MjrStockGoodsTotalDTO(id==null?"":id+"",custId==null?"":custId+"",custName,goodsId==null?"":goodsId+"",
                goodsCode,goodsName,goodsState,stockId==null?"":stockId+"",stockCode,amount==null?"":amount+"",changeDate==null?"": DateTimeUtils.convertDateTimeToString(changeDate)
                );
    }

    @Override
    public String toString() {
        return "MjrStockGoodsTotal{" +
                "id=" + id +
                ", custId=" + custId +
                ", custName='" + custName + '\'' +
                ", goodsId=" + goodsId +
                ", goodsCode='" + goodsCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsState='" + goodsState + '\'' +
                ", stockId=" + stockId +
                ", stockCode='" + stockCode + '\'' +
                ", amount=" + amount +
                ", changeDate=" + changeDate +
                '}';
    }
}
