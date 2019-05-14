package org.hideoutgroup.user.controller;

import org.hideoutgroup.user.db.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * @author 董文强
 * @version 1.0
 * @date 2018年12月18日
 */

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * 根据用户id用户
     * */
    @GetMapping
    public void getUser(Integer id) {
        return;
    }
    /**
     * 添加用户
     * */
    @PostMapping
    public User addUser(User user) {

        return null;
    }

    /**
     * 删除用户
     * */
    @DeleteMapping
    public void deleteUser(Integer id) {

    }
    /**
     * 修改用户
     * */
    @PutMapping
    public User updateUser(User user) {
        return null;
    }

}
