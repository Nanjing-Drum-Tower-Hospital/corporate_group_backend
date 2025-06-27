package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.InboundDetail;
import com.njglyy.corporate_group_backend.entity.Result;
import com.njglyy.corporate_group_backend.entity.UnitRatio;
import com.njglyy.corporate_group_backend.mapper.UnitRatioMapper;
import com.njglyy.corporate_group_backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin//解决跨域问题
public class UnitRatioController {
    @Autowired
    private UnitRatioMapper unitRatioMapper;



    @RequestMapping(value = "/addUnitRatio", method = RequestMethod.POST)
    public Result addOrUpdateUnitRatio
            (@RequestBody UnitRatio unitRatio
            ) {
        if(unitRatio.getUnitNameLeft() == null || unitRatio.getUnitNameRight() == null){
            return new Result(400,"单位名称与比例不能为空！",null);
        }
        if(unitRatio.getUnitNameLeft().equals(unitRatio.getUnitNameRight())){
            return new Result(400,"单位名称不能相同！",null);
        }
        if(unitRatio.getRatio()==0){
            return new Result(400,"比例不能为0！",null);
        }
        UnitRatio unitRatioDB = unitRatioMapper.queryUnitRatioByUnitName(unitRatio.getUnitNameLeft(),unitRatio.getUnitNameRight());
        if(unitRatioDB != null){
            return new Result(400,"单位比例关系已存在！",null);
        }

        unitRatioMapper.addUnitRatio(unitRatio.getUnitNameLeft(),unitRatio.getUnitNameRight(),unitRatio.getRatio());


        return new Result(200,"添加成功！",null);
    }

    @RequestMapping(value = "/deleteUnitRatio", method = RequestMethod.GET)
    public Result deleteUnitRatio

            (@RequestParam(value = "id", required = false) int id
            ) {
        unitRatioMapper.deleteUnitRatio(id);
        return new Result(200, "删除成功！", null);
    }



    @RequestMapping(value = "/queryUnitRatioList", method = RequestMethod.GET)
    public Result queryInboundDetailMachineNoCount
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<UnitRatio> unitRatioList = unitRatioMapper.queryUnitRatioList( offset, pageSize);

        return new Result(200, null, unitRatioList);
    }


    @RequestMapping(value = "/queryUnitRatioListCount", method = RequestMethod.GET)
    public Result countInboundDetailMachineNoCount
            (
             @RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {

        int offset = (currentPage - 1) * pageSize;
        int queryUnitRatioListCount = unitRatioMapper.queryUnitRatioListCount();

        return new Result(200, null, queryUnitRatioListCount);
    }


}
