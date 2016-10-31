package com.wms.business.interfaces;

import com.wms.dto.UserDTO;

/**
 * Created by duyot on 8/24/2016.
 */
public interface UserBusinessInterface {
    public UserDTO login(UserDTO loginUser);
}
