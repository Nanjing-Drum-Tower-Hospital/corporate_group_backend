package com.njglyy.corporate_group_backend.mapper.corporateGroup;

import com.njglyy.corporate_group_backend.entity.Inbound;
import com.njglyy.corporate_group_backend.entity.Item;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InboundMapper {

    @Select("select inbound_list.*,supplier_dictionary.supplier_name,supplier_dictionary.pinyin_code from inbound_list, supplier_dictionary\n" +
            "where inbound_list.[supplier_id]=supplier_dictionary.id")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "orderNo", column = "order_no"),
            @Result(property = "supplierId", column = "supplier_id"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "supplierName", column = "supplier_name"),
            @Result(property = "pinyinCode", column = "pinyin_code"),

    })
    List<Inbound> queryInbound();

    @Select("select inbound_list.*,supplier_dictionary.supplier_name,supplier_dictionary.pinyin_code from inbound_list, supplier_dictionary\n" +
            "where inbound_list.[supplier_id]=supplier_dictionary.id")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "orderNo", column = "order_no"),
            @Result(property = "supplierId", column = "supplier_id"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "supplierName", column = "supplier_name"),
            @Result(property = "pinyinCode", column = "pinyin_code"),

    })
    List<Inbound> queryInboundDetail();


}
