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
            @Result(property = "companyName", column = "company_name"),
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

    @Insert("INSERT INTO dbo.item_dictionary" +
            "set values(#{code},#{name},#{model},#{unitName},#{sellingPrice},#{manufacturer},#{companyName}," +
            "#{billItem},#{standards},#{approvalNo},#{type},#{expireDate},#{createDate},#{extendCode1}," +
            "#{extendCode2},#{extendCode3},#{extendCode4},#{extendCode5},#{comment1},#{comment2},#{comment3}," +
            "#{comment4},#{comment5},#{certificationUrl},#{pinyinCode} ")
    List<Item> AddItem(String code, String name, String model, String unitName, String sellingPrice,
                       String manufacturer, String companyName, String billItem, String standards,
                       String approvalNo, String type, String expireDate, String createDate,
                       String extendCode1, String extendCode2, String extendCode3, String extendCode4,
                       String extendCode5, String comment1, String comment2, String comment3,
                       String comment4, String comment5, String certificationUrl, String pinyinCode);
}
