package com.wms.business.impl;

import com.google.common.collect.Lists;
import com.wms.base.BaseBusinessImpl;
import com.wms.business.interfaces.MjrStockGoodsTotalBusinessInterface;
import com.wms.dto.CatGoodsDTO;
import com.wms.dto.Condition;
import com.wms.dto.MjrStockGoodsTotalDTO;
import com.wms.persistents.dao.MjrStockGoodsTotalDAO;
import com.wms.persistents.model.CatGoods;
import com.wms.persistents.model.MjrStockGoodsTotal;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by duyot on 12/19/2016.
 */
@Service("mjrStockGoodsTotalBusiness")
public class MjrStockGoodsTotalBusinessImpl extends BaseBusinessImpl<MjrStockGoodsTotalDTO, MjrStockGoodsTotalDAO> implements MjrStockGoodsTotalBusinessInterface{
    @Autowired
    MjrStockGoodsTotalDAO mjrStockGoodsTotalDAO;

    @PostConstruct
    public void setupService() {
        this.tdao = mjrStockGoodsTotalDAO;
        this.entityClass = MjrStockGoodsTotalDTO.class;
        this.mjrStockGoodsTotalDAO.setModelClass(MjrStockGoodsTotal.class);
        this.tDTO = new MjrStockGoodsTotalDTO();
    }

    @Override
    public List<MjrStockGoodsTotalDTO> findByCondition(List<Condition> lstCondition, Session session) {
        return listModelToDTO(mjrStockGoodsTotalDAO.findByConditionSession(lstCondition,session));
    }

    @Override
    public int updateGoods(CatGoodsDTO goods) {
        return mjrStockGoodsTotalDAO.updateGoods((CatGoods) goods.toModel());
    }
    @Override
    public List<MjrStockGoodsTotalDTO> findMoreCondition(MjrStockGoodsTotalDTO searchGoodsTotalDTO) {
        return mjrStockGoodsTotalDAO.findMoreCondition(searchGoodsTotalDTO);
    }

    public List<MjrStockGoodsTotalDTO> listModelToDTO(List<MjrStockGoodsTotal> lstModel){
        List<MjrStockGoodsTotalDTO> lstResult = Lists.newArrayList();
        for(MjrStockGoodsTotal i: lstModel){
            lstResult.add(i.toDTO());
        }
        return lstResult;
    }




}
