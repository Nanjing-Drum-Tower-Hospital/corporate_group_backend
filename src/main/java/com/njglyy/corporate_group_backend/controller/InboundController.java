package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.*;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.InboundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class InboundController {
    @Autowired
    private InboundMapper inboundMapper;

    @RequestMapping(value = "/querySupplierList", method = RequestMethod.GET)
    public Result querySupplierList
            () {
        try {
            List<Supplier> supplierList = inboundMapper.querySupplierList();
            return new Result(200, null, supplierList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Result(500, "Error querying manufacturer list: " + e.getMessage(), null);
        }
    }

    @RequestMapping(value = "/addOrUpdateInbound", method = RequestMethod.POST)
    public Result addOrUpdateInbound
            (@RequestBody InboundInfo inboundInfo
            ) {
        if(inboundInfo.getInboundNo()!=null){
            inboundMapper.updateInbound(inboundInfo.getInboundNo(),
                    inboundInfo.getSupplierId(),inboundInfo.getRemark(),inboundInfo.getAccountingReversal());
            return new Result(200, "修改成功！", null);
        }else {
            String newLeftInboundNoString = "";
            String newRightInboundNoString = "";
            List<Inbound> topInboundList= inboundMapper.queryInboundList(0, 1);
            String inboundNoString =topInboundList.get(0).getInboundInfo().getInboundNo();
            String leftInboundNoString= inboundNoString.substring(0,6);
            String rightInboundNoString= inboundNoString.substring(6,11);
            int rightInboundNo = Integer.parseInt(rightInboundNoString);
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
            newLeftInboundNoString = today.format(formatter);
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String newInboundDate = today.format(formatter);
            if(newLeftInboundNoString.equals(leftInboundNoString)){
                newRightInboundNoString = String.format("%05d", rightInboundNo+1);
            }
            else{
                newRightInboundNoString = "00001";
            }
            String newInboundNoString=newLeftInboundNoString+newRightInboundNoString;
            inboundMapper.addInbound(newInboundNoString, LocalDate.parse(newInboundDate), inboundInfo.getSupplierId(),
                    inboundInfo.getRemark(),0);
            return new Result(200, "添加成功！", null);
        }

    }
    @RequestMapping(value = "/deleteInbound", method = RequestMethod.GET)
    public Result deleteInbound
            (@RequestParam(value = "inboundNo", required = false) String inboundNo
            ) {

        inboundMapper.deleteInboundItemListByInboundNo(inboundNo);
        inboundMapper.deleteInboundListByInboundNo(inboundNo);
        return new Result(200, "删除成功！", null);
    }




    @RequestMapping(value = "/queryInboundCount", method = RequestMethod.GET)
    public Result queryInboundCount
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        int inboundsCount = inboundMapper.queryInboundCount(offset, pageSize);
        return new Result(200, null, inboundsCount);
    }

//    @RequestMapping(value = "/queryInboundDetail", method = RequestMethod.GET)
//    public Result queryInboundDetail
//            (@RequestParam(value = "orderNo", required = false) String orderNo) {
//        System.out.println(orderNo);
//        List<Inbound> inboundDetailList = inboundMapper.queryInboundDetail(orderNo);
//        System.out.println(inboundDetailList);
//        return new Result(200, null, inboundDetailList);
//    }

    @RequestMapping(value = "/queryInboundDetailList", method = RequestMethod.GET)
    public Result queryInboundDetailMachineNoCount
            (@RequestParam(value = "inboundNo", required = false) String inboundNo,
             @RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        System.out.println(inboundNo);
        int offset = (currentPage - 1) * pageSize;
        List<Inbound> inboundDetailList = inboundMapper.queryInboundDetailList(inboundNo, offset, pageSize);
        System.out.println(inboundDetailList);
        return new Result(200, null, inboundDetailList);
    }


    @RequestMapping(value = "/countInboundDetailList", method = RequestMethod.GET)
    public Result countInboundDetailMachineNoCount
            (@RequestParam(value = "inboundNo", required = false) String inboundNo,
             @RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        System.out.println(inboundNo);
        System.out.println(currentPage);
        System.out.println(pageSize);
        int offset = (currentPage - 1) * pageSize;
        int inboundDetailsCount = inboundMapper.countInboundDetailList(inboundNo, offset, pageSize);
        System.out.println(inboundDetailsCount);
        return new Result(200, null, inboundDetailsCount);
    }







    @RequestMapping(value = "/addOrUpdateInboundDetail", method = RequestMethod.POST)
    public Result addOrUpdateInboundDetail
            (@RequestBody List<InboundItem> dialogInboundDetail

            ) {
        try {

            System.out.println(dialogInboundDetail);
            InboundItem dialogInboundDetailOld = dialogInboundDetail.get(0);
            InboundItem dialogInboundDetailNew = dialogInboundDetail.get(1);
            if (dialogInboundDetailOld.equals(dialogInboundDetailNew)) {
                System.out.println("The items are equal.");
            } else {
                System.out.println("The items are different.");
            }

//            System.out.println(inboundNo);
//            System.out.println(itemId);
//            System.out.println(machineNumbers);
//            // Convert machineNumbers list to a comma-separated string
//            String machineNumbersString = machineNumbers.stream()
//                    .map(machineNumber -> "'" + machineNumber + "'")
//                    .collect(Collectors.joining(","));
//
//            // Call the mapper method with the generated machineNumbersString
//            inboundMapper.deleteInboundDetailsByInboundNoAndItemIdAndMachineNoNotIn(inboundNo, itemId, machineNumbersString);
//
//
//            for (String machineNumber : machineNumbers) {
//                List<InboundItem> inboundItemList = inboundMapper.queryInboundDetailsByInboundNoAndItemIdAndMachineNo(inboundNo, itemId, machineNumber);
//                System.out.println(inboundItemList);
//                if (inboundItemList.size() == 0) {
//                    inboundMapper.addInboundDetail(inboundNo, itemId, machineNumber);
//                }
//
//            }


            return new Result(200, "添加成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Result(500, "Error deleting item: " + e.getMessage(), null);
        }
    }
    @RequestMapping(value = "/deleteInboundItemListByInboundNoAndItemId", method = RequestMethod.GET)
    public Result deleteInboundItemListByInboundNoAndItemId
            (@RequestParam(value = "inboundNo", required = false) String inboundNo,
             @RequestParam(value = "itemId", required = false) int itemId
            ) {

        inboundMapper.deleteInboundItemListByInboundNoAndItemId(inboundNo, itemId);
        return new Result(200, "删除成功！", null);
    }


    @RequestMapping(value = "/queryInboundItemListByInboundNoAndItemId", method = RequestMethod.GET)
    public Result queryInboundItemListByInboundNoAndItemId
            (@RequestParam(value = "inboundNo", required = false) String inboundNo,
             @RequestParam(value = "itemId", required = false) int itemId
            ) {

        InboundItem InboundItemList = inboundMapper.queryInboundItemListByInboundNoAndItemId(inboundNo, itemId);
        return new Result(200, null, InboundItemList);
    }


    @RequestMapping(value = "/queryInboundList", method = RequestMethod.GET)
    public Result queryInboundList
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<Inbound> inboundList = inboundMapper.queryInboundList(offset, pageSize);
        return new Result(200, null, inboundList);
    }


}
