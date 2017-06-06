package com.wms.services;

import com.wms.business.interfaces.StatisticBusinessInterface;
import com.wms.dto.ChartDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @RequestMapping(value = "/getRevenue",produces = "application/json")
    public List<ChartDTO> getRevenue(@RequestParam("custId") String custId, @RequestParam("type") String type){
        log.info("-------------------------------");
        return statisticBusinessInterface.getRevenue(custId,Integer.parseInt(type));
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/getTopGoods",produces = "application/json")
    public List<ChartDTO> getTopGoods(@RequestParam("custId") String custId, @RequestParam("type") String type){
        log.info("-------------------------------");
        return statisticBusinessInterface.getTopGoods(custId,Integer.parseInt(type));
    }
}
