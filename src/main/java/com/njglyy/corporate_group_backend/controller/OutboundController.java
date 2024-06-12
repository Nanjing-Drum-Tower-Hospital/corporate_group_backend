package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.*;

import com.njglyy.corporate_group_backend.mapper.corporateGroup.InboundMapper;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.OutboundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin//解决跨域问题
public class OutboundController {
    @Autowired
    private OutboundMapper outboundMapper;
    @Autowired
    private InboundMapper inboundMapper;
    @RequestMapping(value = "/queryOutboundList", method = RequestMethod.GET)
    public Result queryOutboundList
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<Outbound> outboundList = outboundMapper.queryOutboundList(offset, pageSize);
        return new Result(200, null, outboundList);
    }

    @RequestMapping(value = "/addOrUpdateOutbound", method = RequestMethod.POST)
    public Result addOrUpdateOutbound
            (@RequestBody OutboundInfo outboundInfo
            ) {
        System.out.println(outboundInfo);
        if(outboundInfo.getOutboundNo()!=null){
            outboundMapper.updateOutbound(outboundInfo.getOutboundNo(),outboundInfo.getRemark(),
                    outboundInfo.getAccountingReversal());
            return new Result(200, "修改成功！", null);
        }else {
            String newLeftOutboundNoString = "";
            String newRightOutboundNoString = "";
            List<Outbound> topOutboundList= outboundMapper.queryOutboundList(0, 1);
            String outboundNoString =topOutboundList.get(0).getOutboundInfo().getOutboundNo();
            String leftOutboundNoString= outboundNoString.substring(0,6);
            String rightOutboundNoString= outboundNoString.substring(6,11);
            int rightOutboundNo = Integer.parseInt(rightOutboundNoString);
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
            newLeftOutboundNoString = today.format(formatter);
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String newOutboundDate = today.format(formatter);
            if(newLeftOutboundNoString.equals(leftOutboundNoString)){
                newRightOutboundNoString = String.format("%05d", rightOutboundNo+1);
            }
            else{
                newRightOutboundNoString = "00001";
            }
            String newOutboundNoString=newLeftOutboundNoString+newRightOutboundNoString;
            outboundMapper.addOutbound(newOutboundNoString, LocalDate.parse(newOutboundDate),
                    outboundInfo.getRemark(),0);
            return new Result(200, "添加成功！", null);
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
