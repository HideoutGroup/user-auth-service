package org.hideoutgroup.user.auth;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author 董文强
 * @date 2019年01月10日
 * @version 1.0
 */
@Data
public class TokenObject {


    /**base64加密*/
    private String base64Secret;
    /**用户名*/
    private String username;
    /**用户ID*/
    private String userId;
    /**到期时间*/
    private long expiresSecond;

    /**角色*/
    private String role;

}
