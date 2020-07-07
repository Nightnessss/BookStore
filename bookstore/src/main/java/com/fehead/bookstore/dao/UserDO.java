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
@TableName("user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDO {

    @TableId(type = IdType.AUTO)
    private Integer userId;
    private String email;
    private String nickname;
    private String password;
}
