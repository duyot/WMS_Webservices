package com.wms.sercurity;

import com.wms.dto.CatUserDTO;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by duyot on 11/18/2016.
 */
public class WMSUserDetails implements UserDetails, CredentialsContainer {
    private CatUserDTO catUserDTO;
    private Set<GrantedAuthority> lstAuthorities;

    public WMSUserDetails() {
    }

    public WMSUserDetails(CatUserDTO catUserDTO) {
        this.catUserDTO = catUserDTO;
        this.lstAuthorities = getLstAuthorities("ADMIN");
    }

    public Set<GrantedAuthority> getLstAuthorities(String roleCode) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + roleCode);
        authorities.add(grantedAuthority);
        return authorities;
    }

    public CatUserDTO getCatUserDTO() {
        return catUserDTO;
    }

    public void setCatUserDTO(CatUserDTO catUserDTO) {
        this.catUserDTO = catUserDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return lstAuthorities;
    }

    @Override
    public String getPassword() {
        return catUserDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return catUserDTO.getCode();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void eraseCredentials() {
        this.catUserDTO.setPassword(null);
    }
}
