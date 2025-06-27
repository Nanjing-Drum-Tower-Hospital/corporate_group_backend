package com.njglyy.corporate_group_backend.mapper;

import com.njglyy.corporate_group_backend.entity.Inbound;
import com.njglyy.corporate_group_backend.entity.UnitRatio;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UnitRatioMapper {


    @Insert("Insert into unit_ratio " +
            " values(#{unitNameLeft}, #{unitNameRight}, #{ratio})")
    void addUnitRatio(String unitNameLeft, String unitNameRight, int ratio);

    @Delete("delete from unit_ratio where id=#{id}")
    void deleteUnitRatio(int id);

    @Select("select * from unit_ratio " +
            "where (unit_name_left=#{unitNameLeft} and unit_name_right=#{unitNameRight}) or " +
            "(unit_name_left=#{unitNameRight} and unit_name_right=#{unitNameLeft})")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "unitNameLeft", column = "unit_name_left"),
            @Result(property = "unitNameRight", column = "unit_name_right"),
            @Result(property = "ratio", column = "ratio"),
    })
    UnitRatio queryUnitRatioByUnitName(String unitNameLeft, String unitNameRight);

    @Select("select * from unit_ratio " +
            "order by id  " +
            "OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY " )
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "unitNameLeft", column = "unit_name_left"),
            @Result(property = "unitNameRight", column = "unit_name_right"),
            @Result(property = "ratio", column = "ratio"),

    })
    List<UnitRatio> queryUnitRatioList(int offset, int pageSize);


    @Select("SELECT COUNT(*) FROM unit_ratio")
    int queryUnitRatioListCount();
}
