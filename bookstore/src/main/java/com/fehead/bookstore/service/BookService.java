package com.fehead.bookstore.service;

import com.fehead.bookstore.controller.vo.BookSimpleVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Nightessss 2020/7/1 9:22
 */
public interface BookService {
    public List<BookSimpleVO> getBooks(Pageable pageable);
}
