package org.hideoutgroup.user.config;

import org.hideoutgroup.user.auth.UserBuild;
import org.hideoutgroup.user.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈〉
 *
 * @author wenqiangdong
 * @date 2019-04-28
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder,UserDetailsService userDetailsService) throws Exception {
        //auth.
        //用户信息保存在内存中
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService(){
        List<UserDetails> list = new ArrayList<>();
        list.add(UserBuild.create("user123","user"));
        list.add(UserBuild.create("test123","test"));
        UserService manager = new UserService(list);
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //登记界面，默认是permit All
        http.csrf().disable();
        http.requestMatchers().antMatchers("/**","/oauth/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



}
