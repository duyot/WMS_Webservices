package com.wms.business.interfaces;

import com.wms.dto.AdminUserDTO;

import java.util.List;

/**
 * Created by duyot on 8/24/2016.
 */
public interface AdminUserBusinessInterface {
    public AdminUserDTO login(AdminUserDTO loginUser);
}
