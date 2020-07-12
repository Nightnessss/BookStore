package com.fehead.bookstore.service;

import com.fehead.lang.error.BusinessException;

/**
 * @author Nightessss 2020/7/8 22:37
 */
public interface OrderService {
    public void submit(String bookId, Integer num, String email) throws BusinessException;
}
