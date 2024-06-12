package com.njglyy.corporate_group_backend.mapper.corporateGroup;

import com.njglyy.corporate_group_backend.entity.Inbound;
import com.njglyy.corporate_group_backend.entity.InboundInfo;
import com.njglyy.corporate_group_backend.entity.Outbound;
import com.njglyy.corporate_group_backend.entity.OutboundInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface OutboundMapper {
    @Select("select * \n"+
            "from " +
            "outbound_list \n" +
            "ORDER BY outbound_list.outbound_no  " +
            "OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY \n" )
    @Results({
            @Result(property = "outboundInfo.outboundNo", column = "outbound_no"),
            @Result(property = "outboundInfo.outboundDate", column = "outbound_date"),
            @Result(property = "outboundInfo.remark", column = "remark"),
            @Result(property = "outboundInfo.accountingReversal", column = "accounting_reversal"),
    })
    List<Outbound> queryOutboundList(int offset, int pageSize);


    @Insert("INSERT INTO dbo.outbound_list " +
            " values(#{outboundNo}, #{outboundDate},  #{remark},#{accountingReversal})")
    void addOutbound(String outboundNo, LocalDate outboundDate,  String remark, int accountingReversal);


    @Update("UPDATE dbo.outbound_list " +
            " set remark = #{remark}, accounting_reversal=#{accountingReversal} " +
            "where outbound_no= #{outboundNo}")
    void updateOutbound(String outboundNo, String remark, int accountingReversal);







    @Select("select * from outbound_list where id= #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "orderNo", column = "order_no"),
            @Result(property = "outboundDate", column = "outbound_date"),
            @Result(property = "remark", column = "remark")
    })
    OutboundInfo queryOutboundById(int id);




    @Update("UPDATE dbo.outbound_detail_list " +
            " set order_no = #{newOrderNo} " +
            "where order_no= #{oldOrderNo}")
    void updateOutboundDetailListByOrderNo(String oldOrderNo,String newOrderNo);


    @Select("select COUNT(*) "+
            "from " +
            "outbound_list \n" +
            " \n" )

    int queryOutboundCount(int offset, int pageSize);

    @Delete("DELETE FROM outbound_list " +
            "WHERE order_no = #{orderNo} ")
    void deleteOutboundListByOrderNo(@Param("orderNo") String orderNo);


    @Delete("DELETE FROM outbound_detail_list " +
            "WHERE order_no = #{orderNo} ")
    void deleteOutboundItemListByOrderNo(@Param("orderNo") String orderNo);
}
