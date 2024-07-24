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
public interface InboundMapper {


    @Insert("INSERT INTO dbo.inbound_list " +
            " values(#{inboundNo}, #{inboundDate}, #{supplierId}, #{remark},#{accountingReversalInboundNo},#{entryType})")
    void addInbound(String inboundNo, LocalDate inboundDate, int supplierId, String remark, String accountingReversalInboundNo, String entryType);

        @Delete("DELETE FROM inbound_list " +
            "WHERE inbound_no = #{inboundNo} ")
    void deleteInboundByInboundNo(@Param("inboundNo") String inboundNo);

    @Update("UPDATE dbo.inbound_list " +
            " set supplier_id = #{supplierId},remark = #{remark}, accounting_reversal_inbound_no=#{accountingReversalInboundNo},entry_type = #{entryType} " +
            "where inbound_no= #{inboundNo}")
    void updateInbound(String inboundNo, int supplierId, String remark,String accountingReversalInboundNo,String entryType);



    @Select("select * from inbound_list " +
            "order by inbound_no desc " +
            "OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY " )
    @Results({
            @Result(property = "inboundNo", column = "inbound_no", id = true),
            @Result(property = "inboundDate", column = "inbound_date"),
            @Result(property = "supplierId", column = "supplier_id"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "accountingReversalInboundNo", column = "accounting_reversal_inbound_no"),
            @Result(property = "entryType", column = "entry_type"),
            @Result(property = "supplier", column = "supplier_id",
                    one = @One(select = "com.njglyy.corporate_group_backend.mapper.SupplierMapper.querySupplierById")),
            @Result(property = "inboundDetailList", column = "inbound_no",
                    many = @Many(select = "queryInboundDetailListByInboundNo"))
    })
    List<Inbound> queryInboundList(int offset, int pageSize);

    @Select("SELECT COUNT(*) FROM inbound_list")
    int queryInboundListCount();

    @Select("select * from inbound_list " +
            "where inbound_no=#{inboundNo} " )
    @Results({
            @Result(property = "inboundNo", column = "inbound_no", id = true),
            @Result(property = "inboundDate", column = "inbound_date"),
            @Result(property = "supplierId", column = "supplier_id"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "accountingReversalInboundNo", column = "accounting_reversal_inbound_no"),
            @Result(property = "entryType", column = "entry_type"),
            @Result(property = "supplier", column = "supplier_id",
                    one = @One(select = "com.njglyy.corporate_group_backend.mapper.SupplierMapper.querySupplierById")),
            @Result(property = "inboundDetailList", column = "inbound_no",
                    many = @Many(select = "queryInboundDetailListByInboundNo"))
    })
    Inbound queryInboundByInboundNo(String inboundNo);










    @Insert("INSERT INTO dbo.inbound_detail_list " +
            " values(#{inboundNo}, #{itemId}, #{itemAmount},#{remark})")
    void addInboundDetail(String inboundNo, int itemId, BigDecimal itemAmount, String remark);


    @Delete("DELETE FROM inbound_detail_list " +
            "WHERE inbound_no = #{inboundNo} ")
    void deleteInboundDetailListByInboundNo(@Param("inboundNo") String inboundNo);

    @Delete("DELETE FROM inbound_detail_list " +
            "WHERE inbound_no = #{inboundNo} " +
            "AND item_id = #{itemId} ")
    void deleteInboundDetailByInboundNoAndItemId(@Param("inboundNo") String inboundNo,
                                                   @Param("itemId") int itemId);

    @Update("UPDATE dbo.inbound_detail_list " +
            " set inbound_no = #{inboundNo},item_id=#{itemId},item_amount=#{itemAmount},remark=#{remark} " +
            "where id= #{id}")
    void updateInboundDetailById(int id,String inboundNo, int itemId, BigDecimal itemAmount,String remark);




    @Select("select * from inbound_detail_list where inbound_no = #{inboundNo}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "inboundNo", column = "inbound_no"),
            @Result(property = "itemId", column = "item_id"),
            @Result(property = "itemAmount", column = "item_amount"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "item", column = "item_id",
                    one = @One(select = "com.njglyy.corporate_group_backend.mapper.ItemMapper.queryItemById"))
    })
    List<InboundDetail> queryInboundDetailListByInboundNo(String inboundNo, int offset, int pageSize);

    @Select("select COUNT(*) from inbound_detail_list where inbound_no = #{inboundNo}")
    int queryInboundDetailListCountByInboundNo(String inboundNo);

    @Select("select * "+
            "from " +
            "inbound_detail_list \n" +
            "where inbound_no = #{inboundNo} and item_id = #{itemId} \n" )
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "inboundNo", column = "inbound_no"),
            @Result(property = "itemId", column = "item_id"),
            @Result(property = "itemAmount", column = "item_amount"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "item", column = "item_id",
                    one = @One(select = "com.njglyy.corporate_group_backend.mapper.ItemMapper.queryItemById"))
    })
    InboundDetail queryInboundDetailListByInboundNoAndItemId(String inboundNo, int itemId);





}
