package com.fehead.bookstore.login;


import com.fehead.bookstore.util.JwtUtil;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Nightnessss 2019/12/28 23:25
 */
@Component
@WebFilter(filterName = "JwtFilter", urlPatterns = {"/bookstore/**"})
public class JwtFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        //等到请求头信息authorization信息
        final String authHeader = request.getHeader("authorization");
        String username = null;
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res);
        } else {

            if (authHeader == null || !authHeader.startsWith("bearer ")) {

                request.getRequestDispatcher("/login/fail").forward(request, response);
                return;
            } else {
                String token = authHeader.replace("bearer ", "");
                try {
                    username = (String) JwtUtil.checkJWT(token).get("username");
                } catch (BusinessException e) {
                    request.getRequestDispatcher("/login/fail").forward(request, response);
                    return;
                }
                System.out.println(username);
            }


            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}
