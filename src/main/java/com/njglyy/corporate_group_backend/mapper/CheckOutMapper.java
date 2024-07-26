package com.njglyy.corporate_group_backend.mapper;

import com.njglyy.corporate_group_backend.entity.Inbound;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CheckOutMapper {
    @Select("select * from check_out_list where type like #{type}" )
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "type", column = "type"),
            @Result(property = "checkOutBeginDate", column = "check_out_begin_date"),
            @Result(property = "checkOutEndDate", column = "check_out_end_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "remark", column = "remark"),

    })
    List<Inbound> queryCheckOutList(String type);
}
