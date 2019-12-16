package com.wms.persistents.model;

import com.wms.base.BaseModel;
import com.wms.dto.MapUserStockDTO;
import javax.persistence.*;

/**
 * Created by duyot on 11/1/2016.
 */
@Entity
@Table(name = "MAP_USER_STOCK")
@SequenceGenerator(
        name = "sequence",
        sequenceName = "SEQ_MAP_USER_STOCK"
)
public class MapUserStock extends BaseModel {
    private Long id;
    private Long userId;
    private Long stockId;

    public MapUserStock(Long id, Long userId, Long stockId) {
        this.id = id;
        this.userId = userId;
        this.stockId = stockId;
    }

    public MapUserStock() {
    }

    @Id
    @GeneratedValue(generator = "sequence")
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "USER_ID", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "STOCK_ID", nullable = false)
    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }


    @Override
    public MapUserStockDTO toDTO() {
        return new MapUserStockDTO(id == null ? "" : id + "", userId != null ? String.valueOf(userId) : "", stockId + "");
    }
}
