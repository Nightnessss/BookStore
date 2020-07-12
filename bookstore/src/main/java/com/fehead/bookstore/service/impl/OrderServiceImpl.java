package com.fehead.bookstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fehead.bookstore.dao.BookDO;
import com.fehead.bookstore.dao.OrderDO;
import com.fehead.bookstore.dao.mapper.BookMapper;
import com.fehead.bookstore.dao.mapper.OrderMapper;
import com.fehead.bookstore.service.OrderService;
import com.fehead.lang.error.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Nightessss 2020/7/11 15:34
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private BookMapper bookMapper;

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void submit(String bookId, Integer num, String email) throws BusinessException {
        OrderDO orderDO = new OrderDO( email, bookId, num, OrderDO.DONE);
        orderMapper.insert(orderDO);
        BookDO bookDO = bookMapper.selectById(bookId);
        bookDO.sold(num);

        bookMapper.update(bookDO, new UpdateWrapper<BookDO>().eq("book_id", bookId));
    }
}
