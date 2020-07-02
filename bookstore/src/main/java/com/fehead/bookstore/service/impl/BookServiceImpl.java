package com.fehead.bookstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fehead.bookstore.controller.vo.BookSimpleVO;
import com.fehead.bookstore.dao.BookDO;
import com.fehead.bookstore.dao.mapper.BookMapper;
import com.fehead.bookstore.service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nightessss 2020/7/1 9:23
 */
@Service
public class BookServiceImpl implements BookService {

    @Resource
    private BookMapper bookMapper;

    @Override
    public List<BookSimpleVO> getBooks(Pageable pageable) {

        Page<BookDO> bookDOPage = new Page<>(pageable.getPageNumber(), pageable.getPageSize());
        QueryWrapper<BookDO> bookDOQueryWrapper = new QueryWrapper<>();

        // 查找剩余数量大于0的书籍
        bookDOQueryWrapper.gt("book_remain", 0);
        IPage<BookDO> bookDOIPage = bookMapper.selectPage(bookDOPage, bookDOQueryWrapper);
        List<BookDO> bookDOList = bookDOIPage.getRecords();

        // 按时间逆序排序
        bookDOList.sort((m1, m2) -> m2.getBookUpTime().compareTo(m1.getBookUpTime()));

        if (bookDOList == null) {
            return null;
        }
        List<BookSimpleVO> bookSimpleVOList = new ArrayList<>();
        // 遍历bookDOList生成bookSimpleVOList
        bookDOList.forEach(bookDO -> {
            BookSimpleVO bookSimpleVO = new BookSimpleVO();
            BeanUtils.copyProperties(bookDO, bookSimpleVO);
            bookSimpleVOList.add(bookSimpleVO);
        });

        return bookSimpleVOList;
    }
}
