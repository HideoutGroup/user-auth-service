package org.hideoutgroup.user.service;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.Base64Codec;
import org.hideoutgroup.user.auth.AuthObject;
import org.hideoutgroup.user.auth.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 董文强
 * @version 1.0
 * @date 2019年01月10日
 */
@Service
public class TokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

    private static Map<String, Key> keyMap = new HashMap<>();

    /**
     * 校验Token
     *
     * @param token
     * @param
     * @return
     */
    public static int checkToken(AuthObject info, String token) {
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("jwt 为空");
        }
        if (token.split("\\.").length != 3) {
            throw new RuntimeException("jwt 格式不正确");
        }
        Jws<Claims> jwt = Jwts.parser()
                .setSigningKey(keyMap.get(info.getUserId())) // <----
                .parseClaimsJws(token);
        LOGGER.info(jwt.getSignature());
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
        String context = Base64Codec.BASE64URL.decodeToString(token.split("\\.")[1]);
        return JSONObject.parseObject(context, AuthUser.class);
    }

    /**
     * 创建jwt
     */
    public static String createJWTByObj(AuthObject authObject) {
        //生成签名密钥
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Key signingKey = new SecretKeySpec(authObject.toString().getBytes(), signatureAlgorithm.getJcaName());
        keyMap.put(authObject.getUserId(), signingKey);
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setPayload(JSONObject.toJSON(authObject).toString())
                .signWith(signatureAlgorithm, signingKey);

        //生成JWT
        return builder.compact();
    }
}
