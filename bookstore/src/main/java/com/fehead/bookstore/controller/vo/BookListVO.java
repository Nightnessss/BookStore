package com.fehead.bookstore.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Nightessss 2020/7/3 22:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookListVO {

    private List<BookSimpleVO> list;
    /**
     * 当前页数
     */
    private Long current;
    /**
     * 总页数
     */
    private Long pages;
}
