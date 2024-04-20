package com.njglyy.corporate_group_backend.controller;
import com.njglyy.corporate_group_backend.entity.Item;
import com.njglyy.corporate_group_backend.entity.Result;
import com.njglyy.corporate_group_backend.entity.User;
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
            (@RequestBody Item item
            ) {
        try {
            System.out.println(item);
            LocalDate today = LocalDate.now();
            if(item.getId()!=0){
                itemMapper.updateItem( item.getCode(),item.getName(), item.getModel(), item.getUnitName(), item.getSellingPrice(),
                        item.getManufacturerId(), item.getSupplierId(), item.getBillItem(), item.getStandards(),
                        item.getApprovalNo(), item.getType(), item.getExpireDate(), today,
                        null, null, null, null, null, null,
                        null, null, null, null,
                        item.getCertificationUrl(),item.getId());
                return new Result(200, "修改成功！", null);
            }


            itemMapper.addItem(item.getCode(), item.getName(), item.getModel(), item.getUnitName(), item.getSellingPrice(),
                    item.getManufacturerId(), item.getSupplierId(), item.getBillItem(), item.getStandards(),
                    item.getApprovalNo(), item.getType(), item.getExpireDate(), today,
                    null, null, null, null, null, null,
                    null, null, null, null,
                    item.getCertificationUrl());

            return new Result(200, "添加成功！", null);
        } catch (Exception e) {
            // Log the exception here; depending on your logging framework you might use:
            // e.g., Logger.error("Failed to add item", e);
            System.out.println("Error adding item: " + e.getMessage());
            return new Result(500, "Error adding item: " + e.getMessage(), null);
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
            // Log the exception here; depending on your logging framework you might use:
            // e.g., Logger.error("Failed to delete item", e);
            System.out.println("Error deleting item: " + e.getMessage());
            return new Result(500, "Error deleting item: " + e.getMessage(), null);
        }
    }

}
