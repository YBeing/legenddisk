package com.service.legenddisk.mapper;

import com.service.legenddisk.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Insert({
        "insert into user (id, username, ",
        "password)",
        "values (#{id,jdbcType=INTEGER}, #{username,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR})"
    })
    int register(User record);
    @Select({
            "select *  from user where username= #{username} and password =#{password}"
    })
    User login(@Param("username") String username, String password);

}