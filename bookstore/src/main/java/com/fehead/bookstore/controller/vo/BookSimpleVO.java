package com.fehead.bookstore.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Nightessss 2020/7/1 9:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSimpleVO {

    private String bookId;
    private String bookName;
    private String bookAuthor;
    private String bookImg;
    private BigDecimal bookPrice;
    private Integer bookSold;
}
