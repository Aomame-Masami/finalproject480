package com.bn.finalp.util;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JwtUtil {

    private static int exp_time = 5*60;
    private static final String userSignature = "SD!IKJ()*&#$";

    /**
     * 生成token
     * @param map
     * @return
     */
    public static String getToken(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, exp_time);

//      创建jwt builder
        JWTCreator.Builder builder = JWT.create();

//        payload
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });

        String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(userSignature));

        return token;
    }

    /**
     * 验证 token
     */

    public static DecodedJWT verifyToken(String token) {
        return JWT.require(Algorithm.HMAC256(userSignature)).build().verify(token);

    }


}
