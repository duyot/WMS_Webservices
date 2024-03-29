package com.wms.services;

import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.business.interfaces.CatStockBusinessInterface;
import com.wms.dto.CatStockDTO;
import com.wms.utils.DataUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by duyot on 2/17/2017.
 */
@RestController
@RequestMapping("/services/catStockServices")
public class CatStockServices extends BaseServices<CatStockDTO> {
    @Autowired
    BaseBusinessInterface catStockBusiness;

    @Autowired
    CatStockBusinessInterface catStockBusinessInterface;

    @PostConstruct
    public void setupService() {
        this.baseBusiness = catStockBusiness;
    }


    @RequestMapping(value = "/getStockByUser/{userId}", produces = "application/json", method = RequestMethod.GET)
    public List<CatStockDTO> getStockByUser(@PathVariable("userId") String userId) {
        if (!DataUtil.isInteger(userId)) {
            log.info("Invalid userId info");
            return new ArrayList<>();
        }

        return catStockBusinessInterface.getStockByUser(Long.parseLong(userId));
    }
}
