package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.*;
import com.njglyy.corporate_group_backend.mapper.InboundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin
public class InboundController {
    @Autowired
    private InboundMapper inboundMapper;



    @RequestMapping(value = "/inboundAccountingReversal", method = RequestMethod.GET)
    public Result inboundAccountingReversal
            (@RequestParam(value = "inboundNo", required = false) String inboundNo
            ) {

        Inbound inbound = inboundMapper.queryInboundByInboundNo(inboundNo);


        inbound.setInboundNo(null);
        inbound.setRemark(inboundNo + " 冲红");
        inbound.setAccountingReversalInboundNo(inboundNo);
        inbound.setEntryType("reversal");
        String newInboundNoString = (String) addOrUpdateInbound(inbound).getData();
        inbound = inboundMapper.queryInboundByInboundNo(inboundNo);

        inboundMapper.updateInbound(inbound.getInboundNo(),
                inbound.getSupplierId(), inbound.getRemark(), newInboundNoString,"original");
        for(InboundDetail inboundDetail:inbound.getInboundDetailList()){


            inboundMapper.addInboundDetail(newInboundNoString, inboundDetail.getItemId(),
                    inboundDetail.getItemAmount().negate(), "冲红");
        }



        return new Result(200, "冲红成功！", null);
    }

    @RequestMapping(value = "/addOrUpdateInbound", method = RequestMethod.POST)
    public Result addOrUpdateInbound
            (@RequestBody Inbound inbound
            ) {
        if(inbound.getSupplierId()==0)
            return new Result(400, "请选择供应商！", null);

        if (inbound.getInboundNo() != null) {
            inboundMapper.updateInbound(inbound.getInboundNo(),
                    inbound.getSupplierId(), inbound.getRemark(), inbound.getAccountingReversalInboundNo(),inbound.getEntryType());
            return new Result(200, "修改成功！", null);
        } else {
            String newLeftInboundNoString = "";
            String newRightInboundNoString = "00001";
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
            newLeftInboundNoString = today.format(formatter);

            List<Inbound> topInboundList = inboundMapper.queryInboundList(0, 1);

            if (topInboundList.size() != 0) {
                String inboundNoString = topInboundList.get(0).getInboundNo();
                String leftInboundNoString = inboundNoString.substring(0, 6);
                String rightInboundNoString = inboundNoString.substring(6, 11);
                int rightInboundNo = Integer.parseInt(rightInboundNoString);
                if (newLeftInboundNoString.equals(leftInboundNoString)) {
                    newRightInboundNoString = String.format("%05d", rightInboundNo + 1);
                }

            }

            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String newInboundDate = today.format(formatter);


            String newInboundNoString = newLeftInboundNoString + newRightInboundNoString;
            inboundMapper.addInbound(newInboundNoString, LocalDate.parse(newInboundDate), inbound.getSupplierId(),
                    inbound.getRemark(), inbound.getAccountingReversalInboundNo(),inbound.getEntryType());
            return new Result(200, "添加成功！", newInboundNoString);
        }

    }

    @RequestMapping(value = "/deleteInboundByInboundNo", method = RequestMethod.GET)
    public Result deleteInbound
            (@RequestParam(value = "inboundNo", required = false) String inboundNo
            ) {

        Inbound inbound = inboundMapper.queryInboundByInboundNo(inboundNo);
        Inbound inboundReversal = inboundMapper.queryInboundByInboundNo(inbound.getAccountingReversalInboundNo());
        if(inboundReversal!=null){
            inboundMapper.updateInbound(inboundReversal.getInboundNo(),
                    inboundReversal.getSupplierId(), inboundReversal.getRemark(), null,null);
        }
        inboundMapper.deleteInboundDetailListByInboundNo(inboundNo);
        inboundMapper.deleteInboundByInboundNo(inboundNo);
        return new Result(200, "删除成功！", null);
    }


    @RequestMapping(value = "/queryInboundDetailList", method = RequestMethod.GET)
    public Result queryInboundDetailMachineNoCount
            (@RequestParam(value = "inboundNo", required = false) String inboundNo,
             @RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {

        int offset = (currentPage - 1) * pageSize;
        List<InboundDetail> inboundDetailList = inboundMapper.queryInboundDetailListByInboundNo(inboundNo, offset, pageSize);

        return new Result(200, null, inboundDetailList);
    }


    @RequestMapping(value = "/queryInboundDetailListCount", method = RequestMethod.GET)
    public Result countInboundDetailMachineNoCount
            (@RequestParam(value = "inboundNo", required = false) String inboundNo,
             @RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {

        int offset = (currentPage - 1) * pageSize;
        int inboundDetailsCount = inboundMapper.queryInboundDetailListCountByInboundNo(inboundNo);

        return new Result(200, null, inboundDetailsCount);
    }


    @RequestMapping(value = "/addOrUpdateInboundDetail", method = RequestMethod.POST)
    public Result addOrUpdateInboundDetail
            (@RequestBody List<InboundDetail> dialogInboundDetail

            ) {
        try {
            System.out.println(dialogInboundDetail);

            InboundDetail dialogInboundDetailOld = dialogInboundDetail.get(0);
            InboundDetail dialogInboundDetailNew = dialogInboundDetail.get(1);
            if (dialogInboundDetailNew.getItemAmount().equals(0))
                return new Result(400, "数量不能为0！", null);
            if (dialogInboundDetailOld.getId() == 0) {
                inboundMapper.addInboundDetail(dialogInboundDetailNew.getInboundNo(), dialogInboundDetailNew.getItemId(),
                        dialogInboundDetailNew.getItemAmount(), dialogInboundDetailNew.getRemark());
            } else {
                inboundMapper.updateInboundDetailById(dialogInboundDetailNew.getId(), dialogInboundDetailNew.getInboundNo(),
                        dialogInboundDetailNew.getItemId(), dialogInboundDetailNew.getItemAmount(),
                        dialogInboundDetailNew.getRemark());
            }


            return new Result(200, "添加成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Result(500, "Error deleting item: " + e.getMessage(), null);
        }
    }

    @RequestMapping(value = "/deleteInboundDetailByInboundNoAndItemId", method = RequestMethod.GET)
    public Result deleteInboundDetailByInboundNoAndItemId
            (@RequestParam(value = "inboundNo", required = false) String inboundNo,
             @RequestParam(value = "itemId", required = false) int itemId
            ) {

        inboundMapper.deleteInboundDetailByInboundNoAndItemId(inboundNo, itemId);
        return new Result(200, "删除成功！", null);
    }


    @RequestMapping(value = "/queryInboundItemListByInboundNoAndItemId", method = RequestMethod.GET)
    public Result queryInboundItemListByInboundNoAndItemId
            (@RequestParam(value = "inboundNo", required = false) String inboundNo,
             @RequestParam(value = "itemId", required = false) int itemId
            ) {

        InboundDetail InboundItemList = inboundMapper.queryInboundDetailListByInboundNoAndItemId(inboundNo, itemId);
        return new Result(200, null, InboundItemList);
    }
//
//
    @RequestMapping(value = "/queryInboundList", method = RequestMethod.GET)
    public Result queryInboundList
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<Inbound> inboundList = inboundMapper.queryInboundList(offset, pageSize);
        return new Result(200, null, inboundList);
    }

    @RequestMapping(value = "/queryInboundListCount", method = RequestMethod.GET)
    public Result queryInboundCount
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        int inboundsCount = inboundMapper.queryInboundListCount();
        return new Result(200, null, inboundsCount);
    }


}
