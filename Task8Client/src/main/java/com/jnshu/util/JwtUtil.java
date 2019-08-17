package com.jnshu.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    //声明一个密钥,密钥保存在服务器端
    private static final String SECRET ="wangtianchihaha";
    //生成token
    public  static String createToken(String userName) throws UnsupportedEncodingException {
        //签发时间
        Date signDate=new Date();
        //签名过期时间,设置一分钟过期,获取使用默认时区和区域设置的日历。返回的日历基于具有默认格式区域设置的默认时区中的当前时间。
        Calendar nowTime=Calendar.getInstance();
        //根据日历的规则，向给定的日历字段添加或减去指定的时间量。例如，要从日历的当前时间中减去5天，可以通过调用:add(calendar)来实现
        nowTime.add(Calendar.MINUTE,1);
        //返回一个日期对象，该对象表示此日历的时间值
        Date expiresDate = nowTime.getTime();
        //头部
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("typ", "JWT");
        map.put("alg","HS256");

        return JWT.create().withHeader(map)//头部
                .withClaim("userName",userName)    //有效载体
                .withExpiresAt(expiresDate) //设置过期时间  过期时间要大于签发时间
                .withIssuedAt(signDate) //设置签发时间
                .sign(Algorithm.HMAC256(SECRET));//使用HS256进行加密
    }

    //解析token
    public static DecodedJWT parserJavaWebToken(String token) throws UnsupportedEncodingException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET))
                .build();
        return verifier.verify(token);
    }
}