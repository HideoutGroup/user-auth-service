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
public class AuthUser {
    public String username;
    public String password;
    public String role;
}
