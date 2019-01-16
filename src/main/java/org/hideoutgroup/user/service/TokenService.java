package org.hideoutgroup.user.service;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.Base64Codec;
import org.hideoutgroup.user.auth.AuthObject;
import org.hideoutgroup.user.auth.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private SigningKeyService signingKeyService;

    /**
     * 校验Token
     *
     * @param token
     * @param
     * @return
     */
    public Boolean checkToken(AuthObject info, String token) {
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("jwt 为空");
        }
        if (token.split("\\.").length != 3) {
            throw new RuntimeException("jwt 格式不正确");
        }
        Key key = signingKeyService.getKey(info.getUserId());
        if (key == null) {
            throw new RuntimeException("key 不存在");
        }
        try {
            Jws<Claims> jwt = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
            return jwt.getSignature().equals(token.split("\\.")[2]);
        } catch (SignatureException e) {
            return false;
        }

    }


    /**
     * 获取客户信息
     *
     * @param token
     * @return
     * @throws
     */
    public AuthObject getInfoByToken(String token) {
        if ((token == null) || (token.length() < 6)) {
            throw new RuntimeException("token no");
        }
        String context = Base64Codec.BASE64URL.decodeToString(token.split("\\.")[1]);
        return JSONObject.parseObject(context, AuthUser.class);
    }

    /**
     * 创建jwt
     */
    public String createJWTByObj(AuthObject authObject) {
        //生成签名密钥
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Key signingKey = new SecretKeySpec(authObject.toString().getBytes(), signatureAlgorithm.getJcaName());
        signingKeyService.addKey(authObject.getUserId(), signingKey);
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setPayload(JSONObject.toJSON(authObject).toString())
                .signWith(signatureAlgorithm, signingKey);

        //生成JWT
        return builder.compact();
    }
}
