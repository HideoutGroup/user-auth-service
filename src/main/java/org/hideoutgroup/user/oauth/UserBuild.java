package org.hideoutgroup.user.oauth;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 〈〉
 *
 * @author wenqiangdong
 * @date 2019-04-29
 */
public class UserBuild {

    private final static AtomicLong AUTO_ID = new AtomicLong(1);

    //private AtomicLong AUTO_ID(){return null;}

    public static AuthUser create(String username, String password) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add((GrantedAuthority) () -> "all");
        AuthUser user = new AuthUser(username, password, authorities);
        user.setId(AUTO_ID.getAndAdd(1) + "");
        return user;
    }
}
