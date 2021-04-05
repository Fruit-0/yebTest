package com.yeb.server.config.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 冲动火龙果
 * @version 1.0.0
 * @ClassName JwtTokenUtil.java
 * @Description JWT工具类  todo：这个类中获取token中的声明属性的方法太low了，后面有时间可以进行更新，使用反射，即方法的invoke进行动态判断，动态取值
 * @createTime 2021年03月16日 22:46:00
 */
@Component
public class JwtTokenUtil {

    //jwt用户名
    private static final String CLAIM_KEY_USERNAME = "sub";
    //jwt的创建时间
    private static final String CLAIM_KEY_CREATED = "created";
    //jwt头部指定的签名部分加盐以后的加密算法
    private static final SignatureAlgorithm doSecretType=  SignatureAlgorithm.HS512;

    //设置jwt的加密盐（直接从application.yml文件中获取jwt的配置信息）
    @Value("${jwt.secret}")
    private String secret;
    //设置jwt超时时间
    @Value("${jwt.expiration}")
    private long expiration;





    /**
     * @Description:根据用户信息获取token令牌
     * @Author: 冲动火龙果
     * @Date: 2021/3/21 18:03
     * @param userDetails: springsecurity 的 UserDetails接口实现类
     * @return: java.lang.String
     **/
    public String generateTokenByUserDetails(UserDetails userDetails){

        //设置token的属性（其实是给jwt添加【负载】中的声明，可以是自定义的，也可以使用标准声明，使用自定义的声明）【jwt构成要素：头部（Header）；负载（Payload）；签名（signature）】

        /*  jwt标准中注册的声明
        sub: jwt所面向的用户
        aud: 接收jwt的一方
        exp: jwt的过期时间，这个过期时间必须要大于签发时间
        nbf: 定义在什么时间之前，该jwt都是不可用的.
        iat: jwt的签发时间
        jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。*/

        //标准声明对象(系统自带的标准声明对象，自带7种标准声明,有需要赋值标准对象的的约定通过这个类进行赋值)
        DefaultClaims defaultClaims = new DefaultClaims();
        //设置失效时间
        Date expirationDate = new Date(System.currentTimeMillis() + expiration * 1000);
        defaultClaims.setExpiration(expirationDate);

        //自定义声明（有需要额外进行声明的，约定通过这个map进行声明）
        Map<String, Object> claimsSelfMap = new HashMap<>();
        claimsSelfMap.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claimsSelfMap.put(CLAIM_KEY_CREATED,new Date());

        //将标准声明和自定义声明进行整合
        claimsSelfMap.putAll(defaultClaims);


        //生成一个token
        String token = generateToken(claimsSelfMap);

        return token;
    }

    /**
     * @Description:从token中获取登录用户信息
     * @Author: 冲动火龙果
     * @Date: 2021/3/21 21:14
     * @param token: token令牌
     * @return: java.lang.String
     **/
    public String getUserNameFromToken(String token){

        String userName;
        try {
            Claims claims = getClaimsFromToken(token);
            userName = claims.getSubject();
        } catch (Exception e) {
            userName = null;
        }

        return userName;
    }

    /**
     * @Description:验证token是否有效
     * @Author: 冲动火龙果
     * @Date: 2021/3/21 21:27
     * @param token: token令牌
     * @param userDetails: 实现了 UserDetails接口 的自定义用户处理类
     * @return: boolean
     **/
    public boolean validateToken(String token,UserDetails userDetails){
        //从token中获取username的信息
        String userName = getUserNameFromToken(token);
        //只有当token里的username信息和当前系统中的username信息一致，并且token未过期，才认定token有效
        boolean b = userName.equals(userDetails.getUsername()) && !istokenExpired(token);

        return b;

    }


    /**
     * @Description:判断token是否可以被刷新
     * @Author: 冲动火龙果
     * @Date: 2021/3/21 21:34
     * @param token: token令牌
     * @return: boolean
     **/
    public boolean canRefresh(String token){

        boolean b = !istokenExpired(token);

        return b;
    }

    /**
     * @Description:刷新token
     * @Author: 冲动火龙果
     * @Date: 2021/3/21 21:37
     * @param token: token令牌
     * @return: java.lang.String
     **/
    public String refreshToken(String token){

        Claims claimsFromToken = getClaimsFromToken(token);
        claimsFromToken.put(CLAIM_KEY_CREATED, new Date());
        String newToken = generateToken(claimsFromToken);

        return newToken;

    }


    //------------------------------------------私有方法分割线------------------------------------------------------------------
    //------------------------------------------私有方法分割线------------------------------------------------------------------
    //------------------------------------------私有方法分割线------------------------------------------------------------------


    /**
     * @Description:从token中获取过期时间
     * @Author: 冲动火龙果
     * @Date: 2021/3/21 21:31
     * @param token:token令牌
     * @return: java.util.Date
     **/
    private Date getExpiredDateFromToken(String token) {

        Claims claimsFromToken = getClaimsFromToken(token);
        Date expiration = claimsFromToken.getExpiration();

        return expiration;
    }


    /**
     * @Description: 从token中获取jwt荷载
     * @Author: 冲动火龙果
     * @Date: 2021/3/21 21:20
     * @param token: token令牌
     * @return: io.jsonwebtoken.Claims
     **/
    private Claims getClaimsFromToken(String token) {

        Claims claims = null;

        try {
            //使用Jwts生成JwtParser对象用于解密token
            JwtParser parser = Jwts.parser();
            //通过token和盐进行解密
            claims = parser.setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return claims;
    }


    /**
     * @Description: 根据荷载生成token
     * @Author: 冲动火龙果
     * @Date: 2021/3/21 21:10
     * @param claimsMap:  荷载
     * @return: java.lang.String
     **/
    private String generateToken(Map<String, Object> claimsMap){
        //jwt构造对象(通过声明参数的map创建)【填充jwt负载部分】
        JwtBuilder builder = Jwts.builder().setClaims(claimsMap);

        //设置jwt头部的信息(加盐以后的加密方式以及盐本身)
        String token = builder.signWith(doSecretType, secret).compact();

        return token;
    }

    /**
     * @Description:判断token是否失效
     * @Author: 冲动火龙果
     * @Date: 2021/3/21 21:28
     * @param token: token令牌
     * @return: boolean
     **/
    private boolean istokenExpired(String token) {

        Date expireDate =  getExpiredDateFromToken(token);
        boolean ifBefore = expireDate.before(new Date());

        return ifBefore;
    }

}
