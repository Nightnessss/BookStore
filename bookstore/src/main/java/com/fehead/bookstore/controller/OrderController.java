package com.fehead.bookstore.controller;

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

/**
 * @author Nightessss 2020/7/8 22:12
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Resource
    private OrderService orderService;
    @Resource
    private UserService userService;

    @PostMapping("/order")
    public CommonReturnType order(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestParam("list") Goods[] list) {
        String authHeader = request.getHeader("authorization");
        String username = userService.getUser(authHeader);
        for (Goods o:list) {
            orderService.submit(o.getGoodId(), o.getGoodNum(), username);
        }
        return CommonReturnType.create(null);
    }
}
