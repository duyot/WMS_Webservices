package com.wms.services;

import com.google.common.collect.Lists;
import com.wms.base.BaseBusinessInterface;
import com.wms.base.BaseServices;
import com.wms.business.interfaces.SysMenuBusinessInterface;
import com.wms.dto.ActionMenuDTO;
import com.wms.dto.Condition;
import com.wms.dto.SysMenuDTO;
import com.wms.dto.SysRoleMenuDTO;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by duyot on 11/3/2016.
 */
@RestController
@RequestMapping("/services/sysRoleMenuServices")
public class SysRoleMenuServices extends BaseServices<SysRoleMenuDTO> {

    Logger log = LoggerFactory.getLogger(SysRoleMenuServices.class);


    @Autowired
    SysMenuBusinessInterface sysMenuBusinessImpl;
    @Autowired
    BaseBusinessInterface sysRoleMenuBusiness;


    @PostConstruct
    public void setupService() {
        this.baseBusiness = sysRoleMenuBusiness;
    }

    @RequestMapping(value = "/getUserAction", produces = "application/json", method = RequestMethod.GET)
    @Cacheable("menus")
    public List<ActionMenuDTO> getUserAction(@RequestParam("roleId") String roleId, @RequestParam("cusId") String cusId) {
        List<SysMenuDTO> menus = getListUserAction(roleId, cusId);
        if (DataUtil.isListNullOrEmpty(menus)) {
            return new ArrayList<>();
        }
        return initActionMenu(menus);
    }

    private List<SysMenuDTO> getListUserAction(String roleId, String cusId) {

        List<SysRoleMenuDTO> lstRoleAction = getRoleActionByRole(roleId);

        if (DataUtil.isListNullOrEmpty(lstRoleAction)) {
            return null;
        }

        StringBuilder strBuilderActionId = new StringBuilder();
        for (SysRoleMenuDTO i : lstRoleAction) {
            strBuilderActionId.append(",").append(i.getMenuId());
        }

        String strActionId = strBuilderActionId.toString();
        strActionId = strActionId.replaceFirst(",", "");
        List<Condition> lstCondition = Lists.newArrayList();
        lstCondition.add(new Condition("id", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.IN, strActionId));
        lstCondition.add(new Condition("status", Constants.SQL_PRO_TYPE.BYTE, Constants.SQL_OPERATOR.EQUAL, Constants.STATUS.ACTIVE));
        //sap xep theo level(menu cha) -> order trong menu
        lstCondition.add(new Condition("levels", Constants.SQL_OPERATOR.ORDER, "asc"));
        lstCondition.add(new Condition("orders", Constants.SQL_OPERATOR.ORDER, "asc"));

        return sysMenuBusinessImpl.findByCondition(lstCondition);
    }

    private List<SysRoleMenuDTO> getRoleActionByRole(String roleId) {
        List<Condition> lstCondition = Lists.newArrayList();
        lstCondition.add(new Condition("roleId", Constants.SQL_PRO_TYPE.LONG, Constants.SQL_OPERATOR.EQUAL, roleId));
        return sysRoleMenuBusiness.findByCondition(lstCondition);
    }

    private List<ActionMenuDTO> initActionMenu(List<SysMenuDTO> lstAction) {
        List<ActionMenuDTO> lstActionMenu = Lists.newArrayList();
        //
        List<SysMenuDTO> lstParentAction = Lists.newArrayList();
        List<SysMenuDTO> lstChildAction = Lists.newArrayList();
        //lay ra ds action voi parent = 0
        for (SysMenuDTO i : lstAction) {
            if (i.getParentId().equals("0")) {
                lstParentAction.add(i);
            } else {
                lstChildAction.add(i);
            }
        }

        if (DataUtil.isListNullOrEmpty(lstParentAction)) {
            return null;
        }

        for (SysMenuDTO i : lstParentAction) {
            ActionMenuDTO actionMenu = new ActionMenuDTO();
            List<SysMenuDTO> lstChild = Lists.newArrayList();

            for (SysMenuDTO j : lstChildAction) {
                if (j.getParentId().equals(i.getId())) {
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


}
