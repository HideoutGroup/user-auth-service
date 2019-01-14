package org.hideoutgroup.user.controller;

import lombok.Data;
import org.hideoutgroup.user.auth.AuthUser;
import org.hideoutgroup.user.auth.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author 董文强
 * @version 1.0
 * @date 2019年01月10日
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public String login(@Valid @RequestBody login user, HttpServletResponse response) {
        AuthUser authUser = new AuthUser();
        authUser.setRole("经理");
        authUser.setPassword(user.getPassword());
        authUser.setUsername(user.username);
        String token = JwtHelper.createJWTByObj(authUser);
        response.setHeader("token", token);
        LOGGER.info(token);
        return token;
    }

    @Data
    static class login {
        @NotBlank String username;
        @NotBlank String password;
    }
    @RequestMapping("/ver")
    public String verToken(@RequestHeader("token") String token){
        LOGGER.info(token);
        JwtHelper.getInfoByToken(token);
        return "123";
    }


}
