package com.njglyy.corporate_group_backend.controller;
import com.njglyy.corporate_group_backend.entity.Item;
import com.njglyy.corporate_group_backend.entity.Result;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ItemController {
    @Autowired
    private ItemMapper itemMapper;
    @RequestMapping(value = "/itemList", method = RequestMethod.GET)
    public Result queryBedList
            (@RequestParam(value = "code", required = false) String code
            ) {
        System.out.println(123);
        List<Item> itemList = itemMapper.queryItemByCode(code);

        return new Result(200,null,itemList);
    }
}
