package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.*;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.OutboundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class OutboundController {
    @Autowired
    private OutboundMapper outboundMapper;



    @RequestMapping(value = "/addOrUpdateOutbound", method = RequestMethod.POST)
    public Result addOrUpdateOutbound
            (@RequestBody OutboundInfo outboundInfo
            ) {
        if(outboundInfo.getOutboundNo()!=null){
            outboundMapper.updateOutbound(outboundInfo.getOutboundNo(),
                    outboundInfo.getRemark(),outboundInfo.getAccountingReversal());
            return new Result(200, "修改成功！", null);
        }else {
            String newLeftOutboundNoString = "";
            String newRightOutboundNoString ="00001";
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
            newLeftOutboundNoString = today.format(formatter);

            List<Outbound> topOutboundList= outboundMapper.queryOutboundList(0, 1);
            System.out.println(topOutboundList.size());
            if(topOutboundList.size()!=0){
                String outboundNoString =topOutboundList.get(0).getOutboundInfo().getOutboundNo();
                String leftOutboundNoString= outboundNoString.substring(0,6);
                String rightOutboundNoString= outboundNoString.substring(6,11);
                int rightOutboundNo = Integer.parseInt(rightOutboundNoString);
                if(newLeftOutboundNoString.equals(leftOutboundNoString)){
                    newRightOutboundNoString = String.format("%05d", rightOutboundNo+1);
                }

            }

            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String newOutboundDate = today.format(formatter);


            String newOutboundNoString=newLeftOutboundNoString+newRightOutboundNoString;
            outboundMapper.addOutbound(newOutboundNoString, LocalDate.parse(newOutboundDate),
                    outboundInfo.getRemark(),0);
            return new Result(200, "添加成功！", null);
        }

    }
    @RequestMapping(value = "/deleteOutbound", method = RequestMethod.GET)
    public Result deleteOutbound
            (@RequestParam(value = "outboundNo", required = false) String outboundNo
            ) {

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
        System.out.println(outboundNo);
        int offset = (currentPage - 1) * pageSize;
        List<Outbound> outboundDetailList = outboundMapper.queryOutboundDetailList(outboundNo, offset, pageSize);
        System.out.println(outboundDetailList);
        return new Result(200, null, outboundDetailList);
    }


    @RequestMapping(value = "/countOutboundDetailList", method = RequestMethod.GET)
    public Result countOutboundDetailMachineNoCount
            (@RequestParam(value = "outboundNo", required = false) String outboundNo,
             @RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        System.out.println(outboundNo);
        System.out.println(currentPage);
        System.out.println(pageSize);
        int offset = (currentPage - 1) * pageSize;
        int outboundDetailsCount = outboundMapper.countOutboundDetailList(outboundNo, offset, pageSize);
        System.out.println(outboundDetailsCount);
        return new Result(200, null, outboundDetailsCount);
    }







    @RequestMapping(value = "/addOrUpdateOutboundDetail", method = RequestMethod.POST)
    public Result addOrUpdateOutboundDetail
            (@RequestBody List<OutboundItem> dialogOutboundDetail

            ) {
        try {

            System.out.println(dialogOutboundDetail);
            OutboundItem dialogOutboundDetailOld = dialogOutboundDetail.get(0);
            OutboundItem dialogOutboundDetailNew = dialogOutboundDetail.get(1);
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

        OutboundItem OutboundItemList = outboundMapper.queryOutboundItemListByOutboundNoAndItemId(outboundNo, itemId);
        return new Result(200, null, OutboundItemList);
    }


    @RequestMapping(value = "/queryOutboundList", method = RequestMethod.GET)
    public Result queryOutboundList
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<Outbound> outboundList = outboundMapper.queryOutboundList(offset, pageSize);
        return new Result(200, null, outboundList);
    }


}
