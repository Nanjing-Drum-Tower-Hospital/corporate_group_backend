package com.njglyy.corporate_group_backend.mapper.corporateGroup;

import com.njglyy.corporate_group_backend.entity.Item;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ItemMapper {
    @Select("SELECT * FROM dbo.item_dictionary WHERE code like #{code}")
    @Results({
            @Result(property = "code", column = "code"),
            @Result(property = "name", column = "name"),
            @Result(property = "model", column = "model"),
            @Result(property = "unitName", column = "unit_name"),
            @Result(property = "sellingPrice", column = "selling_price"),
            @Result(property = "manufacturer", column = "manufacturer"),
            @Result(property = "company", column = "company"),
            @Result(property = "billItem", column = "bill_item"),
            @Result(property = "standards", column = "standards"),
            @Result(property = "approvalNo", column = "approval_no"),
            @Result(property = "type", column = "type"),
            @Result(property = "expireDate", column = "expire_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "extendCode1", column = "extend_code1"),
            @Result(property = "extendCode2", column = "extend_code2"),
            @Result(property = "extendCode3", column = "extend_code3"),
            @Result(property = "extendCode4", column = "extend_code4"),
            @Result(property = "extendCode5", column = "extend_code5"),
            @Result(property = "comment1", column = "comment1"),
            @Result(property = "comment2", column = "comment2"),
            @Result(property = "comment3", column = "comment3"),
            @Result(property = "comment4", column = "comment4"),
            @Result(property = "comment5", column = "comment5"),
            @Result(property = "certificationUrl", column = "certification_url"),
            @Result(property = "pinyinCode", column = "pinyin_code")
    })
    List<Item> queryItemByCode(@Param("code") String code);
}
