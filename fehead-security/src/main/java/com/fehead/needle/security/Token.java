package com.fehead.needle.security;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Nightnessss 2020/4/28 18:17
 */
public class Token {

    public String getUserId() {
        String id = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return id;
    }
}
