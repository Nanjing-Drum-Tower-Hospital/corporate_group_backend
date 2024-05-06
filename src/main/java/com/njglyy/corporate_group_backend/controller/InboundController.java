package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.Inbound;
import com.njglyy.corporate_group_backend.entity.Result;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.InboundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class InboundController {
    @Autowired
    private InboundMapper inboundMapper;
    @RequestMapping(value = "/queryInboundList", method = RequestMethod.GET)
    public Result queryInboundList
            () {
        List<Inbound> inboundDetailList = inboundMapper.queryInbound();
        return new Result(200,null,inboundDetailList);
    }

    @RequestMapping(value = "/queryInboundDetail", method = RequestMethod.GET)
    public Result queryInboundDetail
            (@RequestParam(value = "orderNo", required = false) String orderNo) {
        List<Inbound> inboundDetailList = inboundMapper.queryInboundDetail(orderNo);
        System.out.println(inboundDetailList);
        return new Result(200,null,inboundDetailList);
    }
}
