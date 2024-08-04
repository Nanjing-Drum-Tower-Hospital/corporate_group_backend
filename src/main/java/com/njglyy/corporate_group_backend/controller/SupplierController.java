package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.Supplier;
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

    @RequestMapping(value = "/deleteSupplier", method = RequestMethod.GET)
    public Result deleteSupplier
            (@RequestParam(value = "id", required = false) int id
            ) {
        supplierMapper.deleteSupplier(id);
        return new Result(200, "删除成功！", null);
    }

    @RequestMapping(value = "/querySupplierList", method = RequestMethod.GET)
    public Result querySupplierList
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<Supplier> supplierList = supplierMapper.querySupplierList(offset, pageSize);
        return new Result(200, null, supplierList);
    }

    @RequestMapping(value = "/querySuppliersCount", method = RequestMethod.GET)
    public Result querySuppliersCount
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int suppliersCount = supplierMapper.querySuppliersCount();
        return new Result(200, null, suppliersCount);
    }
}
