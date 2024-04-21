package com.njglyy.corporate_group_backend.mapper.corporateGroup;

import com.njglyy.corporate_group_backend.entity.Inbound;
import com.njglyy.corporate_group_backend.entity.InboundDetail;
import com.njglyy.corporate_group_backend.entity.Item;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

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
            @Result(property = "inbound.id", column = "id"),
            @Result(property = "inbound.orderNo", column = "order_no"),
            @Result(property = "inbound.supplierId", column = "supplier_id"),
            @Result(property = "inbound.remark", column = "remark"),
            @Result(property = "supplier.id", column = "supplier_dictionary_id"),
            @Result(property = "supplier.supplierName", column = "supplier_dictionary_supplier_name"),
            @Result(property = "supplier.pinyinCode", column = "supplier_dictionary_pinyin_code"),
    })
    List<InboundDetail> queryInbound();

    @Select("select inbound_list.* , \n" +
            "supplier_dictionary.id as supplier_dictionary_id, \n" +
            "supplier_dictionary.supplier_name as supplier_dictionary_supplier_name, \n" +
            "supplier_dictionary.pinyin_code as supplier_dictionary_pinyin_code " +
            "inbound_detail_list.id as inbound_detail_list_id " +
            "inbound_detail_list.order_no as inbound_detail_list_order_no " +
            "inbound_detail_list.item_id as inbound_detail_item_id " +
            "inbound_detail_list.machine_no as inbound_detail_machine_no " +
            "from " +
            "inbound_list,supplier_dictionary,\n" +
            "inbound_detail_list,item_dictionary \n" +
            "where inbound_list.supplier_id=supplier_dictionary.id and\n" +
            " inbound_list.order_no=inbound_detail_list.order_no and\n" +
            "inbound_detail_list.item_id=item_dictionary.item_id")
    @Results({
            @Result(property = "inbound.id", column = "id"),
            @Result(property = "inbound.orderNo", column = "order_no"),
            @Result(property = "inbound.supplierId", column = "supplier_id"),
            @Result(property = "inbound.remark", column = "remark"),
            @Result(property = "supplier.id", column = "supplier_dictionary_id"),
            @Result(property = "supplier.supplierName", column = "supplier_dictionary_supplier_name"),
            @Result(property = "supplier.pinyinCode", column = "supplier_dictionary_pinyin_code"),

//            @Result(property = "itemId", column = "item_id"),
//            @Result(property = "machineNo", column = "machine_no"),
//            @Result(property = "id", column = "id"),
//            @Result(property = "code", column = "code"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "model", column = "model"),
//            @Result(property = "unitName", column = "unit_name"),
//            @Result(property = "sellingPrice", column = "selling_price"),
//            @Result(property = "manufacturerId", column = "manufacturer_id"),
//            @Result(property = "supplierId", column = "supplier_id"),
//            @Result(property = "billItem", column = "bill_item"),
//            @Result(property = "standards", column = "standards"),
//            @Result(property = "approvalNo", column = "approval_no"),
//            @Result(property = "type", column = "type"),
//            @Result(property = "expireDate", column = "expire_date"),
//            @Result(property = "createDate", column = "create_date"),
//            @Result(property = "extendCode1", column = "extend_code1"),
//            @Result(property = "extendCode2", column = "extend_code2"),
//            @Result(property = "extendCode3", column = "extend_code3"),
//            @Result(property = "extendCode4", column = "extend_code4"),
//            @Result(property = "extendCode5", column = "extend_code5"),
//            @Result(property = "comment1", column = "comment1"),
//            @Result(property = "comment2", column = "comment2"),
//            @Result(property = "comment3", column = "comment3"),
//            @Result(property = "comment4", column = "comment4"),
//            @Result(property = "comment5", column = "comment5"),
//            @Result(property = "certificationUrl", column = "certification_url"),
//            @Result(property = "pinyinCode", column = "pinyin_code")

    })
    List<InboundDetail> queryInboundDetail();


}
