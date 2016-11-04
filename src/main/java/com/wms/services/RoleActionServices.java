package com.wms.services;

import com.google.common.collect.Lists;
import com.wms.base.BaseBusinessInterface;
import com.wms.dto.*;
import com.wms.enums.Responses;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duyot on 11/3/2016.
 */
@RestController
@RequestMapping("/roleActionServices")
public class RoleActionServices {

    Logger log = LoggerFactory.getLogger(RoleActionServices.class);

    @Autowired
    BaseBusinessInterface actionBusiness;
    @Autowired
    BaseBusinessInterface roleActionBusiness;


    @RequestMapping(value = "/getUserAction/{roleId}",produces = "application/json",method = RequestMethod.GET)
    public List<ActionMenuDTO> getUserAction(@PathVariable("roleId") String roleId){
        if(!DataUtil.isInteger(roleId)){
            return null;
        }

        List<ActionDTO> lstUserAction = getListUserAction(roleId);
        if(DataUtil.isListNullOrEmpty(lstUserAction)){
            return new ArrayList<>();
        }

        return initActionMenu(lstUserAction);
    }

    private List<ActionMenuDTO> initActionMenu(List<ActionDTO> lstAction){
        List<ActionMenuDTO> lstActionMenu = Lists.newArrayList();
        //
        List<ActionDTO> lstParentAction = Lists.newArrayList();
        List<ActionDTO> lstChildAction  = Lists.newArrayList();
        //lay ra ds action voi parent = 0
        for(ActionDTO i: lstAction){
            if(i.getParentActionId().equals("0")){
                lstParentAction.add(i);
            }else{
                lstChildAction.add(i);
            }
        }

        if(DataUtil.isListNullOrEmpty(lstParentAction)){
            return null;
        }

        for(ActionDTO i: lstParentAction){
            ActionMenuDTO actionMenu = new ActionMenuDTO();
            List<ActionDTO> lstChild = Lists.newArrayList();

            for(ActionDTO j: lstChildAction){
                if(j.getParentActionId().equals(i.getId())){
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

    private List<ActionDTO> getListUserAction(String roleId){

        List<RoleActionDTO> lstRoleAction = getRoleActionByRole(roleId);

        if(DataUtil.isListNullOrEmpty(lstRoleAction)){
            return null;
        }

        StringBuilder strBuilderActionId = new StringBuilder();
        for(RoleActionDTO i: lstRoleAction){
            strBuilderActionId.append(",").append(i.getActionId());
        }

        String strActionId = strBuilderActionId.toString();
        strActionId = strActionId.replaceFirst(",","");
        List<Condition> lstCondition = Lists.newArrayList();
        lstCondition.add(new Condition("id",Constants.SQL_PRO_TYPE.LONG,Constants.SQL_OPERATOR.IN,strActionId));

        return actionBusiness.findByCondition(lstCondition);
    }

    private List<RoleActionDTO> getRoleActionByRole(String roleId){
        List<Condition> lstCondition = Lists.newArrayList();
        lstCondition.add(new Condition("roleId",Constants.SQL_PRO_TYPE.LONG,Constants.SQL_OPERATOR.EQUAL,roleId));
        lstCondition.add(new Condition("status",Constants.SQL_OPERATOR.EQUAL,Constants.STATUS.ACTIVE));

        return roleActionBusiness.findByCondition(lstCondition);
    }

}
