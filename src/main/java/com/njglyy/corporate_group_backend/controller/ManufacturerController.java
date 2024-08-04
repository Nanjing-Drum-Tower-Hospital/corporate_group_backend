package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.Result;
import com.njglyy.corporate_group_backend.entity.Manufacturer;
import com.njglyy.corporate_group_backend.mapper.ManufacturerMapper;
import com.njglyy.corporate_group_backend.service.PinyinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin
public class ManufacturerController {
    @Autowired
    private ManufacturerMapper manufacturerMapper;
    @Autowired
    private PinyinService pinyinService;
    @RequestMapping(value = "/addOrUpdateManufacturer", method = RequestMethod.POST)
    public Result addOrUpdateManufacturer
            (@RequestBody Manufacturer manufacturer
            ) {
        if(manufacturer.getManufacturerName() == null){
            return new Result(400,"供应商名称不能为空！",null);
        }
        else{
            if(manufacturer.getId()!=0){
                manufacturerMapper.updateManufacturer(manufacturer.getId(),manufacturer.getManufacturerName(),pinyinService.getPinyinInitials(manufacturer.getManufacturerName()));
            }
            else{
                manufacturerMapper.addManufacturer(manufacturer.getManufacturerName(),pinyinService.getPinyinInitials(manufacturer.getManufacturerName()));
            }

        }

        return new Result(200,"添加成功！",null);
    }

    @RequestMapping(value = "/deleteManufacturer", method = RequestMethod.GET)
    public Result deleteManufacturer
            (@RequestParam(value = "id", required = false) int id
            ) {
        manufacturerMapper.deleteManufacturer(id);
        return new Result(200, "删除成功！", null);
    }

    @RequestMapping(value = "/queryManufacturerList", method = RequestMethod.GET)
    public Result queryManufacturerList
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<Manufacturer> manufacturerList = manufacturerMapper.queryManufacturerList(offset, pageSize);
        return new Result(200, null, manufacturerList);
    }

    @RequestMapping(value = "/queryManufacturersCount", method = RequestMethod.GET)
    public Result queryManufacturersCount
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int manufacturersCount = manufacturerMapper.queryManufacturersCount();
        return new Result(200, null, manufacturersCount);
    }



}
