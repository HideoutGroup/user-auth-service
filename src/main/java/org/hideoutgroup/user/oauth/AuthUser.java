package org.hideoutgroup.user.oauth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author 董文强
 * @version 1.0
 * @date 2019年01月10日
 */

@Data
public class AuthUser  implements AuthObject,UserDetails {

    private String id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities ;


    public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
        this.username = username;
        this.password = password;
    }

    public AuthUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
        this.username = username;
        this.password = password;
    }


    @Override
    public String getUserId() {
        return username;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  this.authorities;
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
}
