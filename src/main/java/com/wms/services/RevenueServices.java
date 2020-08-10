package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import java.util.List;
import javax.annotation.PostConstruct;

import com.wms.dto.RevenueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by duyot on 12/7/2016.
 */
@RestController
@RequestMapping("/services/revenueServices")
public class RevenueServices extends BaseServices<RevenueDTO> {
    @Autowired
    BaseBusinessInterface revenueBusiness;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = revenueBusiness;
    }

}