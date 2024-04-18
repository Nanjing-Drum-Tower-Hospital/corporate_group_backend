package com.njglyy.corporate_group_backend.controller;
import com.njglyy.corporate_group_backend.entity.Item;
import com.njglyy.corporate_group_backend.entity.Result;
import com.njglyy.corporate_group_backend.entity.User;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping(value = "/addItem", method = RequestMethod.POST)
    public Result addItem
            (@RequestBody Item item
            ) {
        System.out.println(item);
//        List<Item> itemList = itemMapper.queryItemByCode("%"+code+"%");

        return new Result(200,null,null);
    }
}
