package com.njglyy.corporate_group_backend.mapper.corporateGroup;

import com.njglyy.corporate_group_backend.entity.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Result;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
@Repository
public interface OutboundMapper {
    @Delete("DELETE FROM outbound_list " +
            "WHERE outbound_no = #{outboundNo} ")
    void deleteOutboundListByOutboundNo(@Param("outboundNo") String outboundNo);


    @Delete("DELETE FROM outbound_detail_list " +
            "WHERE outbound_no = #{outboundNo} ")
    void deleteOutboundItemListByOutboundNo(@Param("outboundNo") String outboundNo);

    @Delete("DELETE FROM outbound_detail_list " +
            "WHERE outbound_no = #{outboundNo} " +
            "AND item_id = #{itemId} ")
    void deleteOutboundItemListByOutboundNoAndItemId(@Param("outboundNo") String outboundNo,
                                                   @Param("itemId") int itemId);



    @Select("select * "+
            "from " +
            "outbound_detail_list \n" +
            "where outbound_no = #{outboundNo} and item_id = #{itemId} \n" )
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "outboundNo", column = "outbound_no"),
            @Result(property = "itemId", column = "item_id"),
            @Result(property = "itemAmount", column = "item_amount"),
    })
    OutboundItem queryOutboundItemListByOutboundNoAndItemId(String outboundNo, int itemId);



    @Insert("INSERT INTO dbo.outbound_detail_list " +
            " values(#{outboundNo}, #{itemId}, #{itemAmount},#{remark})")
    void addOutboundDetail(String outboundNo, int itemId, int itemAmount,String remark);



    @Select("select outbound_list.* " +
            "from " +
            "outbound_list \n" +
            "ORDER BY outbound_list.outbound_no desc " +
            "OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY \n" )
    @Results({
            @Result(property = "outboundInfo.outboundNo", column = "outbound_no"),
            @Result(property = "outboundInfo.outboundDate", column = "outbound_date"),
            @Result(property = "outboundInfo.remark", column = "remark"),
    })
    List<Outbound> queryOutboundList(int offset, int pageSize);



    @Select("select COUNT(*) "+
            "from " +
            "outbound_list \n" +
            " \n" )

    int queryOutboundCount(int offset, int pageSize);


    @Select("SELECT \n" +
            "    outbound_list.*,\n" +
            "    outbound_detail_list.id AS outbound_detail_list_id,\n" +
            "    outbound_detail_list.outbound_no AS outbound_detail_list_outbound_no,\n" +
            "    outbound_detail_list.item_id AS outbound_detail_list_item_id,\n" +
            "    outbound_detail_list.item_amount AS outbound_detail_list_item_amount,\n" +
            "    outbound_detail_list.remark AS outbound_detail_list_remark,\n" +
            "    item_dictionary.id AS item_dictionary_id,\n" +
            "    item_dictionary.code AS item_dictionary_code,\n" +
            "    item_dictionary.name AS item_dictionary_name,\n" +
            "    item_dictionary.model AS item_dictionary_model,\n" +
            "    item_dictionary.unit_name AS item_dictionary_unit_name,\n" +
            "    item_dictionary.unit_price_excluding_tax AS item_dictionary_unit_price_excluding_tax,\n" +
            "    item_dictionary.manufacturer_id AS item_dictionary_manufacturer_id,\n" +
            "    item_dictionary.bill_item AS item_dictionary_bill_item,\n" +
            "    item_dictionary.standards AS item_dictionary_standards,\n" +
            "    item_dictionary.approval_no AS item_dictionary_approval_no,\n" +
            "    item_dictionary.type AS item_dictionary_type,\n" +
            "    item_dictionary.expire_date AS item_dictionary_expire_date,\n" +
            "    item_dictionary.create_date AS item_dictionary_create_date,\n" +
            "    item_dictionary.extend_code1 AS item_dictionary_extend_code1,\n" +
            "    item_dictionary.extend_code2 AS item_dictionary_extend_code2,\n" +
            "    item_dictionary.extend_code3 AS item_dictionary_extend_code3,\n" +
            "    item_dictionary.extend_code4 AS item_dictionary_extend_code4,\n" +
            "    item_dictionary.extend_code5 AS item_dictionary_extend_code5,\n" +
            "    item_dictionary.comment1 AS item_dictionary_comment1,\n" +
            "    item_dictionary.comment2 AS item_dictionary_comment2,\n" +
            "    item_dictionary.comment3 AS item_dictionary_comment3,\n" +
            "    item_dictionary.comment4 AS item_dictionary_comment4,\n" +
            "    item_dictionary.comment5 AS item_dictionary_comment5,\n" +
            "    item_dictionary.certification_url AS item_dictionary_certification_url,\n" +
            "    item_dictionary.pinyin_code AS item_dictionary_pinyin_code,\n" +
            "    manufacturer_dictionary.id AS manufacturer_dictionary_id,\n" +
            "    manufacturer_dictionary.manufacturer_name AS manufacturer_dictionary_manufacturer_name,\n" +
            "    manufacturer_dictionary.pinyin_code AS manufacturer_dictionary_pinyin_code\n" +
            "FROM outbound_list \n" +
            "JOIN outbound_detail_list ON outbound_list.outbound_no = outbound_detail_list.outbound_no \n" +
            "JOIN item_dictionary ON outbound_detail_list.item_id = item_dictionary.id \n" +
            "JOIN manufacturer_dictionary ON item_dictionary.manufacturer_id = manufacturer_dictionary.id \n" +
            "WHERE outbound_list.outbound_no = #{outboundNo} \n" +
            "ORDER BY item_dictionary.id " +
            "OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY")
    @Results({
            @Result(property = "outboundInfo.outboundNo", column = "outbound_no"),
            @Result(property = "outboundInfo.outboundDate", column = "outbound_date"),
            @Result(property = "outboundInfo.remark", column = "remark"),
            @Result(property = "outboundInfo.accountingReversalOutboundNo", column = "accounting_reversal_outbound_no"),
            @Result(property = "outboundItem.id", column = "outbound_detail_list_id"),
            @Result(property = "outboundItem.outboundNo", column = "outbound_detail_list_outbound_no"),
            @Result(property = "outboundItem.itemId", column = "outbound_detail_list_item_id"),
            @Result(property = "outboundItem.itemAmount", column = "outbound_detail_list_item_amount"),
            @Result(property = "outboundItem.remark", column = "outbound_detail_list_remark"),
            @Result(property = "item.itemDetail.id", column = "item_dictionary_id"),
            @Result(property = "item.itemDetail.code", column = "item_dictionary_code"),
            @Result(property = "item.itemDetail.name", column = "item_dictionary_name"),
            @Result(property = "item.itemDetail.model", column = "item_dictionary_model"),
            @Result(property = "item.itemDetail.unitName", column = "item_dictionary_unit_name"),
            @Result(property = "item.itemDetail.unitPriceExcludingTax", column = "item_dictionary_unit_price_excluding_tax"),
            @Result(property = "item.itemDetail.manufacturerId", column = "item_dictionary_manufacturer_id"),
            @Result(property = "item.itemDetail.billItem", column = "item_dictionary_bill_item"),
            @Result(property = "item.itemDetail.standards", column = "item_dictionary_standards"),
            @Result(property = "item.itemDetail.approvalNo", column = "item_dictionary_approval_no"),
            @Result(property = "item.itemDetail.type", column = "item_dictionary_type"),
            @Result(property = "item.itemDetail.expireDate", column = "item_dictionary_expire_date"),
            @Result(property = "item.itemDetail.createDate", column = "item_dictionary_create_date"),
            @Result(property = "item.itemDetail.extendCode1", column = "item_dictionary_extend_code1"),
            @Result(property = "item.itemDetail.extendCode2", column = "item_dictionary_extend_code2"),
            @Result(property = "item.itemDetail.extendCode3", column = "item_dictionary_extend_code3"),
            @Result(property = "item.itemDetail.extendCode4", column = "item_dictionary_extend_code4"),
            @Result(property = "item.itemDetail.extendCode5", column = "item_dictionary_extend_code5"),
            @Result(property = "item.itemDetail.comment1", column = "item_dictionary_comment1"),
            @Result(property = "item.itemDetail.comment2", column = "item_dictionary_comment2"),
            @Result(property = "item.itemDetail.comment3", column = "item_dictionary_comment3"),
            @Result(property = "item.itemDetail.comment4", column = "item_dictionary_comment4"),
            @Result(property = "item.itemDetail.comment5", column = "item_dictionary_comment5"),
            @Result(property = "item.itemDetail.certificationUrl", column = "item_dictionary_certification_url"),
            @Result(property = "item.itemDetail.pinyinCode", column = "item_dictionary_pinyin_code"),
            @Result(property = "item.manufacturer.id", column = "manufacturer_dictionary_id"),
            @Result(property = "item.manufacturer.manufacturerName", column = "manufacturer_dictionary_manufacturer_name"),
            @Result(property = "item.manufacturer.pinyinCode", column = "manufacturer_dictionary_pinyin_code"),
    })
    List<Outbound> queryOutboundDetailList(String outboundNo, int offset, int pageSize);



    @Select("select count(*) from outbound_list where outbound_no = #{outboundNo}")
    int countOutboundDetailList(String outboundNo, int offset, int pageSize);


    @Insert("INSERT INTO dbo.outbound_list " +
            " values(#{outboundNo}, #{outboundDate}, #{remark},#{accountingReversalOutboundNo})")
    void addOutbound(String outboundNo, LocalDate outboundDate,  String remark, String accountingReversalOutboundNo);



    @Update("UPDATE dbo.outbound_list " +
            " set ,remark = #{remark}, accounting_reversal=#{accountingReversalOutboundNo} " +
            "where outbound_no= #{outboundNo}")
    void updateOutbound(String outboundNo,  String remark,String accountingReversalOutboundNo);



    @Update("UPDATE dbo.outbound_detail_list " +
            " set outbound_no = #{outboundNo},item_id=#{itemId},item_amount=#{itemAmount},remark=#{remark} " +
            "where id= #{id}")
    void updateOutboundDetailById(int id,String outboundNo, int itemId, int itemAmount,String remark);



}
