package com.njglyy.corporate_group_backend.mapper;

import com.njglyy.corporate_group_backend.entity.Supplier;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SupplierMapper {
    @Insert("   insert into supplier_dictionary  values (#{supplierName},#{pinyinCode})")
    void addSupplier(String supplierName,String pinyinCode);


    @Update("   update supplier_dictionary  set supplier_name=#{supplierName},pinyin_code=#{pinyinCode} where id=#{id}")
    void updateSupplier(int id,String supplierName,String pinyinCode);

    @Select("select * from supplier_dictionary")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "supplierName", column = "supplier_name"),
            @Result(property = "pinyinCode", column = "pinyin_code")
    })
    List<Supplier> querySupplierList();

    @Select("select * from supplier_dictionary where id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "supplierName", column = "supplier_name"),
            @Result(property = "pinyinCode", column = "pinyin_code")

    })
    Supplier querySupplierById(int id);

    @Delete("delete from supplier_dictionary where id=#{id}")
    void deleteSupplier(int id);
}
