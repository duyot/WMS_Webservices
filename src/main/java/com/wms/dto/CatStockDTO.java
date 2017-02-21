package com.wms.dto;

import com.wms.base.BaseDTO;
import com.wms.base.BaseModel;
<<<<<<< HEAD
import com.wms.utils.StringUtils;
import com.wms.persistents.model.CatStock;
/**
 * Created by doanlv4 on 2/17/2017.
 */
public class CatStockDTO extends BaseDTO {
    private String id;
    private String name;
    private String code;
    private String status;
    private String custId;
    private String address;

    public CatStockDTO(String id, String code, String name, String status, String custId, String address ) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.custId = custId;
        this.address = address;
        this.code = code;
=======
import com.wms.persistents.model.CatStock;
import com.wms.utils.StringUtils;

/**
 * Created by duyot on 2/17/2017.
 */
public class CatStockDTO extends BaseDTO {
    private String id;
    private String custId;
    private String code;
    private String name;
    private String address;
    private String status;
    private String managerInfo;

    public CatStockDTO(String id, String custId, String code, String name, String address, String status, String managerInfo) {
        this.id = id;
        this.custId = custId;
        this.code = code;
        this.name = name;
        this.address = address;
        this.status = status;
        this.managerInfo = managerInfo;
>>>>>>> f97d1f5817603b7b5087e101d6f6579d2945e4eb
    }

    public CatStockDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

<<<<<<< HEAD
=======
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

>>>>>>> f97d1f5817603b7b5087e101d6f6579d2945e4eb
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

<<<<<<< HEAD
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public BaseModel toModel() {
        return new CatStock(!StringUtils.validString(id) ? null:Long.valueOf(id),code,name,status,custId, address);
    }

    @Override
    public String toString() {
        return "CatStockDTO{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", custId='" + custId + '\'' +
                ", address='" + address + '\'' +
                '}';
=======
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getManagerInfo() {
        return managerInfo;
    }

    public void setManagerInfo(String managerInfo) {
        this.managerInfo = managerInfo;
    }

    @Override
    public CatStock toModel() {
        return new CatStock(!StringUtils.validString(id) ? null:Long.valueOf(id),!StringUtils.validString(custId) ? null:Long.valueOf(custId),
                    code,name,address,status,managerInfo
                );
>>>>>>> f97d1f5817603b7b5087e101d6f6579d2945e4eb
    }
}
