package com.wms.persistents.model;

/**
 * Created by doanlv4 on 25/03/2017.
 */
import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.AppParamsDTO;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
@Entity
@DynamicUpdate
@Table(name = "APP_PARAMS")
public class AppParams extends BaseModel  {
    private Long id;
    private String code;
    private String name;
    private byte status;
    private String type;
    private String parOrder;

    public AppParams(Long id,String code, String name, byte status, String type, String parOrder) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
        this.type = type;
        this.parOrder = parOrder;
    }

    public AppParams() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_APP_PARAMS")
    @SequenceGenerator(
            name="SEQ_APP_PARAMS",
            sequenceName="SEQ_APP_PARAMS",
            allocationSize = 1,
            initialValue= 1000
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

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "STATUS")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "PAR_ORDER")
    public String getParOrder() {
        return parOrder;
    }

    public void setParOrder(String parOrder) {
        this.parOrder = parOrder;
    }

    @Override
    public BaseDTO toDTO() {
        return new AppParamsDTO(id==null?"":id+"",code,name,status+"",type, parOrder);
    }
}
