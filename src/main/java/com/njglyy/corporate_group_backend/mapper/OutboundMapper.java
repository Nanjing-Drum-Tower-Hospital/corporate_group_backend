package com.njglyy.corporate_group_backend.mapper;

import com.njglyy.corporate_group_backend.entity.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Result;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface OutboundMapper {


    @Insert("INSERT INTO dbo.outbound_list " +
            " values(#{outboundNo}, #{outboundDate},  #{remark},#{accountingReversalOutboundNo},#{entryType})")
    void addOutbound(String outboundNo, LocalDate outboundDate,  String remark, String accountingReversalOutboundNo, String entryType);

    @Delete("DELETE FROM outbound_list " +
            "WHERE outbound_no = #{outboundNo} ")
    void deleteOutboundByOutboundNo(@Param("outboundNo") String outboundNo);

    @Update("UPDATE dbo.outbound_list " +
            " set remark = #{remark}, accounting_reversal_outbound_no=#{accountingReversalOutboundNo},entry_type = #{entryType} " +
            "where outbound_no= #{outboundNo}")
    void updateOutbound(String outboundNo,  String remark,String accountingReversalOutboundNo,String entryType);



    @Select("select * from outbound_list " +
            "order by outbound_no desc " +
            "OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY " )
    @Results({
            @Result(property = "outboundNo", column = "outbound_no", id = true),
            @Result(property = "outboundDate", column = "outbound_date"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "accountingReversalOutboundNo", column = "accounting_reversal_outbound_no"),
            @Result(property = "entryType", column = "entry_type"),
            @Result(property = "outboundDetailList", column = "outbound_no",
                    many = @Many(select = "queryOutboundDetailListByOutboundNo")),
            @Result(property = "checkOut", column = "outbound_date",
                    one = @One(select = "com.njglyy.corporate_group_backend.mapper.CheckOutMapper.queryOutboundValidCheckOutByDate")
            ),
    })
    List<Outbound> queryOutboundList(int offset, int pageSize);

    @Select("SELECT COUNT(*) FROM outbound_list")
    int queryOutboundListCount();

    @Select("select * from outbound_list " +
            "where outbound_no=#{outboundNo} " )
    @Results({
            @Result(property = "outboundNo", column = "outbound_no", id = true),
            @Result(property = "outboundDate", column = "outbound_date"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "accountingReversalOutboundNo", column = "accounting_reversal_outbound_no"),
            @Result(property = "entryType", column = "entry_type"),
            @Result(property = "outboundDetailList", column = "outbound_no",
                    many = @Many(select = "queryOutboundDetailListByOutboundNo"))
    })
    Outbound queryOutboundByOutboundNo(String outboundNo);










    @Insert("INSERT INTO dbo.outbound_detail_list " +
            " values(#{outboundNo}, #{itemId}, #{itemAmount},#{remark})")
    void addOutboundDetail(String outboundNo, int itemId, BigDecimal itemAmount, String remark);


    @Delete("DELETE FROM outbound_detail_list " +
            "WHERE outbound_no = #{outboundNo} ")
    void deleteOutboundDetailListByOutboundNo(@Param("outboundNo") String outboundNo);

    @Delete("DELETE FROM outbound_detail_list " +
            "WHERE outbound_no = #{outboundNo} " +
            "AND item_id = #{itemId} ")
    void deleteOutboundDetailByOutboundNoAndItemId(@Param("outboundNo") String outboundNo,
                                                 @Param("itemId") int itemId);

    @Update("UPDATE dbo.outbound_detail_list " +
            " set outbound_no = #{outboundNo},item_id=#{itemId},item_amount=#{itemAmount},remark=#{remark} " +
            "where id= #{id}")
    void updateOutboundDetailById(int id,String outboundNo, int itemId, BigDecimal itemAmount,String remark);




    @Select("select * from outbound_detail_list where outbound_no = #{outboundNo}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "outboundNo", column = "outbound_no"),
            @Result(property = "itemId", column = "item_id"),
            @Result(property = "itemAmount", column = "item_amount"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "item", column = "item_id",
                    one = @One(select = "com.njglyy.corporate_group_backend.mapper.ItemMapper.queryItemById"))
    })
    List<OutboundDetail> queryOutboundDetailListByOutboundNo(String outboundNo, int offset, int pageSize);

    @Select("select COUNT(*) from outbound_detail_list where outbound_no = #{outboundNo}")
    int queryOutboundDetailListCountByOutboundNo(String outboundNo);

    @Select("select * "+
            "from " +
            "outbound_detail_list \n" +
            "where outbound_no = #{outboundNo} and item_id = #{itemId} \n" )
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "outboundNo", column = "outbound_no"),
            @Result(property = "itemId", column = "item_id"),
            @Result(property = "itemAmount", column = "item_amount"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "item", column = "item_id",
                    one = @One(select = "com.njglyy.corporate_group_backend.mapper.ItemMapper.queryItemById"))
    })
    OutboundDetail queryOutboundDetailListByOutboundNoAndItemId(String outboundNo, int itemId);


    @Select("\n" +
            "   SELECT \n" +
            "    COALESCE(\n" +
            "        (SELECT SUM(item_amount)\n" +
            "         FROM inbound_detail_list\n" +
            "         WHERE item_id = #{itemId} ),\n" +
            "        0) -\n" +
            "    COALESCE(\n" +
            "        (SELECT SUM(item_amount)\n" +
            "         FROM outbound_detail_list\n" +
            "         WHERE item_id = #{itemId} ),\n" +
            "        0) ")
    int queryExistingInventoryAmount(int itemId);





}
