package com.njglyy.corporate_group_backend.mapper.corporateGroup;

import com.njglyy.corporate_group_backend.entity.Item;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface ItemMapper {
    @Select("SELECT * FROM dbo.item_dictionary WHERE code like #{code}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "code", column = "code"),
            @Result(property = "name", column = "name"),
            @Result(property = "model", column = "model"),
            @Result(property = "unitName", column = "unit_name"),
            @Result(property = "sellingPrice", column = "selling_price"),
            @Result(property = "manufacturerId", column = "manufacturer_id"),
            @Result(property = "supplierId", column = "supplier_id"),
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

    @Insert("INSERT INTO dbo.item_dictionary " +
            " values(#{code},#{name},#{model},#{unitName},#{sellingPrice},#{manufacturer_id},#{supplier_id}," +
            "#{billItem},#{standards},#{approvalNo},#{type},#{expireDate},#{createDate},#{extendCode1}," +
            "#{extendCode2},#{extendCode3},#{extendCode4},#{extendCode5},#{comment1},#{comment2},#{comment3}," +
            "#{comment4},#{comment5},#{certificationUrl},dbo.getpy(#{name})) ")
    void addItem(String code, String name, String model, String unitName, double sellingPrice,
                 int manufacturerId, int supplierId, String billItem, String standards,
                       String approvalNo, String type, LocalDate expireDate, LocalDate createDate,
                       String extendCode1, String extendCode2, String extendCode3, String extendCode4,
                       String extendCode5, String comment1, String comment2, String comment3,
                       String comment4, String comment5, String certificationUrl);

    @Update("update dbo.item_dictionary " +
            " set name = #{name}, model = #{model}, unit_name = #{unitName}, selling_price = #{sellingPrice}, " +
            " manufacturer_id = #{manufacturerId}, supplier_id = #{supplierId}, bill_item = #{billItem}, standards = #{standards}, " +
            " approval_no = #{approvalNo}, type = #{type}, expire_date = #{expireDate}, create_date = #{createDate}, " +
            " extend_code1 = #{extendCode1}, extend_code2 = #{extendCode2}, extend_code3 = #{extendCode3}, extend_code4 = #{extendCode4}, " +
            " extend_code5 = #{extendCode5}, comment1 = #{comment1}, comment2 = #{comment2}, comment3 = #{comment3}, " +
            " comment4 = #{comment4}, comment5 = #{comment5}, certification_url = #{certificationUrl} " +
            " where id = #{id}")
    void updateItem(String code, String name, String model, String unitName, double sellingPrice,
                 int manufacturerId, int supplierId, String billItem, String standards,
                 String approvalNo, String type, LocalDate expireDate, LocalDate createDate,
                 String extendCode1, String extendCode2, String extendCode3, String extendCode4,
                 String extendCode5, String comment1, String comment2, String comment3,
                 String comment4, String comment5, String certificationUrl, int id);



    @Delete("delete from dbo.item_dictionary  " +
            " where id = #{id}")
    void deleteItem( int id);


}
