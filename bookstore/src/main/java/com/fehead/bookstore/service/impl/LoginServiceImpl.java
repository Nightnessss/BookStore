package com.fehead.bookstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.bookstore.dao.PasswordDO;
import com.fehead.bookstore.dao.mapper.PasswordMapper;
import com.fehead.bookstore.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Nightessss 2020/7/7 23:22
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private PasswordMapper passwordMapper;

    @Override
    public String getPassword(String email) {
        QueryWrapper<PasswordDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        PasswordDO passwordDO = passwordMapper.selectOne(queryWrapper);
        return passwordDO.getPassword();
    }
}
