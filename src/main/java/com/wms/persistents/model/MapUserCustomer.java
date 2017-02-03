package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.MapUserCustomerDTO;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by duyot on 1/24/2017.
 */
@Entity
@DynamicUpdate
@Table(name = "MAP_USER_CUSTOMER")
public class MapUserCustomer extends BaseModel{
    private Long id;
    private Long userId;
    private Long customerId;

    public MapUserCustomer() {
    }

    public MapUserCustomer(Long id, Long userId, Long customerId) {
        this.id = id;
        this.userId = userId;
        this.customerId = customerId;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_MAP_USER_CUSTOMER")
    @SequenceGenerator(
            name="SEQ_MAP_USER_CUSTOMER",
            sequenceName="SEQ_MAP_USER_CUSTOMER",
            allocationSize = 1,
            initialValue= 1000
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "USER_ID")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "CUSTOMER_ID")
    public Long getCustomerId() {
        return customerId;
    }


    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public BaseDTO toDTO() {
        return new MapUserCustomerDTO(id==null?"":id+"",userId==null?"":userId+"",customerId==null?"":customerId+"");
    }
}
