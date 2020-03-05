package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.business.interfaces.CatPartnerBusinessInterface;
import com.wms.dto.CatReasonDTO;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by duyot on 12/7/2016.
 */
@RestController
@RequestMapping("/services/catReasonServices")
public class CatReasonServices extends BaseServices<CatReasonDTO> {
    @Autowired
    BaseBusinessInterface catReasonBusiness;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = catReasonBusiness;
    }


}