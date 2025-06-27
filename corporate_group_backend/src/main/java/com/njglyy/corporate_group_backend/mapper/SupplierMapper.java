package com.njglyy.corporate_group_backend.mapper;

import com.njglyy.corporate_group_backend.entity.Supplier;
import com.njglyy.corporate_group_backend.entity.Supplier;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SupplierMapper {
    @Insert("   insert into supplier_dictionary  values (#{supplierName},#{pinyinCode})")
    void addSupplier(String supplierName,String pinyinCode);

    @Delete("delete from supplier_dictionary where id=#{id}")
    void deleteSupplier(int id);

    @Update("   update supplier_dictionary  set supplier_name=#{supplierName},pinyin_code=#{pinyinCode} where id=#{id}")
    void updateSupplier(int id,String supplierName,String pinyinCode);

    @Select("select * from supplier_dictionary " +
            "order by id "+
            "OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "supplierName", column = "supplier_name"),
            @Result(property = "pinyinCode", column = "pinyin_code")
    })
    List<Supplier> querySupplierList(int offset, int pageSize);

    @Select("SELECT COUNT(*) FROM supplier_dictionary")
    int querySupplierListCount();


    @Select("select * from supplier_dictionary where id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "supplierName", column = "supplier_name"),
            @Result(property = "pinyinCode", column = "pinyin_code")

    })
    Supplier querySupplierById(int id);
}
