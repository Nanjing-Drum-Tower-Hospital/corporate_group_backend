package com.njglyy.corporate_group_backend.mapper;

import com.njglyy.corporate_group_backend.entity.Manufacturer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface ManufacturerMapper {
    @Insert("   insert into manufacturer_dictionary  values (#{manufacturerName},#{pinyinCode})")
    void addManufacturer(String manufacturerName,String pinyinCode);

    @Delete("delete from manufacturer_dictionary where id=#{id}")
    void deleteManufacturer(int id);

    @Update("   update manufacturer_dictionary  set manufacturer_name=#{manufacturerName},pinyin_code=#{pinyinCode} where id=#{id}")
    void updateManufacturer(int id,String manufacturerName,String pinyinCode);

    @Select("select * from manufacturer_dictionary " +
            "order by id "+
            "OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "manufacturerName", column = "manufacturer_name"),
            @Result(property = "pinyinCode", column = "pinyin_code")
    })
    List<Manufacturer> queryManufacturerList(int offset, int pageSize);

    @Select("SELECT COUNT(*) FROM manufacturer_dictionary")
    int queryManufacturerListCount();


    @Select("select * from manufacturer_dictionary where id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "manufacturerName", column = "manufacturer_name"),
            @Result(property = "pinyinCode", column = "pinyin_code")

    })
    Manufacturer queryManufacturerById(int id);
}
