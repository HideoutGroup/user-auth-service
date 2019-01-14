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
public class AuthUser implements AuthObject {
    public String username;
    public String password;
    public String role;
    /**有效时长时间*/
    private long expiresSecond;
    @Override
    public String getUserId() {
        return username;
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", expiresSecond=" + expiresSecond +
                '}';
    }
}
