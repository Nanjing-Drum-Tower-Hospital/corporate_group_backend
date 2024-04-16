package com.njglyy.corporate_group_backend.mapper.corporateGroup;


import com.njglyy.corporate_group_backend.entity.Token;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Mapper
@Repository
public interface TokenMapper {
    @Insert("insert into dbo.token_list(code,token,expiration_time) values( #{code} , #{token}, #{expiration_time}) ")
    void createToken (String code, String token, LocalDateTime expiration_time);



    @Select("select * from dbo.token_list where token=#{token} and code=#{user}")
    Token expirationCheck(String token, String user);



    @Update("update dbo.token_list set expiration_time=#{expiration_time} where token=#{token}")
    void expirationExtend(LocalDateTime expiration_time,String token);


}
