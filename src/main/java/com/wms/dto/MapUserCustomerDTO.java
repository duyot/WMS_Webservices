package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.persistents.model.MapUserCustomer;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 1/24/2017.
 */
public class MapUserCustomerDTO extends BaseDTO{
    private String id;
    private String userId;
    private String customerId;

    public MapUserCustomerDTO() {
    }

    public MapUserCustomerDTO(String id, String userId, String customerId) {
        this.id = id;
        this.userId = userId;
        this.customerId = customerId;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public MapUserCustomer toModel() {
        return new MapUserCustomer(!StringUtils.validString(id) ? null:Long.valueOf(id),
                !StringUtils.validString(userId) ? null:Long.valueOf(userId),
                !StringUtils.validString(customerId) ? null:Long.valueOf(customerId) );

    }
}
