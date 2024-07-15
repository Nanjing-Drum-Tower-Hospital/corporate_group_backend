package com.njglyy.corporate_group_backend.mapper.corporateGroup;

import com.njglyy.corporate_group_backend.entity.Item;
import com.njglyy.corporate_group_backend.entity.ItemDetail;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface ItemMapper {
    @Select("SELECT item_dictionary.*, manufacturer_dictionary.id as manufacturer_dictionary_id, manufacturer_dictionary.manufacturer_name, " +
            "manufacturer_dictionary.pinyin_code as manufacturer_dictionary_pinyin_code " +
            "FROM dbo.item_dictionary, manufacturer_dictionary " +
            "WHERE item_dictionary.manufacturer_id = manufacturer_dictionary.id " +
            "${codeSQL} " +
            "${beginDateSQL} " +
            "${endDateSQL} " +
            "ORDER BY item_dictionary.id " +
            "OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY")
    @Results({
            @Result(property = "itemDetail.id", column = "id"),
            @Result(property = "itemDetail.code", column = "code"),
            @Result(property = "itemDetail.name", column = "name"),
            @Result(property = "itemDetail.model", column = "model"),
            @Result(property = "itemDetail.unitName", column = "unit_name"),
            @Result(property = "itemDetail.unitPriceExcludingTax", column = "unit_price_excluding_tax"),
            @Result(property = "itemDetail.manufacturerId", column = "manufacturer_id"),
            @Result(property = "itemDetail.billItem", column = "bill_item"),
            @Result(property = "itemDetail.standards", column = "standards"),
            @Result(property = "itemDetail.approvalNo", column = "approval_no"),
            @Result(property = "itemDetail.type", column = "type"),
            @Result(property = "itemDetail.expireDate", column = "expire_date"),
            @Result(property = "itemDetail.createDate", column = "create_date"),
            @Result(property = "itemDetail.extendCode1", column = "extend_code1"),
            @Result(property = "itemDetail.extendCode2", column = "extend_code2"),
            @Result(property = "itemDetail.extendCode3", column = "extend_code3"),
            @Result(property = "itemDetail.extendCode4", column = "extend_code4"),
            @Result(property = "itemDetail.extendCode5", column = "extend_code5"),
            @Result(property = "itemDetail.comment1", column = "comment1"),
            @Result(property = "itemDetail.comment2", column = "comment2"),
            @Result(property = "itemDetail.comment3", column = "comment3"),
            @Result(property = "itemDetail.comment4", column = "comment4"),
            @Result(property = "itemDetail.comment5", column = "comment5"),
            @Result(property = "itemDetail.certificationUrl", column = "certification_url"),
            @Result(property = "itemDetail.pinyinCode", column = "pinyin_code"),
            @Result(property = "manufacturer.id", column = "manufacturer_dictionary_id"),
            @Result(property = "manufacturer.manufacturerName", column = "manufacturer_name"),
            @Result(property = "manufacturer.pinyinCode", column = "manufacturer_dictionary_pinyin_code"),
    })
    List<Item> queryItemsByCondition(String codeSQL, String beginDateSQL, String endDateSQL, int offset, int pageSize);


    @Select("SELECT * " +
            "FROM dbo.item_dictionary " +
            "where " +
            "item_dictionary.code = #{code}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "code", column = "code"),
            @Result(property = "name", column = "name"),
            @Result(property = "model", column = "model"),
            @Result(property = "unitName", column = "unit_name"),
            @Result(property = "unitPriceExcludingTax", column = "unit_price_excluding_tax"),
            @Result(property = "manufacturerId", column = "manufacturer_id"),
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
    ItemDetail queryItemByCode(String code);


    @Select("SELECT * " +
            "FROM dbo.item_dictionary " +
            "where " +
            "item_dictionary.code like #{input} or item_dictionary.name like #{input}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "code", column = "code"),
            @Result(property = "name", column = "name"),
            @Result(property = "model", column = "model"),
            @Result(property = "unitName", column = "unit_name"),
            @Result(property = "unitPriceExcludingTax", column = "unit_price_excluding_tax"),
            @Result(property = "manufacturerId", column = "manufacturer_id"),
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
    List<ItemDetail> queryItemByCodeOrName(String input);

    @Select("SELECT COUNT(*) " +
            "FROM dbo.item_dictionary, manufacturer_dictionary " +
            "WHERE item_dictionary.manufacturer_id = manufacturer_dictionary.id " +
            "${codeSQL} " +
            "${beginDateSQL} " +
            "${endDateSQL}")
    int queryItemsCountByCondition(String codeSQL, String beginDateSQL, String endDateSQL);

    @Insert("INSERT INTO dbo.item_dictionary " +
            " values(#{code},#{name},#{model},#{unitName},#{unitPriceExcludingTax},#{manufacturerId}," +
            "#{billItem},#{standards},#{approvalNo},#{type},#{expireDate},#{createDate},#{extendCode1}," +
            "#{extendCode2},#{extendCode3},#{extendCode4},#{extendCode5},#{comment1},#{comment2},#{comment3}," +
            "#{comment4},#{comment5},#{certificationUrl},#{pinyinCode}) ")
    void addItem(String code, String name, String model, String unitName, BigDecimal unitPriceExcludingTax,
                 int manufacturerId, String billItem, String standards,
                 String approvalNo, String type, LocalDate expireDate, LocalDate createDate,
                 String extendCode1, String extendCode2, String extendCode3, String extendCode4,
                 String extendCode5, String comment1, String comment2, String comment3,
                 String comment4, String comment5, String certificationUrl, String pinyinCode);

    @Update("update dbo.item_dictionary " +
            " set name = #{name}, model = #{model}, unit_name = #{unitName}, unit_price_excluding_tax = #{unitPriceExcludingTax}, " +
            " manufacturer_id = #{manufacturerId},  bill_item = #{billItem}, standards = #{standards}, " +
            " approval_no = #{approvalNo}, type = #{type}, expire_date = #{expireDate}, create_date = #{createDate}, " +
            " extend_code1 = #{extendCode1}, extend_code2 = #{extendCode2}, extend_code3 = #{extendCode3}, extend_code4 = #{extendCode4}, " +
            " extend_code5 = #{extendCode5}, comment1 = #{comment1}, comment2 = #{comment2}, comment3 = #{comment3}, " +
            " comment4 = #{comment4}, comment5 = #{comment5}, certification_url = #{certificationUrl},pinyin_code = #{pinyinCode} " +
            " where id = #{id}")
    void updateItem(String code, String name, String model, String unitName, BigDecimal unitPriceExcludingTax,
                    int manufacturerId, String billItem, String standards,
                    String approvalNo, String type, LocalDate expireDate, LocalDate createDate,
                    String extendCode1, String extendCode2, String extendCode3, String extendCode4,
                    String extendCode5, String comment1, String comment2, String comment3,
                    String comment4, String comment5, String certificationUrl, String pinyinCode, int id);



    @Delete("delete from dbo.item_dictionary  " +
            " where id = #{id}")
    void deleteItem( int id);







}
