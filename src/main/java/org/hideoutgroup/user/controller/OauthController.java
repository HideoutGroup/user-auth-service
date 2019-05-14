/**
  * Copyright (C), 2005-2019, Juphoon Corporation
  *
  * FileName   : OauthController
  * Author     : wenqiangdong
  * Date       : 2019-05-09 17:48
  * Description: 
  * 
  *
  * History:
  * <author>          <time>          <version>          <desc>
  * 作者姓名           修改时间           版本号              描述
  */
    package org.hideoutgroup.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈〉
 *
 * @author wenqiangdong
 * @date   2019-05-09
 */
@RestController
@RequestMapping("/oauth")
public class OauthController {

    /**
     * 授权
     * @return
     */
    @GetMapping("/authorize")
    public String authorize(){
        return "NO";
    }

    /**
     * 确认token
     * @return
     */
    @GetMapping("/check_token")
    public String checkToken(){
        return "NO";
    }

    @GetMapping("/token")
    public String token(){
        return "NO";
    }
}
