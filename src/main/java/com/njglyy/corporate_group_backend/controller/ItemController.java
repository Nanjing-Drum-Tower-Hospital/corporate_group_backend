package com.njglyy.corporate_group_backend.controller;
import com.njglyy.corporate_group_backend.entity.ItemDetail;
import com.njglyy.corporate_group_backend.entity.Item;
import com.njglyy.corporate_group_backend.entity.Manufacturer;
import com.njglyy.corporate_group_backend.entity.Result;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.ItemMapper;
import com.njglyy.corporate_group_backend.service.PinyinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
        System.out.println(input);
        String inputStr = "%"+input+"%";

        List<ItemDetail> itemDetailList = itemMapper.queryItemByCodeOrName(inputStr);
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
        List<Item> itemList = itemMapper.queryItemsByCondition(codeSQL, beginDateSQL, endDateSQL, offset, pageSize);


        return new Result(200,null,itemList);
    }


//    @RequestMapping(value = "/queryItemByCode", method = RequestMethod.GET)
//    public Result queryItemByCode
//            (@RequestParam(value = "code", required = false) String code
//            ) {
//
//
//        ItemDetail itemDetail = itemMapper.queryItemByCode(code);
//        System.out.println("code");
//        System.out.println(itemDetail);
//        System.out.println("code");
//
//        return new Result(200,null,itemDetail);
//    }

    @RequestMapping(value = "/queryItemsCount", method = RequestMethod.GET)
    public Result queryItemsCountByCondition
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

        int itemsCount = itemMapper.queryItemsCountByCondition(codeSQL, beginDateSQL, endDateSQL);

        return new Result(200,null,itemsCount);
    }




    @RequestMapping(value = "/addOrUpdateItem", method = RequestMethod.POST)
    public Result addOrUpdateItem
            (@RequestBody ItemDetail itemDetail
            ) {
        try {
            System.out.println(itemDetail);
            LocalDate today = LocalDate.now();
            if(itemDetail.getId()!=0){
                itemMapper.updateItem( itemDetail.getCode(),itemDetail.getName(), itemDetail.getModel(), itemDetail.getUnitName(), itemDetail.getUnitPriceExcludingTax(),
                        itemDetail.getManufacturerId(),  itemDetail.getBillItem(), itemDetail.getStandards(),
                        itemDetail.getApprovalNo(), itemDetail.getType(), itemDetail.getExpireDate(), today,
                        null, null, null, null, null, null,
                        null, null, null, null,
                        itemDetail.getCertificationUrl(),pinyinService.getPinyinInitials(itemDetail.getName()),itemDetail.getId());
                return new Result(200, "修改成功！", null);
            }


            itemMapper.addItem(itemDetail.getCode(), itemDetail.getName(), itemDetail.getModel(), itemDetail.getUnitName(), itemDetail.getUnitPriceExcludingTax(),
                    itemDetail.getManufacturerId(),  itemDetail.getBillItem(), itemDetail.getStandards(),
                    itemDetail.getApprovalNo(), itemDetail.getType(), itemDetail.getExpireDate(), today,
                    null, null, null, null, null, null,
                    null, null, null, null,
                    itemDetail.getCertificationUrl(),pinyinService.getPinyinInitials(itemDetail.getName()));

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





}
