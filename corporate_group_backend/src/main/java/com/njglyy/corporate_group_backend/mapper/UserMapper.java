package com.njglyy.corporate_group_backend.mapper;

//import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import com.njglyy.corporate_group_backend.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper  {
    @Select("select * from dbo.user_list where username=#{username} and password=#{password}")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "fullName", column = "full_name"),
    })
    List<User> queryUser(String username, String password);


    @Select("select * from dbo.user_list where username=#{username}")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "fullName", column = "full_name"),
    })
    User queryUserByUsername(String username);









}
