package com.fehead.bookstore.service;

import com.fehead.bookstore.controller.vo.BookListVO;
import org.springframework.data.domain.Pageable;

/**
 * @author Nightessss 2020/7/1 9:22
 */
public interface BookService {

    /**
     * 分页获取书籍清单
     * @param pageable 分页
     * @return BookListVO
     */
    public BookListVO getBooks(Pageable pageable);
}
