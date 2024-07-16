package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.*;
import com.njglyy.corporate_group_backend.mapper.OutboundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin
public class OutboundController {
    @Autowired
    private OutboundMapper outboundMapper;

    @RequestMapping(value = "/outboundAccountingReversal", method = RequestMethod.GET)
    public Result outboundAccountingReversal
            (@RequestParam(value = "outboundNo", required = false) String outboundNo
            ) {
        System.out.println(outboundNo);
        List<Outbound> outboundDetailList = outboundMapper.queryOutboundDetailList(outboundNo, 0, Integer.MAX_VALUE);
        System.out.println(outboundDetailList);

        OutboundInfo outboundInfo = outboundDetailList.get(0).getOutboundInfo().clone();

        // Deep copy of outboundInfo

        outboundInfo.setOutboundNo(null);
        outboundInfo.setRemark(outboundNo + " 冲红");
        outboundInfo.setAccountingReversalOutboundNo(outboundNo);
        outboundInfo.setEntryType("reversal");
        String newOutboundNoString = (String) addOrUpdateOutbound(outboundInfo).getData();
        outboundInfo = outboundDetailList.get(0).getOutboundInfo().clone();

        outboundMapper.updateOutbound(outboundInfo.getOutboundNo(),
                 outboundInfo.getRemark(), newOutboundNoString,"original");
        for(Outbound outbound:outboundDetailList){


            outboundMapper.addOutboundDetail(newOutboundNoString, outbound.getOutboundItem().getItemId(),
                    (-outbound.getOutboundItem().getItemAmount()), "冲红");
        }



        return new Result(200, "冲红成功！", null);
    }

    @RequestMapping(value = "/addOrUpdateOutbound", method = RequestMethod.POST)
    public Result addOrUpdateOutbound
            (@RequestBody OutboundInfo outboundInfo
            ) {
        if (outboundInfo.getOutboundNo() != null) {
            outboundMapper.updateOutbound(outboundInfo.getOutboundNo(),
                    outboundInfo.getRemark(), outboundInfo.getAccountingReversalOutboundNo(),outboundInfo.getEntryType());
            return new Result(200, "修改成功！", null);
        } else {
            String newLeftOutboundNoString = "";
            String newRightOutboundNoString = "00001";
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
            newLeftOutboundNoString = today.format(formatter);

            List<Outbound> topOutboundList = outboundMapper.queryOutboundList(0, 1);

            if (topOutboundList.size() != 0) {
                String outboundNoString = topOutboundList.get(0).getOutboundInfo().getOutboundNo();
                String leftOutboundNoString = outboundNoString.substring(0, 6);
                String rightOutboundNoString = outboundNoString.substring(6, 11);
                int rightOutboundNo = Integer.parseInt(rightOutboundNoString);
                if (newLeftOutboundNoString.equals(leftOutboundNoString)) {
                    newRightOutboundNoString = String.format("%05d", rightOutboundNo + 1);
                }

            }

            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String newOutboundDate = today.format(formatter);


            String newOutboundNoString = newLeftOutboundNoString + newRightOutboundNoString;
            outboundMapper.addOutbound(newOutboundNoString, LocalDate.parse(newOutboundDate),
                    outboundInfo.getRemark(), outboundInfo.getAccountingReversalOutboundNo(),outboundInfo.getEntryType());
            return new Result(200, "添加成功！", newOutboundNoString);
        }

    }
    @RequestMapping(value = "/deleteOutbound", method = RequestMethod.GET)
    public Result deleteOutbound
            (@RequestParam(value = "outboundNo", required = false) String outboundNo
            ) {
        Outbound outbound = outboundMapper.queryOutboundByOutboundNo(outboundNo);
        Outbound outboundReversal = outboundMapper.queryOutboundByOutboundNo(outbound.getOutboundInfo().getAccountingReversalOutboundNo());
        System.out.println(outbound);
        outboundMapper.updateOutbound(outboundReversal.getOutboundInfo().getOutboundNo(),
                 outboundReversal.getOutboundInfo().getRemark(), null,null);
        outboundMapper.deleteOutboundItemListByOutboundNo(outboundNo);
        outboundMapper.deleteOutboundListByOutboundNo(outboundNo);
        return new Result(200, "删除成功！", null);
    }




