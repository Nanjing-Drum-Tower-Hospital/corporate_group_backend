package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.*;

import com.njglyy.corporate_group_backend.mapper.corporateGroup.InboundMapper;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.OutboundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        if (outboundInfo.getOutboundNo() != null) {
            outboundMapper.updateOutbound(outboundInfo.getOutboundNo(), outboundInfo.getRemark(),
                    outboundInfo.getAccountingReversal());
            return new Result(200, "修改成功！", null);
        } else {
            String newLeftOutboundNoString = "";
            String newRightOutboundNoString = "";
            List<Outbound> topOutboundList = outboundMapper.queryOutboundList(0, 1);
            String outboundNoString = topOutboundList.get(0).getOutboundInfo().getOutboundNo();
            String leftOutboundNoString = outboundNoString.substring(0, 6);
            String rightOutboundNoString = outboundNoString.substring(6, 11);
            int rightOutboundNo = Integer.parseInt(rightOutboundNoString);
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
            newLeftOutboundNoString = today.format(formatter);
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String newOutboundDate = today.format(formatter);
            if (newLeftOutboundNoString.equals(leftOutboundNoString)) {
                newRightOutboundNoString = String.format("%05d", rightOutboundNo + 1);
            } else {
                newRightOutboundNoString = "00001";
            }
            String newOutboundNoString = newLeftOutboundNoString + newRightOutboundNoString;
            outboundMapper.addOutbound(newOutboundNoString, LocalDate.parse(newOutboundDate),
                    outboundInfo.getRemark(), 0);
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

//    @RequestMapping(value = "/deleteOutbound", method = RequestMethod.GET)
//    public Result deleteOutbound
//            (@RequestParam(value = "orderNo", required = false) String orderNo
//            ) {
//
//        outboundMapper.deleteOutboundItemListByOrderNo(orderNo);
//        outboundMapper.deleteOutboundListByOrderNo(orderNo);
//        return new Result(200, "删除成功！", null);
//    }

    @RequestMapping(value = "/queryOutboundDetailMachineNoCount", method = RequestMethod.GET)
    public Result queryInboundDetailMachineNoCount
            (@RequestParam(value = "outboundNo", required = false) String outboundNo,
             @RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        System.out.println(outboundNo);
        int offset = (currentPage - 1) * pageSize;
        List<Outbound> outboundDetailMachineNoCountList = outboundMapper.queryOutboundDetailMachineNoCount(outboundNo, offset, pageSize);
        System.out.println(outboundDetailMachineNoCountList);
        return new Result(200, null, outboundDetailMachineNoCountList);
    }

    @RequestMapping(value = "/countOutboundDetailMachineNoCount", method = RequestMethod.GET)
    public Result countInboundDetailMachineNoCount
            (@RequestParam(value = "outboundNo", required = false) String outboundNo,
             @RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        System.out.println(outboundNo);
        System.out.println(currentPage);
        System.out.println(pageSize);
        int offset = (currentPage - 1) * pageSize;
        int outboundDetailsCount = outboundMapper.countOutboundDetailMachineNoCount(outboundNo, offset, pageSize);
        System.out.println(outboundDetailsCount);
        return new Result(200, null, outboundDetailsCount);
    }


    @RequestMapping(value = "/queryOutboundItemListWithoutOutboundByOutboundNoAndItemId", method = RequestMethod.GET)
    public Result queryOutboundItemListByOutboundNoAndItemId
            (@RequestParam(value = "outboundNo", required = false) String outboundNo,
             @RequestParam(value = "itemId", required = false) Integer  itemId) {
        System.out.println(outboundNo);
        System.out.println(itemId);
        List<Outbound> outboundDetails = outboundMapper.queryOutboundItemListWithoutOutboundByOutboundNoAndItemId(outboundNo, itemId);
        System.out.println(outboundDetails);
        return new Result(200, null, outboundDetails);
    }


//    @RequestMapping(value = "/addOrUpdateOutboundDetail", method = RequestMethod.POST)
//    public Result addOrUpdateOutboundDetail
//            (@RequestBody List<Outbound> outboundList,
//             @RequestParam(value = "outboundNo", required = false) String outboundNo,
//             @RequestParam(value = "itemId", required = false) int itemId
//            ) {
//        System.out.println(outboundNo);
//        System.out.println(itemId);
//        System.out.println(outboundList);
//        List<Outbound> outboundedList =outboundMapper.queryOutboundItemListByOutboundNoAndItemId(outboundNo, itemId);
//        System.out.println(outboundedList);
//
//        for (Outbound outbound : outboundedList) {
//            if (!outboundList.contains(outbound)) {
//                outboundMapper.deleteOutboundListByOutboundNoAndItemIdAndMachineNo(outboundNo, itemId, outbound.getInboundItem().getMachineNo());
//            }
//        }
//        for(Outbound outbound : outboundList){
//            if(outbound.getOutboundInfo() == null) {
//                outboundMapper.addOutboundDetail(outboundNo, outbound.getInboundItem().getId());
//            }
//        }
//
//
//        return new Result(200, "添加成功！", null);
//
//    }


    @RequestMapping(value = "/deleteOutboundItemListByOutboundNoAndItemId", method = RequestMethod.GET)
    public Result addOrUpdateOutboundDetail
            (
             @RequestParam(value = "outboundNo", required = false) String outboundNo,
             @RequestParam(value = "itemId", required = false) int itemId
            ) {
        outboundMapper.deleteOutboundItemListByOutboundNoAndItemId(outboundNo, itemId);
        return new Result(200, "删除成功！", null);

    }
}
