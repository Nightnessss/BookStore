package com.fehead.bookstore.controller;

import com.fehead.bookstore.controller.vo.BookDetailVO;
import com.fehead.bookstore.controller.vo.BookListVO;
import com.fehead.bookstore.controller.vo.BookSimpleVO;
import com.fehead.bookstore.service.BookService;
import com.fehead.lang.controller.BaseController;
import com.fehead.lang.response.CommonReturnType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 书本相关接口控制器
 * @author Nightessss 2020/7/1 8:53
 */
@RestController
@RequestMapping("/bookstore/book")
public class BookController extends BaseController {

    @Resource
    private BookService bookService;

    @GetMapping("")
    public CommonReturnType getBooks(@PageableDefault(size = 6,page = 1) Pageable pageable,
                                     @RequestParam(value = "search", defaultValue = "") String search) {

        BookListVO bookListVO = bookService.getBooks(pageable, search);

        return CommonReturnType.create(bookListVO);
    }

    @GetMapping("/{bookId}")
    public CommonReturnType getBookDetail(@PathVariable("bookId") String bookId) {

        BookDetailVO bookDetailVO = bookService.getDetail(bookId);
        return CommonReturnType.create(bookDetailVO);
    }
}
