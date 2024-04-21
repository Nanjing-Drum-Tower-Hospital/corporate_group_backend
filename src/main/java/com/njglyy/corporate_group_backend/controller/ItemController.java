package com.njglyy.corporate_group_backend.controller;
import com.njglyy.corporate_group_backend.entity.Item;
import com.njglyy.corporate_group_backend.entity.ItemDetail;
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

        List<ItemDetail> itemList = itemMapper.queryItemByCode("%"+code+"%");

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
                        item.getManufacturerId(),  item.getBillItem(), item.getStandards(),
                        item.getApprovalNo(), item.getType(), item.getExpireDate(), today,
                        null, null, null, null, null, null,
                        null, null, null, null,
                        item.getCertificationUrl(),item.getId());
                return new Result(200, "修改成功！", null);
            }


            itemMapper.addItem(item.getCode(), item.getName(), item.getModel(), item.getUnitName(), item.getSellingPrice(),
                    item.getManufacturerId(),  item.getBillItem(), item.getStandards(),
                    item.getApprovalNo(), item.getType(), item.getExpireDate(), today,
                    null, null, null, null, null, null,
                    null, null, null, null,
                    item.getCertificationUrl());

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
