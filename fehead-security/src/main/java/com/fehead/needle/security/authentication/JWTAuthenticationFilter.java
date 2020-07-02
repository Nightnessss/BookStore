package com.fehead.needle.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.response.AuthenticationReturnType;
import com.fehead.needle.security.properties.SecurityProperties;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @author lmwis
 * @description:
 * @date 2019-08-26 09:59
 * @Version 1.0
 */

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private final SecurityProperties securityProperties;
    private final ObjectMapper objectMapper;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,SecurityProperties securityProperties, ObjectMapper objectMapper
    ) {
        super(authenticationManager);
        this.securityProperties =  securityProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(request);
        } catch (BusinessException e) {
//            response.reset();
            PrintWriter out = response.getWriter();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper
                    .writeValueAsString(AuthenticationReturnType
                            .create(e.getErrorMsg()
                                , HttpStatus.UNAUTHORIZED.value())));
            return;
        }


        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws BusinessException {
        String token = request.getHeader("Authorization");
        if (token != null) {
            // parse the token.
            String user = null;
            try {
                user = Jwts.parser()
                        .setSigningKey(securityProperties.getBrowser().getJwtKey())
                        .parseClaimsJws(token.replace("Bearer ", ""))
                        .getBody()
                        .getSubject();
            } catch (SignatureException se){
                throw new BusinessException(EmBusinessError.WRONG_TOKEN);
            } catch (ExpiredJwtException e) {
                throw new BusinessException(EmBusinessError.JWT_TOKEN_EXPIRED);
            }
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }

}
