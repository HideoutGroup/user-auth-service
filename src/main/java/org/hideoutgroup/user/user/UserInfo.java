package org.hideoutgroup.user.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈〉
 *
 * @author wenqiangdong
 * @date   2019-05-13
 */
@Data
public class UserInfo {

    private List<InfoNode> list = new ArrayList<>();

    @Data
    public static class InfoNode{
        //字段名
        private String name;

        //字段存储类型
        private String type;

        //字段正则
        private String regexp;

        //最大长度
        private Integer maxLength;
        //最小长度
        private Integer minLength;

        //是否可以为空
        private Boolean empty;

        //是否可以作为用户名
        private Boolean username;

        //是否作为密码
        private Boolean password;

        //是否加密
        private Boolean encrypt;

        //是否可以修改
        private Boolean only;

        //

    }
}
