package com.fehead.bookstore.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nightessss 2020/7/7 22:09
 */
@TableName("password")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDO {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String email;
    private String password;

    public PasswordDO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
