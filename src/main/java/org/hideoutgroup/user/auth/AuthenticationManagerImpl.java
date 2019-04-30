package org.hideoutgroup.user.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 〈〉
 *
 * @author wenqiangdong
 * @date 2019-04-23
 */

@Slf4j
public class AuthenticationManagerImpl implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("{}",authentication);
        return null;
    }
}
