package com.wms.base;

/**
 * Created by duyot on 8/31/2016.
 */
public interface BaseDTOInterface<T extends BaseModel> {
    T toModel();
}
