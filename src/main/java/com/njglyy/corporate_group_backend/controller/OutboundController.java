package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.*;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.InboundMapper;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.OutboundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin//解决跨域问题
public class OutboundController {
    @Autowired
    private OutboundMapper outboundMapper;
    @Autowired
    private InboundMapper inboundMapper;
    @RequestMapping(value = "/queryOutboundList", method = RequestMethod.GET)
    public Result queryInboundList
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<Outbound> inboundList = outboundMapper.queryOutboundList(offset, pageSize);
        return new Result(200, null, inboundList);
    }

    @RequestMapping(value = "/addOrUpdateOutbound", method = RequestMethod.POST)
    public Result addOrUpdateOutbound
            (@RequestBody OutboundInfo outboundInfo
            ) {
        try {
            System.out.println(outboundInfo);
            System.out.println(outboundInfo.getId());
            if (outboundInfo.getId() != 0) {
                OutboundInfo originalOuboundInfo = outboundMapper.queryOutboundById(outboundInfo.getId());
                outboundMapper.updateOutbound(outboundInfo.getOrderNo(), outboundInfo.getOutboundDate(),
                        outboundInfo.getRemark(), outboundInfo.getId());
                System.out.println("originalOuboundInfo");
                System.out.println(originalOuboundInfo);
                System.out.println("outboundInfo");
                System.out.println(outboundInfo.getOrderNo());
                outboundMapper.updateOutboundDetailListByOrderNo(originalOuboundInfo.getOrderNo(),outboundInfo.getOrderNo());
                return new Result(200, "修改成功！", null);
            }


            outboundMapper.addOutbound(outboundInfo.getOrderNo(), outboundInfo.getOutboundDate(),
                    outboundInfo.getRemark());

            return new Result(200, "添加成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Result(500, "Error deleting item: " + e.getMessage(), null);
        }
    }
    @RequestMapping(value = "/queryOutboundCount", method = RequestMethod.GET)
    public Result queryOutboundCount
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        int outboundsCount = outboundMapper.queryOutboundCount(offset, pageSize);
        return new Result(200, null, outboundsCount);
    }

    @RequestMapping(value = "/deleteOutbound", method = RequestMethod.GET)
    public Result deleteOutbound
            (@RequestParam(value = "orderNo", required = false) String orderNo
            ) {

        outboundMapper.deleteOutboundItemListByOrderNo(orderNo);
        outboundMapper.deleteOutboundListByOrderNo(orderNo);
        return new Result(200, "删除成功！", null);
    }

}
