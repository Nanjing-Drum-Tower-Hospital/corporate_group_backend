package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.*;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.InboundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        System.out.println(orderNo);
        List<Inbound> inboundDetailList = inboundMapper.queryInboundDetail(orderNo);
        System.out.println(inboundDetailList);
        return new Result(200,null,inboundDetailList);
    }

    @RequestMapping(value = "/queryInboundDetailCount", method = RequestMethod.GET)
    public Result queryInboundDetailCount
            (@RequestParam(value = "orderNo", required = false) String orderNo) {
        System.out.println(orderNo);
        List<Inbound> inboundDetailList = inboundMapper.queryInboundDetailCount(orderNo);
        System.out.println(inboundDetailList);
        return new Result(200,null,inboundDetailList);
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


    @RequestMapping(value = "/addOrUpdateInboundDetail", method = RequestMethod.POST)
    public Result addOrUpdateInboundDetail
            (@RequestBody InboundItem inboundItem
            ) {
        try {
            System.out.println(inboundItem);



            inboundMapper.addInboundDetail( inboundItem.getOrderNo(), inboundItem.getItemId(), inboundItem.getMachineNo()
                    );

            return new Result(200, "添加成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Result(500, "Error deleting item: " + e.getMessage(), null);
        }
    }

}
