package com.wms.services;

import com.wms.business.interfaces.StatisticBusinessInterface;
import com.wms.dto.ChartDTO;
import com.wms.dto.InventoryInfoDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by duyot on 5/18/2017.
 */
@RestController
@RequestMapping("/services/statisticServices")
public class StatisticServices {
    Logger log = LoggerFactory.getLogger(StatisticServices.class);
    @Autowired
    StatisticBusinessInterface statisticBusinessInterface;

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/getRevenue", produces = "application/json")
    public List<ChartDTO> getRevenue(@RequestParam("custId") String custId, @RequestParam("type") String type) {
        return statisticBusinessInterface.getRevenue(custId, Integer.parseInt(type));
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/getTopGoods", produces = "application/json")
    public List<ChartDTO> getTopGoods(@RequestParam("custId") String custId, @RequestParam("type") String type) {
        return statisticBusinessInterface.getTopGoods(custId, Integer.parseInt(type));
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/getTransaction", produces = "application/json")
    public List<ChartDTO> getTransaction(@RequestParam("custId") String custId, @RequestParam("type") String type, @RequestParam("userId") String userId) {
        return statisticBusinessInterface.getTransaction(custId, Integer.parseInt(type), userId);
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/getKPIStorage", produces = "application/json")
    public List<ChartDTO> getKPIStorage(@RequestParam("custId") String custId, @RequestParam("type") String type, @RequestParam("userId") String userId) {
        return statisticBusinessInterface.getKPIStorage(custId, Integer.parseInt(type), userId);
    }

    @RequestMapping(value = "/getInventoryInfor", produces = "application/json")
    public InventoryInfoDTO getInventoryInfor(@RequestParam("custId") String custId) {
        return statisticBusinessInterface.getInventoryInfor(custId);
    }
}
