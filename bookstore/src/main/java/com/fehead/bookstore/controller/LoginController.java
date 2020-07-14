package com.fehead.bookstore.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.bookstore.dao.PasswordDO;
import com.fehead.bookstore.dao.UserDO;
import com.fehead.bookstore.dao.mapper.PasswordMapper;
import com.fehead.bookstore.dao.mapper.UserMapper;
import com.fehead.bookstore.service.LoginService;
import com.fehead.bookstore.service.UserService;
import com.fehead.bookstore.util.AESUtil;
import com.fehead.bookstore.util.EmailUtil;
import com.fehead.bookstore.util.JwtUtil;
import com.fehead.lang.controller.BaseController;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.response.CommonReturnType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

/**
 * 写代码 敲快乐
 * だからよ...止まるんじゃねぇぞ
 * ▏n
 * █▏　､⺍
 * █▏ ⺰ʷʷｨ
 * █◣▄██◣
 * ◥██████▋
 * 　◥████ █▎
 * 　　███▉ █▎
 * 　◢████◣⌠ₘ℩
 * 　　██◥█◣\≫
 * 　　██　◥█◣
 * 　　█▉　　█▊
 * 　　█▊　　█▊
 * 　　█▊　　█▋
 * 　　 █▏　　█▙
 * 　　 █
 *
 * @author Nightnessss 2019/12/28 19:37
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserMapper userMapper;
    @Resource
    private PasswordMapper passwordMapper;
    @Resource
    private LoginService loginService;
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public CommonReturnType login(@RequestParam(value = "email") String username,
                                  @RequestParam(value = "password") String password) throws BusinessException {

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "账号或密码不能为空");
        }
        if (!EmailUtil.isEmail(username)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "请输入邮箱");
        }
        String pass = null;
        try {
            pass = loginService.getPassword(username);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessError.OPERATION_ILLEGAL, "用户查询异常");
        }
        String p = AESUtil.decrypt(pass, username);
        if (!StringUtils.equals(p, password)) {
            throw new BusinessException(EmBusinessError.LOGIN_ERROR, "账号或密码错误");
        }

        String token = JwtUtil.geneJsonWebToken(username);
        logger.info("LOGIN: " + username);
        return CommonReturnType.create("bearer " + token);
    }

    @PostMapping("/register")
    @Transactional(rollbackFor = BusinessException.class)
    public CommonReturnType register(@RequestParam("email") String email,
                                     @RequestParam("nickname") String nickname,
                                     @RequestParam("password") String password) throws BusinessException {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "账号或密码不能为空");
        }
        if (!EmailUtil.isEmail(email)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "请输入邮箱");
        }
        userMapper.insert(new UserDO(email, nickname));
        passwordMapper.insert(new PasswordDO(email, AESUtil.encrypt(password, email)));
        return CommonReturnType.create(null);
    }


    @GetMapping("/token")
    public CommonReturnType token(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader = request.getHeader("authorization");
        String username = userService.getUser(authHeader);
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("email", username));
        return CommonReturnType.create(userDO.getNickname());
    }

    @RequestMapping("/fail")
    public CommonReturnType fail() throws BusinessException {
        throw new BusinessException(EmBusinessError.LOGIN_ERROR, "请登录");
    }
}
