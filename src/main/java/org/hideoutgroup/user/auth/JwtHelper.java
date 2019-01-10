package org.hideoutgroup.user.auth;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.jsonwebtoken.impl.Base64Codec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 
 * @author 董文强
 * @date 2019年01月10日
 * @version 1.0
 */
public class JwtHelper {
 private static final Logger LOGGER = LoggerFactory.getLogger(JwtHelper.class);

    /**
     * 校验Token
     * @param jwt
     * @param httpRequest
     * @return
     */
    public static int checkToken(String jwt, HttpServletRequest httpRequest){
        if (!StringUtils.isEmpty(jwt)){
            if (jwt.split("\\.").length==3) {
                LOGGER.info("jwt:" + jwt);
                String[] split = jwt.split("\\.");
                String content = split[1];
                String s = Base64Codec.BASE64URL.decodeToString(content);
                LOGGER.info("s:" + s);
                String sign = split[2];
                LOGGER.info("sign:" + sign);
                JSONObject jsonObject1 = JSONObject.parseObject(s);


                long nowMillis = System.currentTimeMillis();
                Date now = new Date(nowMillis);
                long expiresSecond = (long) jsonObject1.get("expiresSecond");

                //判断是否过期
                if(now.getTime()>expiresSecond)
                    return 2;


                TokenObject o =  JSONObject.toJavaObject(jsonObject1, TokenObject.class);

                String jwtByStr = createJWTByObj(o);
                String s2 = jwtByStr.split("\\.")[2];
                LOGGER.info("s2:" + s2);
                if (sign.equals(s2)) {
                    return 1;
                } else
                    return 0;
            }
        }
        return 0;
    }

    /**
     * 获取用户id
     * @param jwt
     * @return
     */
    public static String  getIdByJWT(String jwt){
        if (!StringUtils.isEmpty(jwt)) {
            if (jwt.split("\\.").length == 3) {
                LOGGER.info("jwt:" + jwt);
                String[] split = jwt.split("\\.");
                String content = split[1];
                String s = Base64Codec.BASE64URL.decodeToString(content);
                JSONObject jsonObject1 = JSONObject.parseObject(s);
                TokenObject o = JSONObject.toJavaObject(jsonObject1, TokenObject.class);
                return o.getUserId();
            }
        }
        return null;
    }

    /**
     * 获取客户信息
     * @param request
     * @return
     * @throws CustomException
     */
    public static String getIdByRequest(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if ((auth != null) && (auth.length() > 6)) {
            String HeadStr = auth.substring(0, 5).toLowerCase();
            if (HeadStr.compareTo("basic") == 0) {
                auth = auth.substring(6, auth.length());
                return JwtHelper.getIdByJWT(auth);
            }
        }
        return null;
    }

    public static String createJWTByObj(TokenObject tokenObject) {
        JSONObject jsonObject = JSONObject.parse(tokenObject);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(tokenObject.getBase64Secret());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setPayload(jsonObject.toString())
                .signWith(signatureAlgorithm, signingKey);

        //生成JWT
        return builder.compact();
    }
}
