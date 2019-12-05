package com.wms.sercurity;

import com.google.common.collect.Lists;
import com.wms.base.BaseBusinessInterface;
import com.wms.dto.CatUserDTO;
import com.wms.dto.Condition;
import com.wms.utils.Constants;
import com.wms.utils.DataUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

/**
 * Created by duyot on 11/18/2016.
 */
@Controller("wmsUserDetailsService")
public class WMSUserDetailsService implements UserDetailsService {
    @Autowired
    BaseBusinessInterface catUserBusiness;

    Logger log = LoggerFactory.getLogger(WMSUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String code) throws UsernameNotFoundException {
        List<Condition> lstCon = Lists.newArrayList();
        lstCon.add(new Condition("code", Constants.SQL_OPERATOR.EQUAL, code));
        //
        try {
            List<CatUserDTO> lstCatUserDTO = catUserBusiness.findByCondition(lstCon);
            if (DataUtil.isListNullOrEmpty(lstCatUserDTO)) {
                log.info("CatUserDTO not available");
                return null;
            }
            CatUserDTO loggedCatUserDTO = lstCatUserDTO.get(0);
            return new WMSUserDetails(loggedCatUserDTO);
        } catch (Exception e) {
            log.info(e.toString());
            throw new UsernameNotFoundException("CatUserDTO not found");
        }
    }
}
