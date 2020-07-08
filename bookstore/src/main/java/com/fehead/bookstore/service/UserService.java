package com.fehead.bookstore.service;

import com.fehead.bookstore.dao.UserDO;

/**
 * @author Nightessss 2020/7/8 22:39
 */
public interface UserService {
    public String getUser(String authHeader);
}
