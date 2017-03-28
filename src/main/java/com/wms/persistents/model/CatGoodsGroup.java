package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.CatGoodsGroupDTO;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by duyot on 12/7/2016.
 */
@Entity
@DynamicUpdate
@Table(name = "CAT_GOODS_GROUP")
public class CatGoodsGroup extends BaseModel{
    private Long id;
    private String name;
    private String status;
    private Long custId;

    public CatGoodsGroup(Long id, String name, String status, Long custId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.custId = custId;
    }

    public CatGoodsGroup() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CAT_GOODS_GROUP")
    @SequenceGenerator(
            name="SEQ_CAT_GOODS_GROUP",
            sequenceName="SEQ_CAT_GOODS_GROUP",
            allocationSize = 1,
            initialValue= 1000
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "CUST_ID")
    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    @Override
    public BaseDTO toDTO() {
        return new CatGoodsGroupDTO(id==null?"":id+"",name,status.equals("1")?"Hiệu lực":"Hết hiệu lực",id==null?"":id+"");
    }
}
