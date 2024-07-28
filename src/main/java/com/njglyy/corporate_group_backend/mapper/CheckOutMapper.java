package com.njglyy.corporate_group_backend.mapper;

import com.njglyy.corporate_group_backend.entity.CheckOut;
import com.njglyy.corporate_group_backend.entity.Inbound;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface CheckOutMapper {

    @Insert("Insert into check_out_list " +
            " values(#{type}, #{checkOutBeginDate}, #{checkOutEndDate}, #{createDate}, #{remark}, #{valid})")
    void addCheckOut(String type, LocalDate checkOutBeginDate, LocalDate checkOutEndDate, LocalDate createDate, String remark,String valid);

    @Update("update check_out_list set validity= 'invalid' where id=#{id}")
    void cancelCheckOut(int id);

    @Select("select * from check_out_list where type like 'inbound' and validity= 'valid' " +
            "and #{date} between check_out_begin_date and check_out_end_date " +
            "order by id desc " +
            "OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY " )
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "type", column = "type"),
            @Result(property = "checkOutBeginDate", column = "check_out_begin_date"),
            @Result(property = "checkOutEndDate", column = "check_out_end_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "validity", column = "validity"),

    })
    CheckOut queryInboundValidCheckOutByDate(LocalDate date);


    @Select("select * from check_out_list where type like 'outbound' and validity= 'valid' " +
            "and #{date} between check_out_begin_date and check_out_end_date " +
            "order by id desc " +
            "OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY " )
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "type", column = "type"),
            @Result(property = "checkOutBeginDate", column = "check_out_begin_date"),
            @Result(property = "checkOutEndDate", column = "check_out_end_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "validity", column = "validity"),

    })
    CheckOut queryOutboundValidCheckOutByDate(LocalDate date);

    @Select("select * from check_out_list where type like #{type} and validity= 'valid' " +
            "order by id desc " +
            "OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY " )
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "type", column = "type"),
            @Result(property = "checkOutBeginDate", column = "check_out_begin_date"),
            @Result(property = "checkOutEndDate", column = "check_out_end_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "validity", column = "validity"),

    })
    CheckOut queryLastValidCheckOut(String type,int offset,int pageSize);

    @Select("select * from check_out_list where type like #{type} " +
            "order by id desc " +
            "OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY " )
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "type", column = "type"),
            @Result(property = "checkOutBeginDate", column = "check_out_begin_date"),
            @Result(property = "checkOutEndDate", column = "check_out_end_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "validity", column = "validity"),

    })
    List<CheckOut> queryCheckOutList(String type,int offset,int pageSize);





    @Select("SELECT COUNT(*) FROM check_out_list where type like #{type}")
    int queryCheckOutListCount(String type);
}
