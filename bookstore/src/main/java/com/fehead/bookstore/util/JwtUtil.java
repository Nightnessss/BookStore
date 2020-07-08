package com.fehead.bookstore.util;

import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author Nightessss 2020/7/8 14:56
 */
public class JwtUtil {

    public static final String SUBJECT="xdclass";
    public static final long EXPIRE=1000*60*60*24*7;
    public static final String APPSECCRET="fehead";

    /**
     * 加密生产token
     * @param username
     * @return
     */
    public static String geneJsonWebToken(String username){
        if(username == null){
            return null;
        }

        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("username",username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256,APPSECCRET).compact();

        return token ;
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static Claims checkJWT(String token) throws BusinessException {
        try{
            final Claims claims=Jwts.parser().setSigningKey(APPSECCRET)
                .parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e){
            throw new BusinessException(EmBusinessError.LOGIN_ERROR, "验证过期");
        }

    }
}
