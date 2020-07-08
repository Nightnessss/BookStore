package com.fehead.bookstore.service;

/**
 * @author Nightessss 2020/7/8 22:37
 */
public interface OrderService {
    public void submit(Integer bookId, Integer num, String email);
}