    @RequestMapping(value = "/queryOutboundCount", method = RequestMethod.GET)
    public Result queryOutboundCount
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        int outboundsCount = outboundMapper.queryOutboundCount(offset, pageSize);
        return new Result(200, null, outboundsCount);
    }


    @RequestMapping(value = "/queryOutboundDetailList", method = RequestMethod.GET)
    public Result queryOutboundDetailMachineNoCount
            (@RequestParam(value = "outboundNo", required = false) String outboundNo,
             @RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {

        int offset = (currentPage - 1) * pageSize;
        List<Outbound> outboundDetailList = outboundMapper.queryOutboundDetailList(outboundNo, offset, pageSize);

        return new Result(200, null, outboundDetailList);
    }


    @RequestMapping(value = "/countOutboundDetailList", method = RequestMethod.GET)
    public Result countOutboundDetailMachineNoCount
            (@RequestParam(value = "outboundNo", required = false) String outboundNo,
             @RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {

        int offset = (currentPage - 1) * pageSize;
        int outboundDetailsCount = outboundMapper.countOutboundDetailList(outboundNo, offset, pageSize);

        return new Result(200, null, outboundDetailsCount);
    }







    @RequestMapping(value = "/addOrUpdateOutboundDetail", method = RequestMethod.POST)
    public Result addOrUpdateOutboundDetail
            (@RequestBody List<OutboundDetail> dialogOutboundDetail

            ) {
        try {


            OutboundDetail dialogOutboundDetailOld = dialogOutboundDetail.get(0);
            OutboundDetail dialogOutboundDetailNew = dialogOutboundDetail.get(1);
            if (dialogOutboundDetailOld.getId()==0){
                outboundMapper.addOutboundDetail(dialogOutboundDetailNew.getOutboundNo(), dialogOutboundDetailNew.getItemId(),
                        dialogOutboundDetailNew.getItemAmount(),dialogOutboundDetailNew.getRemark());
            }
            else{
                outboundMapper.updateOutboundDetailById(dialogOutboundDetailNew.getId(),dialogOutboundDetailNew.getOutboundNo(),
                        dialogOutboundDetailNew.getItemId(), dialogOutboundDetailNew.getItemAmount(),
                        dialogOutboundDetailNew.getRemark());
            }


            return new Result(200, "添加成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Result(500, "Error deleting item: " + e.getMessage(), null);
        }
    }
    @RequestMapping(value = "/deleteOutboundItemListByOutboundNoAndItemId", method = RequestMethod.GET)
    public Result deleteOutboundItemListByOutboundNoAndItemId
            (@RequestParam(value = "outboundNo", required = false) String outboundNo,
             @RequestParam(value = "itemId", required = false) int itemId
            ) {

        outboundMapper.deleteOutboundItemListByOutboundNoAndItemId(outboundNo, itemId);
        return new Result(200, "删除成功！", null);
    }


    @RequestMapping(value = "/queryOutboundItemListByOutboundNoAndItemId", method = RequestMethod.GET)
    public Result queryOutboundItemListByOutboundNoAndItemId
            (@RequestParam(value = "outboundNo", required = false) String outboundNo,
             @RequestParam(value = "itemId", required = false) int itemId
            ) {

        OutboundDetail outboundDetailList = outboundMapper.queryOutboundItemListByOutboundNoAndItemId(outboundNo, itemId);
        return new Result(200, null, outboundDetailList);
    }


    @RequestMapping(value = "/queryOutboundList", method = RequestMethod.GET)
    public Result queryOutboundList
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<Outbound> outboundList = outboundMapper.queryOutboundList(offset, pageSize);
        return new Result(200, null, outboundList);
    }

    @RequestMapping(value = "/queryExistingInventoryAmount", method = RequestMethod.GET)
    public Result queryExistingInventoryAmount
            (@RequestParam(value = "itemId", required = false) int itemId) {
        int existingInventoryAmount = outboundMapper.queryExistingInventoryAmount(itemId);
        return new Result(200, null, existingInventoryAmount);
    }


}
