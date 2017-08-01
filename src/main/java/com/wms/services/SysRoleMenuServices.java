package com.wms.services;

import com.google.common.collect.Lists;
import com.wms.base.BaseBusinessInterface;
import com.wms.dto.ActionMenuDTO;
import com.wms.dto.Condition;
import com.wms.dto.SysMenuDTO;
import com.wms.dto.SysRoleMenuDTO;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by duyot on 11/3/2016.
 */
@RestController
@RequestMapping("/services/sysRoleMenuServices")
public class SysRoleMenuServices {

    Logger log = LoggerFactory.getLogger(SysRoleMenuServices.class);

    @Autowired
    BaseBusinessInterface sysMenuBusiness;
    @Autowired
    BaseBusinessInterface sysRoleMenuBusiness;


    @RequestMapping(value = "/getUserAction/{roleCode}",produces = "application/json",method = RequestMethod.GET)
    public List<ActionMenuDTO> getUserAction(@PathVariable("roleCode") String roleCode,@RequestHeader Map<String,String> mapHeaders){
        //log.info(mapHeaders.toString());
        List<SysMenuDTO> lstUserAction = getListUserAction(roleCode);
        if(DataUtil.isListNullOrEmpty(lstUserAction)){
            return new ArrayList<>();
        }

        return initActionMenu(lstUserAction);
    }

    private List<ActionMenuDTO> initActionMenu(List<SysMenuDTO> lstAction){
        List<ActionMenuDTO> lstActionMenu = Lists.newArrayList();
        //
        List<SysMenuDTO> lstParentAction = Lists.newArrayList();
        List<SysMenuDTO> lstChildAction  = Lists.newArrayList();
        //lay ra ds action voi parent = 0
        for(SysMenuDTO i: lstAction){
            if(i.getParentId().equals("0")){
                lstParentAction.add(i);
            }else{
                lstChildAction.add(i);
            }
        }

        if(DataUtil.isListNullOrEmpty(lstParentAction)){
            return null;
        }

        for(SysMenuDTO i: lstParentAction){
            ActionMenuDTO actionMenu = new ActionMenuDTO();
            List<SysMenuDTO> lstChild = Lists.newArrayList();

            for(SysMenuDTO j: lstChildAction){
                if(j.getParentId().equals(i.getId())){
                    lstChild.add(j);
                }
            }

            actionMenu.setParentAction(i);
            actionMenu.setLstSubAction(lstChild);

            lstActionMenu.add(actionMenu);
        }
        //
        return lstActionMenu;
    }

    private List<SysMenuDTO> getListUserAction(String roleCode){

        List<SysRoleMenuDTO> lstRoleAction = getRoleActionByRole(roleCode);

        if(DataUtil.isListNullOrEmpty(lstRoleAction)){
            return null;
        }

        StringBuilder strBuilderActionId = new StringBuilder();
        for(SysRoleMenuDTO i: lstRoleAction){
            strBuilderActionId.append(",").append(i.getMenuId());
        }

        String strActionId = strBuilderActionId.toString();
        strActionId = strActionId.replaceFirst(",","");
        List<Condition> lstCondition = Lists.newArrayList();
        lstCondition.add(new Condition("id",Constants.SQL_PRO_TYPE.LONG,Constants.SQL_OPERATOR.IN,strActionId));
        lstCondition.add(new Condition("status",Constants.SQL_OPERATOR.EQUAL,Constants.STATUS.ACTIVE));
        //sap xep theo level(menu cha) -> order trong menu
        lstCondition.add(new Condition("levels",Constants.SQL_OPERATOR.ORDER,"asc"));
        lstCondition.add(new Condition("orders",Constants.SQL_OPERATOR.ORDER,"asc"));

        return sysMenuBusiness.findByCondition(lstCondition);
    }

    private List<SysRoleMenuDTO> getRoleActionByRole(String roleCode){
        List<Condition> lstCondition = Lists.newArrayList();
        lstCondition.add(new Condition("roleCode",Constants.SQL_OPERATOR.EQUAL,roleCode));

        return sysRoleMenuBusiness.findByCondition(lstCondition);
    }

}
