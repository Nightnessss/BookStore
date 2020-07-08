package com.fehead.bookstore.service.impl;

import com.fehead.bookstore.service.UserService;
import com.fehead.bookstore.util.JwtUtil;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.response.CommonReturnType;
import org.springframework.stereotype.Service;

/**
 * @author Nightessss 2020/7/8 22:41
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String getUser(String authHeader) {
        String username = null;


        if (authHeader == null || !authHeader.startsWith("bearer ")) {
            return null;
        } else {
            String token = authHeader.replace("bearer ", "");
            try {
                username = (String) JwtUtil.checkJWT(token).get("username");
            } catch (BusinessException e) {
                return null;
            }
            System.out.println(username);
        }
        return username;
    }
}
