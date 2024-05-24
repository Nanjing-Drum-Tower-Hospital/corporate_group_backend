package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.*;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.InboundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class InboundController {
    @Autowired
    private InboundMapper inboundMapper;
    @RequestMapping(value = "/deleteInboundItemListByOrderNoAndItemId", method = RequestMethod.GET)
    public Result deleteInboundItemListByOrderNoAndItemId
            (@RequestParam(value = "orderNo", required = false) String orderNo,
             @RequestParam(value = "itemId", required = false) int itemId
            ) {

        inboundMapper.deleteInboundItemListByOrderNoAndItemId(orderNo,itemId);
        return new Result(200,"删除成功！",null);
    }


    @RequestMapping(value = "/queryInboundItemListByOrderNoAndItemId", method = RequestMethod.GET)
    public Result queryInboundItemListByOrderNoAndItemId
            (@RequestParam(value = "orderNo", required = false) String orderNo,
             @RequestParam(value = "itemId", required = false) int itemId
            ) {

        List<InboundItem> InboundItemList = inboundMapper.queryInboundItemListByOrderNoAndItemId(orderNo,itemId);
        return new Result(200,null,InboundItemList);
    }



    @RequestMapping(value = "/queryInboundList", method = RequestMethod.GET)
    public Result queryInboundList
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<Inbound> inboundList = inboundMapper.queryInboundList(offset, pageSize);
        return new Result(200,null,inboundList);
    }


    @RequestMapping(value = "/queryInboundCount", method = RequestMethod.GET)
    public Result queryInboundCount
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        int inboundsCount = inboundMapper.queryInboundCount(offset, pageSize);
        return new Result(200,null,inboundsCount);
    }

    @RequestMapping(value = "/queryInboundDetail", method = RequestMethod.GET)
    public Result queryInboundDetail
            (@RequestParam(value = "orderNo", required = false) String orderNo) {
        System.out.println(orderNo);
        List<Inbound> inboundDetailList = inboundMapper.queryInboundDetail(orderNo);
        System.out.println(inboundDetailList);
        return new Result(200,null,inboundDetailList);
    }

    @RequestMapping(value = "/queryInboundDetailMachineNoCount", method = RequestMethod.GET)
    public Result queryInboundDetailMachineNoCount
            (@RequestParam(value = "orderNo", required = false) String orderNo,
             @RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        System.out.println(orderNo);
        int offset = (currentPage - 1) * pageSize;
        List<Inbound> inboundDetailMachineNoCountList = inboundMapper.queryInboundDetailMachineNoCount(orderNo, offset, pageSize);
        System.out.println(inboundDetailMachineNoCountList);
        return new Result(200,null,inboundDetailMachineNoCountList);
    }


    @RequestMapping(value = "/countInboundDetailMachineNoCount", method = RequestMethod.GET)
    public Result countInboundDetailMachineNoCount
            (@RequestParam(value = "orderNo", required = false) String orderNo,
             @RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        System.out.println(orderNo);
        System.out.println(currentPage);
        System.out.println(pageSize);
        int offset = (currentPage - 1) * pageSize;
        int inboundDetailsCount = inboundMapper.countInboundDetailMachineNoCount(orderNo, offset, pageSize);
        System.out.println(inboundDetailsCount);
        return new Result(200,null,inboundDetailsCount);
    }


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
        try {
            System.out.println(inboundInfo);

            if(inboundInfo.getId()!=0){
                inboundMapper.updateInbound( inboundInfo.getOrderNo(),inboundInfo.getArrivalDate(),
                        inboundInfo.getSupplierId(),inboundInfo.getRemark(),
                        inboundInfo.getId());
                return new Result(200, "修改成功！", null);
            }


            inboundMapper.addInbound( inboundInfo.getOrderNo(), inboundInfo.getArrivalDate(), inboundInfo.getSupplierId(),
                    inboundInfo.getRemark());

            return new Result(200, "添加成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Result(500, "Error deleting item: " + e.getMessage(), null);
        }
    }


    @RequestMapping(value = "/addOrUpdateInboundDetail", method = RequestMethod.GET)
    public Result addOrUpdateInboundDetail
            (@RequestParam String orderNo,
             @RequestParam int itemId,
             @RequestParam(required = false, defaultValue = "") List<String> machineNumbers
            ) {
        try {
            if(machineNumbers.size() == 0){
                return new Result(300, "无添加信息！", null);
            }
            System.out.println(orderNo);
            System.out.println(itemId);
            System.out.println(machineNumbers);
            // Convert machineNumbers list to a comma-separated string
            String machineNumbersString = machineNumbers.stream()
                    .map(machineNumber -> "'" + machineNumber + "'")
                    .collect(Collectors.joining(","));

            // Call the mapper method with the generated machineNumbersString
            inboundMapper.deleteInboundDetailsByOrderNoAndItemIdAndMachineNoNotIn(orderNo, itemId, machineNumbersString);



            for(String machineNumber: machineNumbers){
                List<InboundItem> inboundItemList = inboundMapper.queryInboundDetailsByOrderNoAndItemIdAndMachineNo(orderNo, itemId, machineNumber);
                System.out.println(inboundItemList);
                if(inboundItemList.size() == 0){
                    inboundMapper.addInboundDetail( orderNo, itemId, machineNumber);
                }

            }


            return new Result(200, "添加成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Result(500, "Error deleting item: " + e.getMessage(), null);
        }
    }

}
