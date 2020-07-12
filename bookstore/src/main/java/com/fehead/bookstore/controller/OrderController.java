package com.fehead.bookstore.controller;

import com.alibaba.fastjson.JSON;
import com.fehead.bookstore.service.OrderService;
import com.fehead.bookstore.service.UserService;
import com.fehead.bookstore.service.model.Goods;
import com.fehead.bookstore.util.JwtUtil;
import com.fehead.lang.controller.BaseController;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.response.CommonReturnType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Nightessss 2020/7/8 22:12
 */
@RestController
@RequestMapping("/bookstore/order")
public class OrderController extends BaseController {

    @Resource
    private OrderService orderService;
    @Resource
    private UserService userService;

    @PostMapping("/order")
    public CommonReturnType order(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestParam("goods") String goodsStr) throws BusinessException {
        String authHeader = request.getHeader("authorization");
        String username = userService.getUser(authHeader);
        List<Goods> list = JSON.parseArray(goodsStr, Goods.class);
        for (Goods good : list) {
            orderService.submit(good.getGoodId(), good.getGoodNum(), username);
        }
        return CommonReturnType.create(null);
    }
}
