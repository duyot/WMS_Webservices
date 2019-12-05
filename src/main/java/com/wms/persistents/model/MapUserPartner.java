package com.wms.persistents.model;

import com.wms.base.BaseModel;
import com.wms.dto.MapUserPartnerDTO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by duyot on 11/1/2016.
 */
@Entity
@Table(name = "MAP_USER_PARTNER")
@SequenceGenerator(
        name = "sequence",
        sequenceName = "SEQ_MAP_USER_PARTNER"
)
public class MapUserPartner extends BaseModel {
    private Long id;
    private Long userId;
    private Long partnerId;

    public MapUserPartner(Long id, Long userId, Long partnerId) {
        this.id = id;
        this.userId = userId;
        this.partnerId = partnerId;
    }

    public MapUserPartner() {
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

    @Column(name = "PARTNER_ID", nullable = false)
    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }


    @Override
    public MapUserPartnerDTO toDTO() {
        return new MapUserPartnerDTO(id == null ? "" : id + "", userId != null ? String.valueOf(userId) : "", partnerId + "");
    }
}
