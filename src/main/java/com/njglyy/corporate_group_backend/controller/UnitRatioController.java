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
