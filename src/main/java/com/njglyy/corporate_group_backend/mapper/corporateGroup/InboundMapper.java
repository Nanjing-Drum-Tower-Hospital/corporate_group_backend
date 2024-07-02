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

    @Delete("DELETE FROM inbound_detail_list " +
            "WHERE inbound_no = #{inboundNo} " +
            "AND item_id = #{itemId} " +
            "AND machine_no NOT IN (${machineNumbers})")
    void deleteInboundDetailsByInboundNoAndItemIdAndMachineNoNotIn(@Param("inboundNo") String inboundNo,
                                                                 @Param("itemId") int itemId,
                                                                 @Param("machineNumbers") String machineNumbers);


    @Select("SELECT * FROM inbound_detail_list " +
            "WHERE inbound_no = #{inboundNo} " +
            "AND item_id = #{itemId} " +
            "AND machine_no = #{machineNo}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "inboundNo", column = "inbound_no"),
            @Result(property = "itemId", column = "item_id"),
            @Result(property = "machineNo", column = "machine_no"),

    })
    List<InboundItem> queryInboundDetailsByInboundNoAndItemIdAndMachineNo(@Param("inboundNo") String inboundNo,
                                                                 @Param("itemId") int itemId,
                                                                 @Param("machineNo") String machineNo);



    @Select("select * "+
            "from " +
            "inbound_detail_list \n" +
            "where inbound_no = #{inboundNo} and item_id = #{itemId} \n" )
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "inboundNo", column = "inbound_no"),
            @Result(property = "itemId", column = "item_id"),
            @Result(property = "machineNo", column = "machine_no"),

    })
    List<InboundItem> queryInboundItemListByInboundNoAndItemId(String inboundNo, int itemId);



    @Insert("INSERT INTO dbo.inbound_detail_list " +
            " values(#{inboundNo}, #{itemId}, #{machineNo})")
    void addInboundDetail(String inboundNo, int itemId, String machineNo);



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
            @Result(property = "supplier.id", column = "supplier_dictionary_id"),
            @Result(property = "supplier.supplierName", column = "supplier_dictionary_supplier_name"),
            @Result(property = "supplier.pinyinCode", column = "supplier_dictionary_pinyin_code"),
    })
    List<Inbound> queryInboundList(int offset, int pageSize);



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
            "    inbound_detail_list.inbound_no AS inbound_detail_list_inbound_no,\n" +
            "    inbound_detail_list.item_id AS inbound_detail_list_item_id,\n" +
            "    COUNT(inbound_detail_list.machine_no) AS inbound_detail_list_machine_no_count,\n" +
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
            "GROUP BY \n" +
            "    inbound_list.inbound_no,inbound_list.inbound_date, inbound_list.supplier_id," +
            "    inbound_list.remark, inbound_list.accounting_reversal, inbound_detail_list.inbound_no, \n" +
            "    inbound_detail_list.item_id, item_dictionary.id, item_dictionary.code, \n" +
            "    item_dictionary.name, item_dictionary.model, item_dictionary.unit_name,\n" +
            "    item_dictionary.unit_price_excluding_tax, item_dictionary.manufacturer_id,\n" +
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
    List<Inbound> queryInboundDetailMachineNoCount(String inboundNo, int offset, int pageSize);



    @Select("SELECT COUNT(*) AS count_twice\n" +
            "FROM (\n" +
            "    SELECT COUNT(machine_no) AS count_once\n" +
            "    FROM inbound_detail_list\n" +
            "    WHERE inbound_no = #{inboundNo}\n" +
            "    GROUP BY item_id\n" +
            ") AS subquery_alias;")
    int countInboundDetailMachineNoCount(String inboundNo, int offset, int pageSize);

    @Select("select inbound_list.*, \n" +
            "supplier_dictionary.id as supplier_dictionary_id, \n" +
            "supplier_dictionary.supplier_name as supplier_dictionary_supplier_name, \n" +
            "supplier_dictionary.pinyin_code as supplier_dictionary_pinyin_code, \n" +
            "inbound_detail_list.id as inbound_detail_list_id, \n" +
            "inbound_detail_list.order_no as inbound_detail_list_order_no, \n" +
            "inbound_detail_list.item_id as inbound_detail_list_item_id, \n" +
            "inbound_detail_list.machine_no as inbound_detail_list_machine_no, \n" +
            "item_dictionary.id as item_dictionary_id, \n" +
            "item_dictionary.code as item_dictionary_code, \n" +
            "item_dictionary.name as item_dictionary_name, \n" +
            "item_dictionary.model as item_dictionary_model, \n" +
            "item_dictionary.unit_name as item_dictionary_unit_name, \n" +
            "item_dictionary.unit_price_excluding_tax as item_dictionary_unit_price_excluding_tax, \n" +
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
            "item_dictionary.pinyin_code as item_dictionary_pinyin_code, \n" +
            "manufacturer_dictionary.id as manufacturer_dictionary_id," +
            "manufacturer_dictionary.manufacturer_name as manufacturer_dictionary_manufacturer_name," +
            "manufacturer_dictionary.pinyin_code as manufacturer_dictionary_pinyin_code   " +
            "from inbound_list \n" +
            "join supplier_dictionary on inbound_list.supplier_id = supplier_dictionary.id \n" +
            "join inbound_detail_list on inbound_list.order_no = inbound_detail_list.order_no \n" +
            "join item_dictionary on inbound_detail_list.item_id = item_dictionary.id \n" +
            "join manufacturer_dictionary on item_dictionary.manufacturer_id=manufacturer_dictionary.id \n" +
            "where inbound_list.order_no=#{orderNo}")
    @Results({
            @Result(property = "inboundInfo.id", column = "id"),
            @Result(property = "inboundInfo.orderNo", column = "order_no"),
            @Result(property = "inboundInfo.arrivalDate", column = "arrival_date"),
            @Result(property = "inboundInfo.supplierId", column = "supplier_id"),
            @Result(property = "inboundInfo.remark", column = "remark"),
            @Result(property = "supplier.id", column = "supplier_dictionary_id"),
            @Result(property = "supplier.supplierName", column = "supplier_dictionary_supplier_name"),
            @Result(property = "supplier.pinyinCode", column = "supplier_dictionary_pinyin_code"),
            @Result(property = "inboundItem.id", column = "inbound_detail_list_id"),
            @Result(property = "inboundItem.orderNo", column = "inbound_detail_list_order_no"),
            @Result(property = "inboundItem.itemId", column = "inbound_detail_list_item_id"),
            @Result(property = "inboundItem.machineNo", column = "inbound_detail_list_machine_no"),
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
    List<Inbound> queryInboundDetail(String orderNo);

    @Insert("INSERT INTO dbo.inbound_list " +
            " values(#{inboundNo}, #{inboundDate}, #{supplierId}, #{remark},#{accountingReversal})")
    void addInbound(String inboundNo, LocalDate inboundDate, int supplierId, String remark, int accountingReversal);



    @Update("UPDATE dbo.inbound_list " +
            " set supplier_id = #{supplierId},remark = #{remark}, accounting_reversal=#{accountingReversal} " +
            "where inbound_no= #{inboundNo}")
    void updateInbound(String inboundNo, int supplierId, String remark,int accountingReversal);

    @Select("select * from inbound_list where id= #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "orderNo", column = "order_no"),
            @Result(property = "arrivalDate", column = "arrival_date"),
            @Result(property = "supplierId", column = "supplier_id"),
            @Result(property = "remark", column = "remark")
    })
    InboundInfo queryInboundById(int id);

    @Update("UPDATE dbo.inbound_detail_list " +
            " set order_no = #{newOrderNo} " +
            "where order_no= #{oldOrderNo}")
    void updateInboundDetailListByOrderNo(String oldOrderNo,String newOrderNo);

    @Select("select * from supplier_dictionary")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "supplierName", column = "supplier_name"),
            @Result(property = "pinyinCode", column = "pinyin_code")
    })
    List<Supplier> querySupplierList();

}
