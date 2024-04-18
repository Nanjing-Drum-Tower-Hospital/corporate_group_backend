package com.njglyy.corporate_group_backend.mapper.corporateGroup;

//import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import com.njglyy.corporate_group_backend.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper  {
    @Select("select * from dbo.user_list where username=#{username} and password=#{password}")
    List<User> queryUser(String username, String password);









}
