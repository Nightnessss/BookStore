package com.fehead.bookstore.controller;

import com.fehead.bookstore.dao.BookDO;
import com.fehead.bookstore.dao.mapper.BookMapper;
import com.fehead.lang.controller.BaseController;
import com.fehead.lang.response.CommonReturnType;
import org.apache.http.NameValuePair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Nightessss 2020/7/3 17:44
 */
@RestController
@RequestMapping("/bookstore/admin")
public class AdminController extends BaseController {

    @Resource
    private BookMapper bookMapper;

    @PostMapping("/book")
    public CommonReturnType submitBook(@RequestParam("book_id") String bookId,
                                       @RequestParam("book_name") String bookName,
                                       @RequestParam("book_author") String bookAuthor,
                                       @RequestParam("book_img") String bookImg,
                                       @RequestParam("book_price")BigDecimal bookPrice,
                                       @RequestParam("book_publisher") String bookPublisher,
                                       @RequestParam("book_type") String bookType,
                                       @RequestParam("book_sold") Integer bookSold,
                                       @RequestParam("book_remain") Integer bookRemain,
                                       @RequestParam("book_intro") String bookIntro) {
        Date bookUpTime = new Date();
        BookDO bookDO = new BookDO(bookId, bookName, bookAuthor, bookImg, bookPrice, bookUpTime, bookPublisher, bookType, bookSold, bookRemain, bookIntro);
        bookMapper.insert(bookDO);

        return CommonReturnType.create(null);
    }
}
