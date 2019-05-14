package org.hideoutgroup.user.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 〈〉
 *
 * @author wenqiangdong
 * @date   2019-04-29
 */
@Slf4j
@Component
public class UserDetailAuthenticationConverter extends DefaultUserAuthenticationConverter {

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap();

        response.put("user", authentication.getPrincipal());
        return response;
    }
}