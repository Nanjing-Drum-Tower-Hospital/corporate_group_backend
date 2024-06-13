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


    @Select("SELECT outbound_list.*, \n" +
            " outbound_detail_list.id as outbound_detail_list_id "+
            " outbound_detail_list.outbound_no as outbound_detail_list_outbound_no "+
            " outbound_detail_list.inbound_detail_id "+
            "COUNT(inbound_detail_list.machine_no) AS inbound_detail_list_machine_no_count,\n"+
            "FROM outbound_list \n" +
            " JOIN outbound_detail_list ON outbound_list.outbound_no = outbound_detail_list.outbound_no \n" +
            " join inbound_detail_list on inbound_detail_id= inbound_detail_list.id \n" +
            " join item_dictionary on inbound_detail_list.item_id=item_dictionary.id " +
            "WHERE inbound_list.inbound_no = #{inboundNo} \n" +
            "GROUP BY \n" +
            "    outbound_list.inbound_no,outbound_list.inbound_date, outbound_list.supplier_id," +
            "    outbound_list.remark, outbound_list.accounting_reversal, inbound_detail_list.inbound_no, \n" +
            "    inbound_detail_list.item_id, item_dictionary.id, item_dictionary.code, \n" +
            "    item_dictionary.name, item_dictionary.model, item_dictionary.unit_name,\n" +
            "    item_dictionary.selling_price, item_dictionary.manufacturer_id,\n" +
            "    item_dictionary.bill_item, item_dictionary.standards, item_dictionary.approval_no,\n" +
            "    item_dictionary.type, item_dictionary.expire_date, item_dictionary.create_date,\n" +
            "    item_dictionary.extend_code1, item_dictionary.extend_code2, item_dictionary.extend_code3,\n" +
            "    item_dictionary.extend_code4, item_dictionary.extend_code5, item_dictionary.comment1,\n" +
            "    item_dictionary.comment2, item_dictionary.comment3, item_dictionary.comment4, \n" +
            "    item_dictionary.comment5, item_dictionary.certification_url, item_dictionary.pinyin_code,\n" +
            "    supplier_dictionary.id, supplier_dictionary.supplier_name, supplier_dictionary.pinyin_code,\n" +
            "    manufacturer_dictionary.id, manufacturer_dictionary.manufacturer_name, \n" +
            "    manufacturer_dictionary.pinyin_code " +
            "ORDER BY item_dictionary.id " +
            "OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY")
    @Results({
            @Result(property = "inboundInfo.inboundNo", column = "inbound_no"),
            @Result(property = "inboundInfo.inboundDate", column = "inbound_date"),
            @Result(property = "inboundInfo.supplierId", column = "supplier_id"),
            @Result(property = "inboundInfo.remark", column = "remark"),
            @Result(property = "inboundInfo.accountingReversal", column = "accounting_reversal"),
            @Result(property = "supplier.id", column = "supplier_dictionary_id"),
            @Result(property = "supplier.supplierName", column = "supplier_dictionary_supplier_name"),
            @Result(property = "supplier.pinyinCode", column = "supplier_dictionary_pinyin_code"),
            @Result(property = "inboundItem.id", column = "inbound_detail_list_id"),
            @Result(property = "inboundItem.inboundNo", column = "inbound_detail_list_inbound_no"),
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
