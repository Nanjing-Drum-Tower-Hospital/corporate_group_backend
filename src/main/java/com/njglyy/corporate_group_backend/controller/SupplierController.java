package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.Result;
import com.njglyy.corporate_group_backend.entity.Supplier;
import com.njglyy.corporate_group_backend.mapper.SupplierMapper;
import com.njglyy.corporate_group_backend.service.PinyinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class SupplierController {
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private PinyinService pinyinService;
    @RequestMapping(value = "/addOrUpdateSupplier", method = RequestMethod.POST)
    public Result addOrUpdateSupplier
            (@RequestBody Supplier supplier
             ) {
        System.out.println(supplier);
        if(supplier.getSupplierName() == null){
            return new Result(400,"供应商名称不能为空！",null);
        }
        else{
            if(supplier.getId()!=0){
                supplierMapper.updateSupplier(supplier.getId(),supplier.getSupplierName(),pinyinService.getPinyinInitials(supplier.getSupplierName()));
            }
            else{
                supplierMapper.addSupplier(supplier.getSupplierName(),pinyinService.getPinyinInitials(supplier.getSupplierName()));
            }

        }

        return new Result(200,"添加成功！",null);
    }


    @RequestMapping(value = "/querySupplierList", method = RequestMethod.GET)
    public Result querySupplierList
            () {
        try {
            List<Supplier> supplierList = supplierMapper.querySupplierList();
            return new Result(200, null, supplierList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Result(500, "Error querying manufacturer list: " + e.getMessage(), null);
        }
    }


    @RequestMapping(value = "/deleteSupplier", method = RequestMethod.GET)
    public Result deleteSupplier
            (@RequestParam(value = "id", required = false) int id
            ) {
        supplierMapper.deleteSupplier(id);
        return new Result(200, "删除成功！", null);
    }
}
