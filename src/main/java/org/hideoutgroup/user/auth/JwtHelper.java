package org.hideoutgroup.user.auth;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author 董文强
 * @version 1.0
 * @date 2019年01月10日
 */
public class JwtHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtHelper.class);


    /**
     * 校验Token
     *
     * @param jwt
     * @param httpRequest
     * @return
     */
    public static int checkToken(String jwt, HttpServletRequest httpRequest) {
        if (StringUtils.isEmpty(jwt)) {
            throw new RuntimeException("jwt 为空");
        }
        if (jwt.split("\\.").length != 3) {
            throw new RuntimeException("jwt 格式不正确");
        }
        LOGGER.info("jwt:" + jwt);
        String[] split = jwt.split("\\.");
        String s = Base64Codec.BASE64URL.decodeToString(split[1]);

        String sign = split[2];
        LOGGER.info("sign:" + sign);
        JSONObject jsonObject1 = JSONObject.parseObject(split[2]);


        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expiresSecond = (long) jsonObject1.get("expiresSecond");

        //判断是否过期
        if (now.getTime() > expiresSecond)
            return 2;


        AuthUser o = JSONObject.toJavaObject(jsonObject1, AuthUser.class);

        String jwtByStr = createJWTByObj(o);
        String s2 = jwtByStr.split("\\.")[2];
        LOGGER.info("s2:" + s2);
        if (sign.equals(s2)) {
            return 1;

        }
        return 0;
    }

   
    /**
     * 获取客户信息
     *
     * @param token
     * @return
     * @throws
     */
    public static AuthObject getInfoByToken(String token) {
        if ((token == null) || (token.length() < 6)) {
            throw new RuntimeException("token no");
        }
        String context =  Base64Codec.BASE64URL.decodeToString(token.split("\\.")[1]);
        return JSONObject.parseObject(context,AuthUser.class);

    }

    /**
     * 创建jwt
     */
    public static String createJWTByObj(AuthObject authObject) {
        //生成签名密钥
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Key signingKey = new SecretKeySpec(authObject.toString().getBytes(), signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setPayload(JSONObject.toJSON(authObject).toString())
                .signWith(signatureAlgorithm, signingKey);

        //生成JWT
        return builder.compact();
    }
}
