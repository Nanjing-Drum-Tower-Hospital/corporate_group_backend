package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.Inbound;
import com.njglyy.corporate_group_backend.entity.Item;
import com.njglyy.corporate_group_backend.entity.Result;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.InboundMapper;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class InboundController {
    @Autowired
    private InboundMapper inboundMapper;
    @RequestMapping(value = "/queryInboundList", method = RequestMethod.GET)
    public Result queryItemList
            () {
        List<Inbound> inboundList = inboundMapper.queryInbound();
        return new Result(200,null,inboundList);
    }
}
