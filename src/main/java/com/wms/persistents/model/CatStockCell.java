package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.CatStockCellDTO;
import javax.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Created by duyot on 4/19/2017.
 */
@Entity
@DynamicUpdate
@Table(name = "CAT_STOCK_CELL")
public class CatStockCell extends BaseModel {
    private Long id;
    private String code;
    private Long stockId;
    private Double maxWeight;
    private Double maxVolume;
    private Long manyCodes;

    public CatStockCell(Long id, String code, Long stockId, Double maxWeight, Double maxVolume, Long manyCodes) {
        this.id = id;
        this.code = code;
        this.stockId = stockId;
        this.maxWeight = maxWeight;
        this.maxVolume = maxVolume;
        this.manyCodes = manyCodes;
    }

    public CatStockCell() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CAT_STOCK_CELL")
    @SequenceGenerator(
            name = "SEQ_CAT_STOCK_CELL",
            sequenceName = "SEQ_CAT_STOCK_CELL",
            allocationSize = 1,
            initialValue = 1000
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "STOCK_ID")
    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    @Column(name = "MAX_WEIGHT")
    public Double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }
    @Column(name = "MAX_VOLUME")
    public Double getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(Double maxVolume) {
        this.maxVolume = maxVolume;
    }

    @Column(name = "MANY_CODES")
    public Long getManyCodes() {
        return manyCodes;
    }

    public void setManyCodes(Long manyCodes) {
        this.manyCodes = manyCodes;
    }

    @Override
    public BaseDTO toDTO() {
        return new CatStockCellDTO(id == null ? "" : id + "", code, stockId == null ? "" : stockId + "", maxWeight == null ? "" : maxWeight+"", maxVolume == null ? "" : maxVolume+"", manyCodes == null ? "" : manyCodes+"");
    }
}
