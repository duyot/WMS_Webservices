package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.business.interfaces.CatPartnerBusinessInterface;
import com.wms.dto.CatPartnerDTO;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by duyot on 12/7/2016.
 */
@RestController
@RequestMapping("/services/catPartnerServices")
public class CatPartnerServices extends BaseServices<CatPartnerDTO> {
    @Autowired
    BaseBusinessInterface catPartnerBusiness;

    @Autowired
    CatPartnerBusinessInterface catPartnerBusinessInterface;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = catPartnerBusiness;
    }

    @RequestMapping(value = "/getPartnerByUser", produces = "application/json", method = RequestMethod.GET)
    public List<CatPartnerDTO> getPartnerByUser(@RequestParam("userId") String userId, @RequestParam("partnerPermission") String partnerPermission) {
        return catPartnerBusinessInterface.getPartnerByUser(Long.valueOf(userId), Long.valueOf(partnerPermission));
    }

    @RequestMapping(value = "/updatePartnerByProperties", produces = "application/json", method = RequestMethod.GET)
    public String getPartnerByUser(@RequestBody CatPartnerDTO partner) {
        String[] updateProperties = {"address"};
        return catPartnerBusiness.updateByProperties(partner, Long.parseLong(partner.getId()), updateProperties);
    }
}