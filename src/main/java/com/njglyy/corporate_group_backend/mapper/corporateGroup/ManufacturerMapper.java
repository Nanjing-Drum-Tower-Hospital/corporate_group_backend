package com.njglyy.corporate_group_backend.mapper.corporateGroup;

import com.njglyy.corporate_group_backend.entity.Manufacturer;
import com.njglyy.corporate_group_backend.entity.Manufacturer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface ManufacturerMapper {
    @Insert("   insert into manufacturer_dictionary  values (#{manufacturerName},#{pinyinCode})")
    void addManufacturer(String manufacturerName,String pinyinCode);


    @Update("   update manufacturer_dictionary  set manufacturer_name=#{manufacturerName},pinyin_code=#{pinyinCode} where id=#{id}")
    void updateManufacturer(int id,String manufacturerName,String pinyinCode);

    @Select("select * from manufacturer_dictionary")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "manufacturerName", column = "manufacturer_name"),
            @Result(property = "pinyinCode", column = "pinyin_code")
    })
    List<Manufacturer> queryManufacturerList();

    @Delete("delete from manufacturer_dictionary where id=#{id}")
    void deleteManufacturer(int id);
}
