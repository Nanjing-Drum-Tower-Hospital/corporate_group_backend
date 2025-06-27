package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.PurchaseRecord;
import com.njglyy.corporate_group_backend.entity.Result;
import com.njglyy.corporate_group_backend.mapper.PurchaseRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin
public class PurchaseRecordController {
    @Autowired
    private PurchaseRecordMapper purchaseRecordMapper;
    @RequestMapping(value = "/addOrUpdatePurchaseRecord", method = RequestMethod.POST)
    public Result addOrUpdatePurchaseRecord
            (@RequestBody PurchaseRecord purchaseRecord
            ) {
        System.out.println(purchaseRecord);
        if(purchaseRecord.getPurchaseDate() == null || purchaseRecord.getItemBrand() ==null
                || purchaseRecord.getItemModel() == null || purchaseRecord.getMachineNo() == null
                || purchaseRecord.getMachineManufactureDate() == null )
        {
            return new Result(400,"购买时间不能为空！",null);
        }
        if(purchaseRecord.getCustomerId() == 0){
            return new Result(400,"客户信息不能为空！",null);
        }
        else{
            if(purchaseRecord.getId()!=0){
                purchaseRecordMapper.updatePurchaseRecord(purchaseRecord.getId(),purchaseRecord.getCustomerId(),
                        purchaseRecord.getPurchaseDate(), purchaseRecord.getItemBrand(), purchaseRecord.getItemModel(),
                        purchaseRecord.getItemAmount(), purchaseRecord.getMachineNo(), purchaseRecord.getMachineManufactureDate(),
                        purchaseRecord.getChargerAmount(), purchaseRecord.getReceiverAmount(),
                        purchaseRecord.getPayerName(),
                        purchaseRecord.getPayerPhoneNumber(),purchaseRecord.getPaymentMethod(),
                        purchaseRecord.getPaymentDate(),purchaseRecord.getPaymentAmount(),
                        purchaseRecord.getRecommendationStaffId(), purchaseRecord.getRecommendationStaffName(),
                        purchaseRecord.getRemark());
            }
            else{
                purchaseRecordMapper.addPurchaseRecord(purchaseRecord.getCustomerId(),
                        purchaseRecord.getPurchaseDate(), purchaseRecord.getItemBrand(), purchaseRecord.getItemModel(),
                        purchaseRecord.getItemAmount(), purchaseRecord.getMachineNo(), purchaseRecord.getMachineManufactureDate(),
                        purchaseRecord.getChargerAmount(), purchaseRecord.getReceiverAmount(),
                        purchaseRecord.getPayerName(),
                        purchaseRecord.getPayerPhoneNumber(),purchaseRecord.getPaymentMethod(),
                        purchaseRecord.getPaymentDate(),purchaseRecord.getPaymentAmount(),
                        purchaseRecord.getRecommendationStaffId(), purchaseRecord.getRecommendationStaffName(),
                        purchaseRecord.getRemark());
            }

        }

        return new Result(200,"添加成功！",null);
    }

    @RequestMapping(value = "/deletePurchaseRecordById", method = RequestMethod.GET)
    public Result deletePurchaseRecord
            (@RequestParam(value = "id", required = false) int id
            ) {
        purchaseRecordMapper.deletePurchaseRecordById(id);
        return new Result(200, "删除成功！", null);
    }

    @RequestMapping(value = "/queryPurchaseRecordList", method = RequestMethod.GET)
    public Result queryPurchaseRecordList
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<PurchaseRecord> purchaseRecordList = purchaseRecordMapper.queryPurchaseRecordList(offset, pageSize);
        return new Result(200, null, purchaseRecordList);
    }

    @RequestMapping(value = "/queryPurchaseRecordListCount", method = RequestMethod.GET)
    public Result queryPurchaseRecordListCount
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int purchaseRecordListCount = purchaseRecordMapper.queryPurchaseRecordListCount();
        return new Result(200, null, purchaseRecordListCount);
    }

    @RequestMapping(value = "/queryPurchaseRecordByIdAndCustomerId", method = RequestMethod.GET)
    public Result queryPurchaseRecordCustomerListByIdAndCustomerId
            (@RequestParam(value = "id", required = false) int id,
             @RequestParam(value = "customerId", required = false) int customerId) {
        PurchaseRecord purchaseRecord = purchaseRecordMapper.queryPurchaseRecordByIdAndCustomerId(id, customerId);
        return new Result(200, null, purchaseRecord);
    }

    @RequestMapping(value = "/queryPurchaseRecordByCustomerName", method = RequestMethod.GET)
    public Result queryPurchaseRecordByCustomerName
            (@RequestParam(value = "input", required = false) String customerName) {
        String inputStr = "%"+customerName+"%";
        List<PurchaseRecord> purchaseRecordList = purchaseRecordMapper.queryPurchaseRecordByCustomerName(inputStr);
        return new Result(200, null, purchaseRecordList);
    }
}
