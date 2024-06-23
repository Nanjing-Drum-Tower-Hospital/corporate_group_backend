package com.njglyy.corporate_group_backend.mapper.corporateGroup;

import com.njglyy.corporate_group_backend.entity.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Result;
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

    @Select("select COUNT(*) "+
            "from " +
            "outbound_list \n" +
            " \n" )

    int queryOutboundCount(int offset, int pageSize);

    @Insert("INSERT INTO dbo.outbound_list " +
            " values(#{outboundNo}, #{outboundDate},  #{remark},#{accountingReversal})")
    void addOutbound(String outboundNo, LocalDate outboundDate,  String remark, int accountingReversal);


    @Update("UPDATE dbo.outbound_list " +
            " set remark = #{remark}, accounting_reversal=#{accountingReversal} " +
            "where outbound_no= #{outboundNo}")
    void updateOutbound(String outboundNo, String remark, int accountingReversal);


    @Select("SELECT outbound_list.*, \n" +
            "inbound_detail_list.item_id as inbound_detail_list_item_id, \n" +
            "COUNT(inbound_detail_list.machine_no) AS inbound_detail_list_machine_no_count,\n"+
            "item_dictionary.id as item_dictionary_id, \n" +
            "item_dictionary.code as item_dictionary_code, \n" +
            "item_dictionary.name as item_dictionary_name, \n" +
            "item_dictionary.model as item_dictionary_model, \n" +
            "item_dictionary.unit_name as item_dictionary_unit_name, \n" +
            "item_dictionary.selling_price as item_dictionary_selling_price, \n" +
            "item_dictionary.manufacturer_id as item_dictionary_manufacturer_id, \n" +
            "item_dictionary.bill_item as item_dictionary_bill_item, \n" +
            "item_dictionary.standards as item_dictionary_standards, \n" +
            "item_dictionary.approval_no as item_dictionary_approval_no, \n" +
            "item_dictionary.type as item_dictionary_type, \n" +
            "item_dictionary.expire_date as item_dictionary_expire_date, \n" +
            "item_dictionary.create_date as item_dictionary_create_date, \n" +
            "item_dictionary.extend_code1 as item_dictionary_extend_code1, \n" +
            "item_dictionary.extend_code2 as item_dictionary_extend_code2, \n" +
            "item_dictionary.extend_code3 as item_dictionary_extend_code3, \n" +
            "item_dictionary.extend_code4 as item_dictionary_extend_code4, \n" +
            "item_dictionary.extend_code5 as item_dictionary_extend_code5, \n" +
            "item_dictionary.comment1 as item_dictionary_comment1, \n" +
            "item_dictionary.comment2 as item_dictionary_comment2, \n" +
            "item_dictionary.comment3 as item_dictionary_comment3, \n" +
            "item_dictionary.comment4 as item_dictionary_comment4, \n" +
            "item_dictionary.comment5 as item_dictionary_comment5, \n" +
            "item_dictionary.certification_url as item_dictionary_certification_url, \n" +
            "item_dictionary.pinyin_code as item_dictionary_pinyin_code \n" +
            "FROM outbound_list \n" +
            " JOIN outbound_detail_list ON outbound_list.outbound_no = outbound_detail_list.outbound_no \n" +
            " join inbound_detail_list on inbound_detail_id= inbound_detail_list.id \n" +
            " join item_dictionary on inbound_detail_list.item_id=item_dictionary.id " +
            "WHERE outbound_list.outbound_no = #{outboundNo} \n" +
            "GROUP BY \n" +
            "    outbound_list.outbound_no,outbound_list.outbound_date," +
            "    outbound_list.remark, outbound_list.accounting_reversal, " +
            "    inbound_detail_list.item_id, item_dictionary.id, item_dictionary.code, \n" +
            "    item_dictionary.name, item_dictionary.model, item_dictionary.unit_name,\n" +
            "    item_dictionary.selling_price, item_dictionary.manufacturer_id,\n" +
            "    item_dictionary.bill_item, item_dictionary.standards, item_dictionary.approval_no,\n" +
            "    item_dictionary.type, item_dictionary.expire_date, item_dictionary.create_date,\n" +
            "    item_dictionary.extend_code1, item_dictionary.extend_code2, item_dictionary.extend_code3,\n" +
            "    item_dictionary.extend_code4, item_dictionary.extend_code5, item_dictionary.comment1,\n" +
            "    item_dictionary.comment2, item_dictionary.comment3, item_dictionary.comment4, \n" +
            "    item_dictionary.comment5, item_dictionary.certification_url, item_dictionary.pinyin_code \n" +
            "ORDER BY item_dictionary.id " +
            "OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY")
    @Results({
            @Result(property = "outboundInfo.outboundNo", column = "outbound_no"),
            @Result(property = "outboundInfo.outboundDate", column = "outbound_date"),
            @Result(property = "outboundInfo.remark", column = "remark"),
            @Result(property = "outboundInfo.accountingReversal", column = "accounting_reversal"),
            @Result(property = "inboundItem.itemId", column = "inbound_detail_list_item_id"),
            @Result(property = "inboundItem.machineNoCount", column = "inbound_detail_list_machine_no_count"),
            @Result(property = "item.itemDetail.id", column = "item_dictionary_id"),
            @Result(property = "item.itemDetail.code", column = "item_dictionary_code"),
            @Result(property = "item.itemDetail.name", column = "item_dictionary_name"),
            @Result(property = "item.itemDetail.model", column = "item_dictionary_model"),
            @Result(property = "item.itemDetail.unitName", column = "item_dictionary_unit_name"),
            @Result(property = "item.itemDetail.sellingPrice", column = "item_dictionary_selling_price"),
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
    List<Outbound> queryOutboundDetailMachineNoCount(String outboundNo, int offset, int pageSize);


    @Select(
            "SELECT COUNT(DISTINCT CONCAT_WS(',', outbound_list.outbound_no, outbound_list.outbound_date,\n" +
                    "    outbound_list.remark, outbound_list.accounting_reversal,\n" +
                    "    inbound_detail_list.item_id, item_dictionary.id, item_dictionary.code,\n" +
                    "    item_dictionary.name, item_dictionary.model, item_dictionary.unit_name,\n" +
                    "    item_dictionary.selling_price, item_dictionary.manufacturer_id,\n" +
                    "    item_dictionary.bill_item, item_dictionary.standards, item_dictionary.approval_no,\n" +
                    "    item_dictionary.type, item_dictionary.expire_date, item_dictionary.create_date,\n" +
                    "    item_dictionary.extend_code1, item_dictionary.extend_code2, item_dictionary.extend_code3,\n" +
                    "    item_dictionary.extend_code4, item_dictionary.extend_code5, item_dictionary.comment1,\n" +
                    "    item_dictionary.comment2, item_dictionary.comment3, item_dictionary.comment4,\n" +
                    "    item_dictionary.comment5, item_dictionary.certification_url, item_dictionary.pinyin_code))\n" +
                    "FROM outbound_list\n" +
                    "JOIN outbound_detail_list ON outbound_list.outbound_no = outbound_detail_list.outbound_no\n" +
                    "JOIN inbound_detail_list ON inbound_detail_id = inbound_detail_list.id\n" +
                    "JOIN item_dictionary ON inbound_detail_list.item_id = item_dictionary.id\n" +
                    "WHERE outbound_list.outbound_no = #{outboundNo}")
    int countOutboundDetailMachineNoCount(String outboundNo, int offset, int pageSize);


    @Select("select outbound_no, inbound_detail_list.* from inbound_detail_list \n" +
            "            left join outbound_detail_list on outbound_detail_list.inbound_detail_id=inbound_detail_list.id \n" +
            "WHERE item_id = #{itemId} \n" +
            "  AND inbound_detail_list.id NOT IN (\n" +
            "    SELECT inbound_detail_list.id \n" +
            "    FROM outbound_detail_list \n" +
            "    JOIN inbound_detail_list \n" +
            "      ON outbound_detail_list.inbound_detail_id = inbound_detail_list.id \n" +
            "      AND item_id = #{itemId} \n" +
            "    WHERE outbound_no != #{outboundNo}\n" +
            "  );")
    @Results({
            @Result(property = "outboundInfo.outboundNo", column = "outbound_no"),
            @Result(property = "inboundItem.id", column = "id"),
            @Result(property = "inboundItem.inboundNo", column = "inbound_no"),
            @Result(property = "inboundItem.itemId", column = "item_id"),
            @Result(property = "inboundItem.machineNo", column = "machine_no"),
    })
    List<Outbound> queryOutboundItemListWithoutOutboundByOutboundNoAndItemId(String outboundNo, int itemId);


    @Select("\n" +
            "select outbound_no, inbound_detail_list.* from outbound_detail_list \n" +
            "            join inbound_detail_list on outbound_detail_list.inbound_detail_id=inbound_detail_list.id \n" +
            "            and item_id =#{itemId} where outbound_no=#{outboundNo}")
    @Results({
            @Result(property = "outboundInfo.outboundNo", column = "outbound_no"),
            @Result(property = "inboundItem.id", column = "id"),
            @Result(property = "inboundItem.inboundNo", column = "inbound_no"),
            @Result(property = "inboundItem.itemId", column = "item_id"),
            @Result(property = "inboundItem.machineNo", column = "machine_no"),
    })
    List<Outbound> queryOutboundItemListByOutboundNoAndItemId(String outboundNo, int itemId);


    @Delete("DELETE outbound_detail_list\n" +
            "FROM outbound_detail_list\n" +
            "JOIN inbound_detail_list ON outbound_detail_list.inbound_detail_id = inbound_detail_list.id " +
            "WHERE outbound_no = #{outboundNo} and" +
            " item_id = #{itemId} and " +
            " machine_no=#{machineNo} " )
    void deleteOutboundListByOutboundNoAndItemIdAndMachineNo(String outboundNo, int itemId,String machineNo);

    @Insert("INSERT INTO dbo.outbound_detail_list " +
            " values(#{outboundNo} , #{id})")
    void addOutboundDetail(String outboundNo, int id);


    @Delete("DELETE outbound_detail_list \n" +
            "FROM outbound_detail_list \n" +
            "JOIN inbound_detail_list ON outbound_detail_list.inbound_detail_id = inbound_detail_list.id \n" +
            "WHERE outbound_detail_list.outbound_no = #{outboundNo} \n" +
            "  AND inbound_detail_list.item_id = #{itemId} \n " )
    void deleteOutboundItemListByOutboundNoAndItemId(String outboundNo,int itemId);


//    @Delete("DELETE FROM outbound_list " +
//            "WHERE order_no = #{orderNo} ")
//    void deleteOutboundListByOrderNo(@Param("orderNo") String orderNo);
//
//
//    @Delete("DELETE FROM outbound_detail_list " +
//            "WHERE order_no = #{orderNo} ")
//    void deleteOutboundItemListByOrderNo(@Param("orderNo") String orderNo);
}
