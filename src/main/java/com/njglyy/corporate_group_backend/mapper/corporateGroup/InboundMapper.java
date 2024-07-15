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
public interface InboundMapper {





    @Delete("DELETE FROM inbound_list " +
            "WHERE inbound_no = #{inboundNo} ")
    void deleteInboundListByInboundNo(@Param("inboundNo") String inboundNo);


    @Delete("DELETE FROM inbound_detail_list " +
            "WHERE inbound_no = #{inboundNo} ")
    void deleteInboundItemListByInboundNo(@Param("inboundNo") String inboundNo);

    @Delete("DELETE FROM inbound_detail_list " +
            "WHERE inbound_no = #{inboundNo} " +
            "AND item_id = #{itemId} ")
    void deleteInboundItemListByInboundNoAndItemId(@Param("inboundNo") String inboundNo,
                                                                 @Param("itemId") int itemId);



    @Select("select * "+
            "from " +
            "inbound_detail_list \n" +
            "where inbound_no = #{inboundNo} and item_id = #{itemId} \n" )
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "inboundNo", column = "inbound_no"),
            @Result(property = "itemId", column = "item_id"),
            @Result(property = "itemAmount", column = "item_amount"),
    })
    InboundItem queryInboundItemListByInboundNoAndItemId(String inboundNo, int itemId);



    @Insert("INSERT INTO dbo.inbound_detail_list " +
            " values(#{inboundNo}, #{itemId}, #{itemAmount},#{remark})")
    void addInboundDetail(String inboundNo, int itemId, int itemAmount,String remark);



    @Select("select inbound_list.* , \n" +
            "supplier_dictionary.id as supplier_dictionary_id, \n" +
            "supplier_dictionary.supplier_name as supplier_dictionary_supplier_name, \n" +
            "supplier_dictionary.pinyin_code as supplier_dictionary_pinyin_code " +
            "from " +
            "inbound_list,supplier_dictionary \n" +
            "where inbound_list.supplier_id=supplier_dictionary.id " +
            "ORDER BY inbound_list.inbound_no desc " +
            "OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY \n" )
    @Results({
            @Result(property = "inboundInfo.inboundNo", column = "inbound_no"),
            @Result(property = "inboundInfo.inboundDate", column = "inbound_date"),
            @Result(property = "inboundInfo.supplierId", column = "supplier_id"),
            @Result(property = "inboundInfo.remark", column = "remark"),
            @Result(property = "inboundInfo.accountingReversalInboundNo", column = "accounting_reversal_inbound_no"),
            @Result(property = "inboundInfo.entryType", column = "entry_type"),
            @Result(property = "supplier.id", column = "supplier_dictionary_id"),
            @Result(property = "supplier.supplierName", column = "supplier_dictionary_supplier_name"),
            @Result(property = "supplier.pinyinCode", column = "supplier_dictionary_pinyin_code"),
    })
    List<Inbound> queryInboundList(int offset, int pageSize);

    @Select("select inbound_list.* , \n" +
            "supplier_dictionary.id as supplier_dictionary_id, \n" +
            "supplier_dictionary.supplier_name as supplier_dictionary_supplier_name, \n" +
            "supplier_dictionary.pinyin_code as supplier_dictionary_pinyin_code " +
            "from " +
            "inbound_list,supplier_dictionary \n" +
            "where inbound_list.supplier_id=supplier_dictionary.id " +
            "and inbound_list.inbound_no = #{inboundNo} \n" )
    @Results({
            @Result(property = "inboundInfo.inboundNo", column = "inbound_no"),
            @Result(property = "inboundInfo.inboundDate", column = "inbound_date"),
            @Result(property = "inboundInfo.supplierId", column = "supplier_id"),
            @Result(property = "inboundInfo.remark", column = "remark"),
            @Result(property = "inboundInfo.accountingReversalInboundNo", column = "accounting_reversal_inbound_no"),
            @Result(property = "inboundInfo.entryType", column = "entry_type"),
            @Result(property = "supplier.id", column = "supplier_dictionary_id"),
            @Result(property = "supplier.supplierName", column = "supplier_dictionary_supplier_name"),
            @Result(property = "supplier.pinyinCode", column = "supplier_dictionary_pinyin_code"),
    })
    Inbound queryInboundByInboundNo(String inboundNo);

    @Select("select COUNT(*) "+
            "from " +
            "inbound_list,supplier_dictionary \n" +
            "where inbound_list.supplier_id=supplier_dictionary.id " +
            " \n" )

    int queryInboundCount(int offset, int pageSize);


    @Select("SELECT \n" +
            "    inbound_list.*,\n" +
            "    supplier_dictionary.id AS supplier_dictionary_id,\n" +
            "    supplier_dictionary.supplier_name AS supplier_dictionary_supplier_name,\n" +
            "    supplier_dictionary.pinyin_code AS supplier_dictionary_pinyin_code,\n" +
            "    inbound_detail_list.id AS inbound_detail_list_id,\n" +
            "    inbound_detail_list.inbound_no AS inbound_detail_list_inbound_no,\n" +
            "    inbound_detail_list.item_id AS inbound_detail_list_item_id,\n" +
            "    inbound_detail_list.item_amount AS inbound_detail_list_item_amount,\n" +
            "    inbound_detail_list.remark AS inbound_detail_list_remark,\n" +
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
            "FROM inbound_list \n" +
            "JOIN supplier_dictionary ON inbound_list.supplier_id = supplier_dictionary.id \n" +
            "JOIN inbound_detail_list ON inbound_list.inbound_no = inbound_detail_list.inbound_no \n" +
            "JOIN item_dictionary ON inbound_detail_list.item_id = item_dictionary.id \n" +
            "JOIN manufacturer_dictionary ON item_dictionary.manufacturer_id = manufacturer_dictionary.id \n" +
            "WHERE inbound_list.inbound_no = #{inboundNo} \n" +
            "ORDER BY item_dictionary.id " +
            "OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY")
    @Results({
            @Result(property = "inboundInfo.inboundNo", column = "inbound_no"),
            @Result(property = "inboundInfo.inboundDate", column = "inbound_date"),
            @Result(property = "inboundInfo.supplierId", column = "supplier_id"),
            @Result(property = "inboundInfo.remark", column = "remark"),
            @Result(property = "inboundInfo.accountingReversalInboundNo", column = "accounting_reversal_inbound_no"),
            @Result(property = "inboundInfo.entryType", column = "entry_type"),
            @Result(property = "supplier.id", column = "supplier_dictionary_id"),
            @Result(property = "supplier.supplierName", column = "supplier_dictionary_supplier_name"),
            @Result(property = "supplier.pinyinCode", column = "supplier_dictionary_pinyin_code"),
            @Result(property = "inboundItem.id", column = "inbound_detail_list_id"),
            @Result(property = "inboundItem.inboundNo", column = "inbound_detail_list_inbound_no"),
            @Result(property = "inboundItem.itemId", column = "inbound_detail_list_item_id"),
            @Result(property = "inboundItem.itemAmount", column = "inbound_detail_list_item_amount"),
            @Result(property = "inboundItem.remark", column = "inbound_detail_list_remark"),
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
    List<Inbound> queryInboundDetailList(String inboundNo, int offset, int pageSize);



    @Select("select count(*) from inbound_list where inbound_no = #{inboundNo}")
    int countInboundDetailList(String inboundNo, int offset, int pageSize);


    @Insert("INSERT INTO dbo.inbound_list " +
            " values(#{inboundNo}, #{inboundDate}, #{supplierId}, #{remark},#{accountingReversalInboundNo},#{entryType})")
    void addInbound(String inboundNo, LocalDate inboundDate, int supplierId, String remark, String accountingReversalInboundNo,String entryType);



    @Update("UPDATE dbo.inbound_list " +
            " set supplier_id = #{supplierId},remark = #{remark}, accounting_reversal_inbound_no=#{accountingReversalInboundNo},entry_type = #{entryType} " +
            "where inbound_no= #{inboundNo}")
    void updateInbound(String inboundNo, int supplierId, String remark,String accountingReversalInboundNo,String entryType);



    @Update("UPDATE dbo.inbound_detail_list " +
            " set inbound_no = #{inboundNo},item_id=#{itemId},item_amount=#{itemAmount},remark=#{remark} " +
            "where id= #{id}")
    void updateInboundDetailById(int id,String inboundNo, int itemId, int itemAmount,String remark);



}
