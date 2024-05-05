package com.njglyy.corporate_group_backend.controller;
import com.njglyy.corporate_group_backend.entity.ItemDetail;
import com.njglyy.corporate_group_backend.entity.Item;
import com.njglyy.corporate_group_backend.entity.Result;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
public class ItemController {
    @Autowired
    private ItemMapper itemMapper;
    @RequestMapping(value = "/queryItemList", method = RequestMethod.GET)
    public Result queryItemList
            (@RequestParam(value = "code", required = false) String code
            ) {

        List<Item> itemList = itemMapper.queryItemByCode("%"+code+"%");

        return new Result(200,null,itemList);
    }


    @RequestMapping(value = "/addOrUpdateItem", method = RequestMethod.POST)
    public Result addOrUpdateItem
            (@RequestBody ItemDetail itemDetail
            ) {
        try {
            System.out.println(itemDetail);
            LocalDate today = LocalDate.now();
            if(itemDetail.getId()!=0){
                itemMapper.updateItem( itemDetail.getCode(),itemDetail.getName(), itemDetail.getModel(), itemDetail.getUnitName(), itemDetail.getSellingPrice(),
                        itemDetail.getManufacturerId(),  itemDetail.getBillItem(), itemDetail.getStandards(),
                        itemDetail.getApprovalNo(), itemDetail.getType(), itemDetail.getExpireDate(), today,
                        null, null, null, null, null, null,
                        null, null, null, null,
                        itemDetail.getCertificationUrl(),itemDetail.getId());
                return new Result(200, "修改成功！", null);
            }


            itemMapper.addItem(itemDetail.getCode(), itemDetail.getName(), itemDetail.getModel(), itemDetail.getUnitName(), itemDetail.getSellingPrice(),
                    itemDetail.getManufacturerId(),  itemDetail.getBillItem(), itemDetail.getStandards(),
                    itemDetail.getApprovalNo(), itemDetail.getType(), itemDetail.getExpireDate(), today,
                    null, null, null, null, null, null,
                    null, null, null, null,
                    itemDetail.getCertificationUrl());

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
