package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.persistents.model.MapUserPartner;
import com.wms.utils.StringUtils;

public class MapUserPartnerDTO extends BaseDTO {

    String id;
    String userId;
    String partnerId;

    public MapUserPartnerDTO(String id, String userId, String partnerId) {
        this.id = id;
        this.userId = userId;
        this.partnerId = partnerId;
    }

    public MapUserPartnerDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    @Override
    public MapUserPartner toModel() {
        return new MapUserPartner(!StringUtils.validString(id) ? null:Long.valueOf(id),!StringUtils.validString(userId) ? null:Long.valueOf(userId),!StringUtils.validString(partnerId) ? null:Long.valueOf(partnerId));
    }
}
