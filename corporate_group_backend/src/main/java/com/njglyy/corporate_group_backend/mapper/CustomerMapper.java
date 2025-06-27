package com.njglyy.corporate_group_backend.mapper;

import com.njglyy.corporate_group_backend.entity.Customer;
import com.njglyy.corporate_group_backend.entity.Inbound;
import com.njglyy.corporate_group_backend.entity.InboundDetail;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface CustomerMapper {


    @Insert("INSERT INTO dbo.customer_list " +
            " values(#{name}, #{gender}, #{phoneNumber}, #{emailAddress},#{remark})")
    void addCustomer(String name,String gender, String phoneNumber, String emailAddress,String remark);

    @Delete("DELETE FROM customer_list " +
            "WHERE id = #{id} ")
    void deleteCustomerById(@Param("id") int id);

    @Update("UPDATE dbo.customer_list " +
            " set name = #{name},gender = #{gender}, phone_number=#{phoneNumber},email_address = #{emailAddress},remark=#{remark} " +
            "where id= #{id}")
    void updateCustomer(int id, String name, String gender, String phoneNumber, String emailAddress,String remark);



    @Select("select * from customer_list " +
            "order by id desc " +
            "OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY " )
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "emailAddress", column = "email_address"),
            @Result(property = "remark", column = "remark")
    })
    List<Customer> queryCustomerList(int offset, int pageSize);

    @Select("SELECT COUNT(*) FROM customer_list")
    int queryCustomerListCount();



    @Select("select * from customer_list " +
            "where id=#{id} " )
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "emailAddress", column = "email_address"),
            @Result(property = "remark", column = "remark")
    })
    Customer queryCustomerById(int id);

    @Select("select * from customer_list " +
            "where name='%'+#{name}+'%' " )
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "emailAddress", column = "email_address"),
            @Result(property = "remark", column = "remark")
    })
    Customer queryCustomerByName(int id);

    @Select("select * from customer_list " +
            "where id=#{inputInt} or name like #{inputStr}  " )
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "emailAddress", column = "email_address"),
            @Result(property = "remark", column = "remark")
    })
    List<Customer> queryCustomerByIdOrName(int inputInt,String inputStr);



}
