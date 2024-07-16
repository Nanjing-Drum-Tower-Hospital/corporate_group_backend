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
        System.out.println(manufacturer);
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


    @RequestMapping(value = "/queryManufacturerList", method = RequestMethod.GET)
    public Result queryManufacturerList
            () {
        try {
            List<Manufacturer> manufacturerList = manufacturerMapper.queryManufacturerList();
            return new Result(200, null, manufacturerList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Result(500, "Error querying manufacturer list: " + e.getMessage(), null);
        }
    }


    @RequestMapping(value = "/deleteManufacturer", method = RequestMethod.GET)
    public Result deleteManufacturer
            (@RequestParam(value = "id", required = false) int id
            ) {
        manufacturerMapper.deleteManufacturer(id);
        return new Result(200, "删除成功！", null);
    }
}
