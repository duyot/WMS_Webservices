package com.wms.services;

import com.google.common.collect.Lists;
import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.dto.*;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by duyot on 3/24/2017.
 */
@RestController
@RequestMapping("/services/mjrStockGoodsTotalServices")
public class MjrStockGoodsTotalServices extends BaseServices<MjrStockGoodsTotalDTO> {
    @Autowired
    BaseBusinessInterface mjrStockGoodsTotalBusiness;
    @Autowired
    BaseBusinessInterface mjrStockGoodsSerialBusiness;
    @Autowired
    BaseBusinessInterface mjrStockGoodsBusiness;

    @PostConstruct
    public void setupServices(){
        this.baseBusiness = mjrStockGoodsTotalBusiness;
    }

    @RequestMapping(value = "/getGoodsDetail",method = RequestMethod.GET)
    public List<MjrStockTransDetailDTO> getGoodsDetail(@RequestParam("custId")String custId,@RequestParam("stockId")String stockId, @RequestParam("goodsId")String goodsId,@RequestParam("isSerial")String isSerial,
                                   @RequestParam("goodsState")String goodsState,@RequestParam("limit")String limit,@RequestParam("offset")String offset){
        //
        List<Condition> lstCon = Lists.newArrayList();
        lstCon.add(new Condition("custId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL,custId));
        lstCon.add(new Condition("status", Constants.SQL_OPERATOR.EQUAL,Constants.STATUS.ACTIVE));

        if(!DataUtil.isStringNullOrEmpty(stockId) && !stockId.equals("-1")){
            lstCon.add(new Condition("stockId",Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL,stockId));
        }
        if(!DataUtil.isStringNullOrEmpty(goodsId) && !goodsId.equals("-1")){
            lstCon.add(new Condition("goodsId",Constants.SQL_PRO_TYPE.LONG,Constants.SQL_OPERATOR.EQUAL,goodsId));
        }
        if(!DataUtil.isStringNullOrEmpty(goodsState) && !goodsState.equals("-1")){
            lstCon.add(new Condition("goodsState",Constants.SQL_OPERATOR.EQUAL,goodsState));
        }
        lstCon.add(new Condition("",Constants.SQL_OPERATOR.LIMIT, Integer.parseInt(limit)));
        lstCon.add(new Condition("",Constants.SQL_OPERATOR.OFFSET, Integer.parseInt(offset)));
        lstCon.add(new Condition("importDate",Constants.SQL_OPERATOR.ORDER,"desc"));
        //
        if("1".equalsIgnoreCase(isSerial)){
            return convertGoodsSerialToDetail(mjrStockGoodsSerialBusiness.findByCondition(lstCon));
        }else{
            return convertGoodsToDetail(mjrStockGoodsBusiness.findByCondition(lstCon));
        }
    }

    @RequestMapping(value = "/getCountGoodsDetail",method = RequestMethod.GET)
    public Long getCountGoodsDetail(@RequestParam("custId")String custId,@RequestParam("stockId")String stockId, @RequestParam("goodsId")String goodsId,@RequestParam("isSerial")String isSerial,
                                                        @RequestParam("goodsState")String goodsState){
        List<Condition> lstCon = Lists.newArrayList();
        lstCon.add(new Condition("custId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL,custId));

        if(!DataUtil.isStringNullOrEmpty(stockId) && !stockId.equals("-1")){
            lstCon.add(new Condition("stockId",Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL,stockId));
        }
        if(!DataUtil.isStringNullOrEmpty(goodsId) && !goodsId.equals("-1")){
            lstCon.add(new Condition("goodsId",Constants.SQL_PRO_TYPE.LONG,Constants.SQL_OPERATOR.EQUAL,goodsId));
        }
        if(!DataUtil.isStringNullOrEmpty(goodsState) && !goodsState.equals("-1")){
            lstCon.add(new Condition("goodsState",Constants.SQL_OPERATOR.EQUAL,goodsState));
        }
        //
        if("1".equalsIgnoreCase(isSerial)){
            return mjrStockGoodsSerialBusiness.countByCondition(lstCon);
        }else{
            return mjrStockGoodsBusiness.countByCondition(lstCon);
        }
    }

    private List<MjrStockTransDetailDTO> convertGoodsToDetail(List<MjrStockGoodsDTO> lstStockGoods){
        List<MjrStockTransDetailDTO> lstResult = Lists.newArrayList();
        if (!DataUtil.isListNullOrEmpty(lstStockGoods)) {
            for(MjrStockGoodsDTO i:lstStockGoods){
                MjrStockTransDetailDTO temp = new MjrStockTransDetailDTO();
                temp.setGoodsId(i.getGoodsId());
                temp.setGoodsState(i.getGoodsState());
                temp.setStatus(i.getStatus());
                temp.setAmount(i.getAmount());
                temp.setImportDate(i.getImportDate());
                temp.setExportDate(i.getExportDate());
                temp.setInputPrice(i.getInputPrice());
                temp.setOutputPrice(i.getOutputPrice());
                //
                lstResult.add(temp);
            }
        }
        return lstResult;
    }


    public static List<MjrStockTransDetailDTO> convertGoodsSerialToDetail(List<MjrStockGoodsSerialDTO> lstStockGoodsSerial){
        List<MjrStockTransDetailDTO> lstResult = Lists.newArrayList();
        if (!DataUtil.isListNullOrEmpty(lstStockGoodsSerial)) {
            for(MjrStockGoodsSerialDTO i:lstStockGoodsSerial){
                MjrStockTransDetailDTO temp = new MjrStockTransDetailDTO();
                temp.setGoodsId(i.getGoodsId());
                temp.setGoodsState(i.getGoodsState());
                temp.setStatus(i.getStatus());
                temp.setSerial(i.getSerial());
                temp.setAmount(i.getAmount());
                temp.setImportDate(i.getImportDate());
                temp.setExportDate(i.getExportDate());
                temp.setInputPrice(i.getInputPrice());
                temp.setOutputPrice(i.getOutputPrice());
                temp.setStockId(i.getStockId());
                //
                lstResult.add(temp);
            }
        }
        return lstResult;
    }


}
