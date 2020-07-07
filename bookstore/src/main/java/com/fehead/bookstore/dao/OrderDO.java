package com.fehead.bookstore.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nightessss 2020/7/7 20:16
 */
@TableName("order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDO {

    public static final int DONE = 1;
    public static final int UNDONE = 0;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String bookId;
    private Integer status;
}
