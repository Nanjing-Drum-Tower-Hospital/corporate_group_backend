package com.njglyy.corporate_group_backend.mapper.corporateGroup;

import com.njglyy.corporate_group_backend.entity.Item;
import com.njglyy.corporate_group_backend.entity.ItemDetail;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface ItemMapper {
    @Select("SELECT item_dictionary.*,manufacturer_dictionary.id as manufacturer_dictionary_id,manufacturer_dictionary.manufacturer_name," +
            "manufacturer_dictionary.pinyin_code as manufacturer_dictionary_pinyin_code   " +
            "FROM dbo.item_dictionary, manufacturer_dictionary WHERE " +
            "item_dictionary.manufacturer_id=manufacturer_dictionary.id and" +
            " code like #{code}")
    @Results({
            @Result(property = "item.id", column = "id"),
            @Result(property = "item.code", column = "code"),
            @Result(property = "item.name", column = "name"),
            @Result(property = "item.model", column = "model"),
            @Result(property = "item.unitName", column = "unit_name"),
            @Result(property = "item.sellingPrice", column = "selling_price"),
            @Result(property = "item.manufacturerId", column = "manufacturer_id"),
            @Result(property = "item.billItem", column = "bill_item"),
            @Result(property = "item.standards", column = "standards"),
            @Result(property = "item.approvalNo", column = "approval_no"),
            @Result(property = "item.type", column = "type"),
            @Result(property = "item.expireDate", column = "expire_date"),
            @Result(property = "item.createDate", column = "create_date"),
            @Result(property = "item.extendCode1", column = "extend_code1"),
            @Result(property = "item.extendCode2", column = "extend_code2"),
            @Result(property = "item.extendCode3", column = "extend_code3"),
            @Result(property = "item.extendCode4", column = "extend_code4"),
            @Result(property = "item.extendCode5", column = "extend_code5"),
            @Result(property = "item.comment1", column = "comment1"),
            @Result(property = "item.comment2", column = "comment2"),
            @Result(property = "item.comment3", column = "comment3"),
            @Result(property = "item.comment4", column = "comment4"),
            @Result(property = "item.comment5", column = "comment5"),
            @Result(property = "item.certificationUrl", column = "certification_url"),
            @Result(property = "item.pinyinCode", column = "pinyin_code"),
            @Result(property = "manufacturer.id", column = "manufacturer_dictionary_id"),
            @Result(property = "manufacturer.manufacturerName", column = "manufacturer_name"),
            @Result(property = "manufacturer.pinyinCode", column = "manufacturer_dictionary_pinyin_code"),
    })
    List<ItemDetail> queryItemByCode(@Param("code") String code);

    @Insert("INSERT INTO dbo.item_dictionary " +
            " values(#{code},#{name},#{model},#{unitName},#{sellingPrice},#{manufacturerId}," +
            "#{billItem},#{standards},#{approvalNo},#{type},#{expireDate},#{createDate},#{extendCode1}," +
            "#{extendCode2},#{extendCode3},#{extendCode4},#{extendCode5},#{comment1},#{comment2},#{comment3}," +
            "#{comment4},#{comment5},#{certificationUrl},dbo.getpy(#{name})) ")
    void addItem(String code, String name, String model, String unitName, double sellingPrice,
                 int manufacturerId,  String billItem, String standards,
                       String approvalNo, String type, LocalDate expireDate, LocalDate createDate,
                       String extendCode1, String extendCode2, String extendCode3, String extendCode4,
                       String extendCode5, String comment1, String comment2, String comment3,
                       String comment4, String comment5, String certificationUrl);

    @Update("update dbo.item_dictionary " +
            " set name = #{name}, model = #{model}, unit_name = #{unitName}, selling_price = #{sellingPrice}, " +
            " manufacturer_id = #{manufacturerId},  bill_item = #{billItem}, standards = #{standards}, " +
            " approval_no = #{approvalNo}, type = #{type}, expire_date = #{expireDate}, create_date = #{createDate}, " +
            " extend_code1 = #{extendCode1}, extend_code2 = #{extendCode2}, extend_code3 = #{extendCode3}, extend_code4 = #{extendCode4}, " +
            " extend_code5 = #{extendCode5}, comment1 = #{comment1}, comment2 = #{comment2}, comment3 = #{comment3}, " +
            " comment4 = #{comment4}, comment5 = #{comment5}, certification_url = #{certificationUrl} " +
            " where id = #{id}")
    void updateItem(String code, String name, String model, String unitName, double sellingPrice,
                 int manufacturerId,  String billItem, String standards,
                 String approvalNo, String type, LocalDate expireDate, LocalDate createDate,
                 String extendCode1, String extendCode2, String extendCode3, String extendCode4,
                 String extendCode5, String comment1, String comment2, String comment3,
                 String comment4, String comment5, String certificationUrl, int id);



    @Delete("delete from dbo.item_dictionary  " +
            " where id = #{id}")
    void deleteItem( int id);


}
