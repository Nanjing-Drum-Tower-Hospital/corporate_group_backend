package com.njglyy.corporate_group_backend.mapper.corporateGroup;

import com.njglyy.corporate_group_backend.entity.Inbound;
import com.njglyy.corporate_group_backend.entity.Manufacturer;
import com.njglyy.corporate_group_backend.entity.Supplier;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface InboundMapper {

    @Select("select inbound_list.* , \n" +
            "supplier_dictionary.id as supplier_dictionary_id, \n" +
            "supplier_dictionary.supplier_name as supplier_dictionary_supplier_name, \n" +
            "supplier_dictionary.pinyin_code as supplier_dictionary_pinyin_code " +
            "from " +
            "inbound_list,supplier_dictionary \n" +
            "where inbound_list.supplier_id=supplier_dictionary.id \n" )
    @Results({
            @Result(property = "inboundInfo.id", column = "id"),
            @Result(property = "inboundInfo.orderNo", column = "order_no"),
            @Result(property = "inboundInfo.arrivalDate", column = "arrival_date"),
            @Result(property = "inboundInfo.supplierId", column = "supplier_id"),
            @Result(property = "inboundInfo.remark", column = "remark"),
            @Result(property = "supplier.id", column = "supplier_dictionary_id"),
            @Result(property = "supplier.supplierName", column = "supplier_dictionary_supplier_name"),
            @Result(property = "supplier.pinyinCode", column = "supplier_dictionary_pinyin_code"),
    })
    List<Inbound> queryInbound();

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
    List<Inbound> queryInboundDetail(String orderNo);

    @Insert("INSERT INTO dbo.inbound_list " +
            " values(#{orderNo}, #{arrivalDate}, #{supplierId}, #{remark})")
    void addInbound(String orderNo, LocalDate arrivalDate, int supplierId, String remark);



    @Update("UPDATE dbo.inbound_list " +
            " set order_no = #{orderNo},arrival_date=  #{arrivalDate},supplier_id = #{supplierId},remark = #{remark}" +
            "where id= #{id}")
    void updateInbound(String orderNo, LocalDate arrivalDate, int supplierId, String remark, int id);




    @Select("select * from supplier_dictionary")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "supplierName", column = "supplier_name"),
            @Result(property = "pinyinCode", column = "pinyin_code")
    })
    List<Supplier> querySupplierList();

}
