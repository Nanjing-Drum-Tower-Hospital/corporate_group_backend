package com.njglyy.corporate_group_backend.mapper;

import com.njglyy.corporate_group_backend.entity.Inbound;
import com.njglyy.corporate_group_backend.entity.UnitRatio;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UnitRatioMapper {
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
