package com.njglyy.corporate_group_backend.controller;
import com.njglyy.corporate_group_backend.entity.Item;
import com.njglyy.corporate_group_backend.entity.Result;
import com.njglyy.corporate_group_backend.mapper.ItemMapper;
import com.njglyy.corporate_group_backend.service.PinyinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
public class ItemController {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private PinyinService pinyinService;
    @RequestMapping(value = "/queryItemByCodeOrName", method = RequestMethod.GET)
    public Result queryItemByCodeOrName
            (@RequestParam(value = "input", required = false) String input
            ) {
        String inputStr = "%"+input+"%";

        List<Item> itemDetailList = itemMapper.queryItemByCodeOrName(inputStr);
        return new Result(200,null,itemDetailList);
    }


    @RequestMapping(value = "/queryItemList", method = RequestMethod.GET)
    public Result queryItemList
            (@RequestParam(value = "code", required = false) String code,
             @RequestParam(value = "beginDate", required = false)
             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
             @RequestParam(value = "endDate", required = false)
             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
             @RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize

            ) {

        String codeSQL="";
        String beginDateSQL="";
        String endDateSQL="";
        if(code!=null){
            codeSQL="and code like '"+code+"%' ";
        }
        if(beginDate!=null){
            beginDateSQL="and item_dictionary.create_date >= '"+beginDate+"' ";
        }
        if(endDate!=null){
            endDateSQL="and item_dictionary.create_date <= '"+endDate+"' ";
        }

        int offset = (currentPage - 1) * pageSize;
        List<Item> itemList = itemMapper.queryItemListByCondition(codeSQL, beginDateSQL, endDateSQL, offset, pageSize);


        return new Result(200,null,itemList);
    }




    @RequestMapping(value = "/queryItemListCount", method = RequestMethod.GET)
    public Result queryItemListCountByCondition
            (@RequestParam(value = "code", required = false) String code,
             @RequestParam(value = "beginDate", required = false)
             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
             @RequestParam(value = "endDate", required = false)
             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
             @RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize

            ) {

        String codeSQL="";
        String beginDateSQL="";
        String endDateSQL="";
        if(code!=null){
            codeSQL="and code like '"+code+"%' ";
        }
        if(beginDate!=null){
            beginDateSQL="and item_dictionary.create_date >= '"+beginDate+"' ";
        }
        if(endDate!=null){
            endDateSQL="and item_dictionary.create_date <= '"+endDate+"' ";
        }

        int itemListCount = itemMapper.queryItemListCountByCondition(codeSQL, beginDateSQL, endDateSQL);

        return new Result(200,null,itemListCount);
    }




    @RequestMapping(value = "/addOrUpdateItem", method = RequestMethod.POST)
    public Result addOrUpdateItem
            (@RequestBody Item item
            ) {
        try {
            LocalDate today = LocalDate.now();
            if(item.getId()!=0){
                itemMapper.updateItem( item.getCode(),item.getName(), item.getModel(), item.getUnitName(), item.getUnitPriceExcludingTax(),
                        item.getManufacturerId(),  item.getBillItem(), item.getStandards(),
                        item.getApprovalNo(), item.getType(), item.getExpireDate(), today,
                        null, null, null, null, null, null,
                        null, null, null, null,
                        item.getCertificationUrl(),pinyinService.getPinyinInitials(item.getName()),item.getId(), item.getRetailPrice(), item.getRetailEmployeePrice());
                return new Result(200, "修改成功！", null);
            }


            itemMapper.addItem(item.getCode(), item.getName(), item.getModel(), item.getUnitName(), item.getUnitPriceExcludingTax(),
                    item.getManufacturerId(),  item.getBillItem(), item.getStandards(),
                    item.getApprovalNo(), item.getType(), item.getExpireDate(), today,
                    null, null, null, null, null, null,
                    null, null, null, null,
                    item.getCertificationUrl(),pinyinService.getPinyinInitials(item.getName()), item.getRetailPrice(), item.getRetailEmployeePrice());

            return new Result(200, "添加成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Result(500, "Error deleting item: " + e.getMessage(), null);
        }


    }

    @RequestMapping(value = "/deleteItem", method = RequestMethod.GET)
    public Result deleteItem
            (@RequestParam(value = "id", required = false) int id
            ) {
        try {
            itemMapper.deleteItem(id);
            return new Result(200, "删除成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Result(500, "Error deleting item: " + e.getMessage(), null);
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleJsonParseException(HttpMessageNotReadableException e) {
        return new Result(400, "检测到异常字符！", null);
    }




}
