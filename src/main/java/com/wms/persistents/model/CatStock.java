package com.wms.persistents.model;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
import com.wms.dto.CatStockDTO;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
<<<<<<< HEAD
 * Created by doanlv4 on 2/17/2017.
=======
 * Created by duyot on 2/17/2017.
>>>>>>> f97d1f5817603b7b5087e101d6f6579d2945e4eb
 */
@Entity
@DynamicUpdate
@Table(name = "CAT_STOCK")
<<<<<<< HEAD
public class CatStock extends BaseModel {
    private Long id;
    private String code;
    private String name;
    private String status;
    private String custId;
    private String address;

    public CatStock(Long id,String code, String name, String status, String custId, String address) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
        this.custId = custId;
        this.address = address;
=======
public class CatStock  extends BaseModel{
    private Long id;
    private Long custId;
    private String code;
    private String name;
    private String address;
    private String status;
    private String managerInfo;

    public CatStock(Long id, Long custId, String code, String name, String address, String status, String managerInfo) {
        this.id = id;
        this.custId = custId;
        this.code = code;
        this.name = name;
        this.address = address;
        this.status = status;
        this.managerInfo = managerInfo;
>>>>>>> f97d1f5817603b7b5087e101d6f6579d2945e4eb
    }

    public CatStock() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CAT_STOCK")
    @SequenceGenerator(
            name="SEQ_CAT_STOCK",
            sequenceName="SEQ_CAT_STOCK",
            allocationSize = 1,
            initialValue= 1000
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

<<<<<<< HEAD
=======
    @Column(name = "CUST_ID")
    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

>>>>>>> f97d1f5817603b7b5087e101d6f6579d2945e4eb
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

<<<<<<< HEAD
=======
    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

>>>>>>> f97d1f5817603b7b5087e101d6f6579d2945e4eb
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

<<<<<<< HEAD
    @Column(name = "CUST_ID")
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public BaseDTO toDTO() {
        return new CatStockDTO(id==null?"":id+"",code,name,status,custId, address);
=======
    @Column(name = "MANAGER_INFO")
    public String getManagerInfo() {
        return managerInfo;
    }

    public void setManagerInfo(String managerInfo) {
        this.managerInfo = managerInfo;
    }

    @Override
    public CatStockDTO toDTO() {
        return new CatStockDTO(id==null?"":id+"",custId==null?"":custId+"",code,name,address,status,managerInfo);
>>>>>>> f97d1f5817603b7b5087e101d6f6579d2945e4eb
    }
}
