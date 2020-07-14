package com.fehead.bookstore.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fehead.bookstore.dao.mapper.PasswordMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;

/**
 * @author Nightessss 2020/7/7 20:16
 */
@TableName("user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDO {

    @TableId(type = IdType.INPUT)
    private String email;
    private String nickname;

}
