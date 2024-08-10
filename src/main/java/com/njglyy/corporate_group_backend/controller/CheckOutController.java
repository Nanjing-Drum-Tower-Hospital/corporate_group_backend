package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.CheckOut;
import com.njglyy.corporate_group_backend.entity.Inbound;
import com.njglyy.corporate_group_backend.entity.InboundDetail;
import com.njglyy.corporate_group_backend.entity.Result;
import com.njglyy.corporate_group_backend.mapper.CheckOutMapper;
import com.njglyy.corporate_group_backend.mapper.InboundMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
public class CheckOutController {
    @Autowired
    private CheckOutMapper checkOutMapper;
    @RequestMapping(value = "/addCheckOut", method = RequestMethod.GET)
    public Result addCheckOut
            (@RequestParam(value = "type", required = false) String type,
             @RequestParam(value = "beginDate", required = false) LocalDate beginDate,
             @RequestParam(value = "endDate", required = false) LocalDate endDate,
             @RequestParam(value = "remark", required = false) String remark
            ) {
        Result lastCheckOutResult = queryLastValidCheckOut(type);
        CheckOut lastCheckOut = (CheckOut) lastCheckOutResult.getData();
        if(beginDate.isAfter(endDate)){
            return new Result(400, "结账开始日期不能晚于结账结束日期！", null);
        }
        if (endDate.isAfter(LocalDate.now())) {
            return new Result(400, "结账结束日期不得晚于当前时间！", null);
        }
        if(lastCheckOut==null){
            checkOutMapper.addCheckOut(type,beginDate,endDate,LocalDate.now(),remark,"valid");
            return new Result(200, "添加成功！", null);
        }
        if(lastCheckOut.getCheckOutEndDate().isAfter(beginDate)){
            return new Result(400, "结账开始日期不能早于上一次结账结束日期！", null);
        }
        if (!lastCheckOut.getCheckOutEndDate().plusDays(1).equals(beginDate)) {
            return new Result(400, "结账开始日期必须在上一次结账结束日期的后一天！", null);
        }
        checkOutMapper.addCheckOut(type,beginDate,endDate,LocalDate.now(),remark,"valid");
        return new Result(200, "添加成功！", null);
    }


    @RequestMapping(value = "/cancelCheckOut", method = RequestMethod.GET)
    public Result cancelCheckOut
            (@RequestParam(value = "id", required = false) int id,
             @RequestParam(value = "type", required = false) String type
            ) {
        Result lastValidCheckOutResult = queryLastValidCheckOut(type);
        CheckOut lastValidCheckOut = (CheckOut) lastValidCheckOutResult.getData();
        if(lastValidCheckOut.getId()!=id){
            return new Result(400, "只能取消最近一次结账！", null);
        }
        checkOutMapper.cancelCheckOut(id);
        return new Result(200, "取消结账成功！", null);
    }

    @RequestMapping(value = "/queryLastValidCheckOut", method = RequestMethod.GET)
    public Result queryLastValidCheckOut
            (@RequestParam(value = "type", required = false) String type

            ) {
        CheckOut checkOut = checkOutMapper.queryLastValidCheckOut(type,0,1);
        return new Result(200, null, checkOut);
    }

    @RequestMapping(value = "/queryCheckOutList", method = RequestMethod.GET)
    public Result queryCheckOutList
            (@RequestParam(value = "type", required = false) String type,
             @RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize

            ) {
        int offset = (currentPage - 1) * pageSize;
        List<CheckOut> checkOutList = checkOutMapper.queryCheckOutList(type,offset,pageSize);
        return new Result(200, null, checkOutList);
    }
    @RequestMapping(value = "/queryCheckOutListCount", method = RequestMethod.GET)
    public Result countInboundDetailMachineNoCount
            (@RequestParam(value = "type", required = false) String type,
             @RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {

        int offset = (currentPage - 1) * pageSize;
        int checkOutListCount = checkOutMapper.queryCheckOutListCount(type);

        return new Result(200, null, checkOutListCount);
    }
}
