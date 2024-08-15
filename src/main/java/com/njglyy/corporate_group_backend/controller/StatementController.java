package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.*;
import com.njglyy.corporate_group_backend.mapper.*;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;



@RestController
@CrossOrigin
public class StatementController {
    @Autowired
    private InboundMapper inboundMapper;
    @Autowired
    private OutboundMapper outboundMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ManufacturerMapper manufacturerMapper;
    @Autowired
    private CheckOutMapper checkOutMapper;
    @Autowired
    private UnitRatioMapper unitRatioMapper;

    @RequestMapping(value = "/exportInboundStatement", method = RequestMethod.POST)
    public Result exportInboundStatement
            (@RequestBody Inbound inbound
            ) {
        Inbound inboundDB = inboundMapper.queryInboundByInboundNo(inbound.getInboundNo());

        // Create a workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("采购入库单");

        // Create headers
        String[] headers = {"产品编码", "产品名称", "单位", "数量", "单价", "税额", "金额", "含税单价", "价税合计", "税率"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Populate details
        int rowNum = 1;
        for (InboundDetail detail : inboundDB.getInboundDetailList()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(detail.getItem().getCode());
            row.createCell(1).setCellValue(detail.getItem().getName());
            row.createCell(2).setCellValue(detail.getItem().getUnitName());
            row.createCell(3).setCellValue(String.valueOf(detail.getItemAmount()));
            row.createCell(4).setCellValue(String.valueOf(detail.getItem().getUnitPriceExcludingTax().setScale(2, RoundingMode.HALF_UP)));
            row.createCell(5).setCellValue(String.valueOf(detail.getInboundDetailTax().setScale(2, RoundingMode.HALF_UP)));
            row.createCell(6).setCellValue(String.valueOf(detail.getInboundDetailPriceExcludingTax().setScale(2, RoundingMode.HALF_UP)));
            row.createCell(7).setCellValue(String.valueOf(detail.getItem().getUnitPriceIncludingTax().setScale(2, RoundingMode.HALF_UP)));
            row.createCell(8).setCellValue(String.valueOf(detail.getInboundDetailPriceIncludingTax().setScale(2, RoundingMode.HALF_UP)));
            row.createCell(9).setCellValue("13%");
        }

// Insert blank row
        sheet.createRow(rowNum++);

// Continue with more data after the blank row
        Row nextRow = sheet.createRow(rowNum++);
        nextRow.createCell(0).setCellValue(String.valueOf("合计"));
        nextRow.createCell(5).setCellValue(String.valueOf(inboundDB.getInboundTax().setScale(2, RoundingMode.HALF_UP)));
        nextRow.createCell(6).setCellValue(String.valueOf(inboundDB.getInboundPriceExcludingTax().setScale(2, RoundingMode.HALF_UP)));
        nextRow.createCell(8).setCellValue(String.valueOf(inboundDB.getInboundPriceIncludingTax().setScale(2, RoundingMode.HALF_UP)));
        // Auto size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Define the file path for saving the Excel file locally
        String filePath = inboundDB.getInboundNo() + "采购入库单.xlsx";  // Update with your desired file path

        // Write the workbook to a file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            byte[] bytes = IOUtils.toByteArray(fileInputStream);
            String base64 = Base64.getEncoder().encodeToString(bytes);
            FileData fileData = new FileData();
            fileData.setFileName(filePath);
            fileData.setFileContent(base64);
            Result result = new Result();
            result.setCode(200); // Success code
            result.setMessage("导出成功！");
            result.setData(fileData);

            return result;
        } catch (Exception e) {
            // Handle exceptions
            return new Result(400, "生成失败！", null);
        }


    }

    @RequestMapping(value = "/exportOutboundStatement", method = RequestMethod.POST)
    public Result exportOutboundStatement
            (@RequestBody Outbound outbound
            ) {
        Outbound outboundDB = outboundMapper.queryOutboundByOutboundNo(outbound.getOutboundNo());

        // Create a workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("销售出库单");

        // Create headers
        String[] headers = {"产品编码", "产品名称", "单位", "数量", "单价", "税额", "金额", "含税单价", "价税合计", "税率"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Populate details
        int rowNum = 1;
        for (OutboundDetail detail : outboundDB.getOutboundDetailList()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(detail.getItem().getCode());
            row.createCell(1).setCellValue(detail.getItem().getName());
            row.createCell(2).setCellValue(detail.getItem().getUnitName());
            row.createCell(3).setCellValue(String.valueOf(detail.getItemAmount()));
            row.createCell(4).setCellValue(String.valueOf(detail.getItem().getUnitPriceExcludingTax().setScale(2, RoundingMode.HALF_UP)));
            row.createCell(5).setCellValue(String.valueOf(detail.getOutboundDetailTax().setScale(2, RoundingMode.HALF_UP)));
            row.createCell(6).setCellValue(String.valueOf(detail.getOutboundDetailPriceExcludingTax().setScale(2, RoundingMode.HALF_UP)));
            row.createCell(7).setCellValue(String.valueOf(detail.getItem().getUnitPriceIncludingTax().setScale(2, RoundingMode.HALF_UP)));
            row.createCell(8).setCellValue(String.valueOf(detail.getOutboundDetailPriceIncludingTax().setScale(2, RoundingMode.HALF_UP)));
            row.createCell(9).setCellValue("13%");
        }

// Insert blank row
        sheet.createRow(rowNum++);

// Continue with more data after the blank row
        Row nextRow = sheet.createRow(rowNum++);
        nextRow.createCell(0).setCellValue(String.valueOf("合计"));
        nextRow.createCell(5).setCellValue(String.valueOf(outboundDB.getOutboundTax().setScale(2, RoundingMode.HALF_UP)));
        nextRow.createCell(6).setCellValue(String.valueOf(outboundDB.getOutboundPriceExcludingTax().setScale(2, RoundingMode.HALF_UP)));
        nextRow.createCell(8).setCellValue(String.valueOf(outboundDB.getOutboundPriceIncludingTax().setScale(2, RoundingMode.HALF_UP)));
        // Auto size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Define the file path for saving the Excel file locally
        String filePath = outboundDB.getOutboundNo() + "销售出库单.xlsx";  // Update with your desired file path

        // Write the workbook to a file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            byte[] bytes = IOUtils.toByteArray(fileInputStream);
            String base64 = Base64.getEncoder().encodeToString(bytes);
            FileData fileData = new FileData();
            fileData.setFileName(filePath);
            fileData.setFileContent(base64);
            Result result = new Result();
            result.setCode(200); // Success code
            result.setMessage("导出成功！");
            result.setData(fileData);

            return result;
        } catch (Exception e) {
            // Handle exceptions
            return new Result(400, "生成失败！", null);
        }


    }
    // Method to format value with replacement if zero
    private String getFormattedValue(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) == 0 ? "0.0000000000" : String.valueOf(value.setScale(10, RoundingMode.HALF_UP));
    }

    @RequestMapping(value = "/inventoryManagementStatement", method = RequestMethod.GET)
    public Result inventoryManagementStatement
            (@RequestParam(value = "beginDate", required = false) LocalDate beginDate,
             @RequestParam(value = "endDate", required = false) LocalDate endDate,
                @RequestParam(value = "mainUnitName", required = false) String mainUnitName
            ) {
        if (beginDate == null || endDate == null) {
            return new Result(400, "日期不能为空！", null);
        }
        if (mainUnitName == null) {
            return new Result(400, "合计单位不能为空！", null);
        }
        if (beginDate.isAfter(endDate)) {
            return new Result(400, "开始日期不能晚于结束日期！", null);
        }
        List<CheckOut> checkOutInboundList = checkOutMapper.queryCheckOutList("inbound",0, Integer.MAX_VALUE);
        List<CheckOut> checkOutOutboundList = checkOutMapper.queryCheckOutList("outbound",0, Integer.MAX_VALUE);

        boolean isNotInsideAnyInboundCheckOut = checkOutInboundList.stream()
                .noneMatch(checkOut ->
                        !beginDate.isBefore(checkOut.getCheckOutBeginDate()) &&
                                !endDate.isAfter(checkOut.getCheckOutEndDate())
                );

        boolean isNotInsideAnyOutboundCheckOut = checkOutOutboundList.stream()
                .noneMatch(checkOut ->
                        !beginDate.isBefore(checkOut.getCheckOutBeginDate()) &&
                                !endDate.isAfter(checkOut.getCheckOutEndDate())
                );
        if (isNotInsideAnyInboundCheckOut || isNotInsideAnyOutboundCheckOut) {
            return new Result(400, "该时间区间内有未结算的账单！", null);
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("收发存汇总");


        String[] headers = {"货品名称", "类型","单位",  "期初数量", "期初单价", "金额", "购入数量", "购入单价", "购入金额", "发出数量", "发出单价", "发出金额", "结存数量", "结存单价", "结存金额"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        List<Inbound> inboundList = inboundMapper.queryInboundList(0, Integer.MAX_VALUE);
        List<Outbound> outboundList = outboundMapper.queryOutboundList(0, Integer.MAX_VALUE);
        List<Manufacturer> manufacturerList = manufacturerMapper.queryManufacturerList(0, Integer.MAX_VALUE);
        List<Item> itemList = itemMapper.queryItemListByCondition(null,null,null,0, Integer.MAX_VALUE);
        int rowNum = 1;
        BigDecimal totalInitialPrice = new BigDecimal(0);
        BigDecimal totalInboundPrice = new BigDecimal(0);
        BigDecimal totalOutboundPrice = new BigDecimal(0);
        BigDecimal totalFinalPrice = new BigDecimal(0);
        BigDecimal totalInitialCount = new BigDecimal(0);
        BigDecimal totalInboundCount = new BigDecimal(0);
        BigDecimal totalOutboundCount = new BigDecimal(0);
        BigDecimal totalFinalCount = new BigDecimal(0);

        for (Manufacturer manufacturer : manufacturerList) {
            BigDecimal totalInitialManufacturerPrice = new BigDecimal(0);
            BigDecimal totalInboundManufacturerPrice = new BigDecimal(0);
            BigDecimal totalOutboundManufacturerPrice = new BigDecimal(0);
            BigDecimal totalFinalManufacturerPrice = new BigDecimal(0);
            BigDecimal totalInitialManufacturerCount = new BigDecimal(0);
            BigDecimal totalInboundManufacturerCount = new BigDecimal(0);
            BigDecimal totalOutboundManufacturerCount = new BigDecimal(0);
            BigDecimal totalFinalManufacturerCount = new BigDecimal(0);
            for (Item item : itemList){
                if(item.getManufacturerId() == manufacturer.getId()){
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(item.getName());
                    row.createCell(1).setCellValue(item.getType());
                    row.createCell(2).setCellValue(item.getUnitName());



                    BigDecimal initialItemAmount = new BigDecimal(0);
                    for(Inbound inbound : inboundList){
                        if(inbound.getInboundDate().isBefore(beginDate)){
                            for(InboundDetail inboundDetail : inbound.getInboundDetailList()){
                                if(inboundDetail.getItemId() == item.getId()){
                                    initialItemAmount = initialItemAmount.add(inboundDetail.getItemAmount()) ;
                                }
                            }
                        }
                    }
                    for(Outbound outbound : outboundList){
                        if(outbound.getOutboundDate().isBefore(beginDate)){
                            for(OutboundDetail outboundDetail : outbound.getOutboundDetailList()){
                                if(outboundDetail.getItemId() == item.getId()){
                                    initialItemAmount = initialItemAmount.add(outboundDetail.getItemAmount().negate());
                                }
                            }
                        }
                    }
                    row.createCell(3).setCellValue(String.valueOf(initialItemAmount));


                    BigDecimal totalInitialItemPrice = item.getUnitPriceExcludingTax().multiply(initialItemAmount);
                    row.createCell(4).setCellValue(getFormattedValue(item.getUnitPriceExcludingTax()));
                    row.createCell(5).setCellValue(getFormattedValue(totalInitialItemPrice));
                    totalInitialManufacturerPrice=totalInitialManufacturerPrice.add(totalInitialItemPrice);




                    BigDecimal inboundItemAmount = new BigDecimal(0);
                    for(Inbound inbound : inboundList){
                        if((inbound.getInboundDate().isAfter(beginDate) || inbound.getInboundDate().isEqual(beginDate))
                        && (inbound.getInboundDate().isBefore(endDate) || inbound.getInboundDate().isEqual(endDate))){
                            for(InboundDetail inboundDetail : inbound.getInboundDetailList()){
                                if(inboundDetail.getItemId() == item.getId()){
                                    inboundItemAmount = inboundItemAmount.add(inboundDetail.getItemAmount()) ;
                                }
                            }
                        }
                    }
                    row.createCell(6).setCellValue(String.valueOf(inboundItemAmount));
                    BigDecimal totalInboundItemPrice = item.getUnitPriceExcludingTax().multiply(inboundItemAmount);
                    row.createCell(7).setCellValue(getFormattedValue(item.getUnitPriceExcludingTax()));
                    row.createCell(8).setCellValue(getFormattedValue(totalInboundItemPrice));
                    totalInboundManufacturerPrice=totalInboundManufacturerPrice.add(totalInboundItemPrice);




                    BigDecimal outboundItemAmount = new BigDecimal(0);
                    for(Outbound outbound : outboundList){
                        if((outbound.getOutboundDate().isAfter(beginDate) || outbound.getOutboundDate().isEqual(beginDate))
                                && (outbound.getOutboundDate().isBefore(endDate) || outbound.getOutboundDate().isEqual(endDate))){
                            for(OutboundDetail outboundDetail : outbound.getOutboundDetailList()){
                                if(outboundDetail.getItemId() == item.getId()){
                                    outboundItemAmount = outboundItemAmount.add(outboundDetail.getItemAmount()) ;
                                }
                            }
                        }
                    }
                    row.createCell(9).setCellValue(String.valueOf(outboundItemAmount));
                    BigDecimal totalOutboundItemPrice = item.getUnitPriceExcludingTax().multiply(outboundItemAmount);
                    row.createCell(10).setCellValue(getFormattedValue(item.getUnitPriceExcludingTax()));
                    row.createCell(11).setCellValue(getFormattedValue(totalOutboundItemPrice));
                    totalOutboundManufacturerPrice=totalOutboundManufacturerPrice.add(totalOutboundItemPrice);



                    BigDecimal finalItemAmount = new BigDecimal(0);
                    for(Inbound inbound : inboundList){
                        if(inbound.getInboundDate().isBefore(endDate) || inbound.getInboundDate().isEqual(endDate)){
                            for(InboundDetail inboundDetail : inbound.getInboundDetailList()){
                                if(inboundDetail.getItemId() == item.getId()){
                                    finalItemAmount = finalItemAmount.add(inboundDetail.getItemAmount()) ;
                                }
                            }
                        }
                    }
                    for(Outbound outbound : outboundList){
                        if(outbound.getOutboundDate().isBefore(endDate) || outbound.getOutboundDate().isEqual(endDate)){
                            for(OutboundDetail outboundDetail : outbound.getOutboundDetailList()){
                                if(outboundDetail.getItemId() == item.getId()){
                                    finalItemAmount = finalItemAmount.add(outboundDetail.getItemAmount().negate());
                                }
                            }
                        }
                    }
                    row.createCell(12).setCellValue(String.valueOf(finalItemAmount));
                    BigDecimal totalFinalItemPrice = item.getUnitPriceExcludingTax().multiply(finalItemAmount);
                    row.createCell(13).setCellValue(getFormattedValue(item.getUnitPriceExcludingTax()));
                    row.createCell(14).setCellValue(getFormattedValue(totalFinalItemPrice));
                    totalFinalManufacturerPrice=totalFinalManufacturerPrice.add(totalFinalItemPrice);


                    UnitRatio unitRatio = unitRatioMapper.queryUnitRatioByUnitName(item.getUnitName(),mainUnitName);
                    if(item.getUnitName().equals(mainUnitName)){
                        totalInitialManufacturerCount=totalInitialManufacturerCount.add(initialItemAmount);
                        totalInboundManufacturerCount=totalInboundManufacturerCount.add(inboundItemAmount);
                        totalOutboundManufacturerCount=totalOutboundManufacturerCount.add(outboundItemAmount);
                        totalFinalManufacturerCount=totalFinalManufacturerCount.add(finalItemAmount);
                    }
                    else{
                        if(unitRatio==null){
                            return new Result(400, item.getUnitName()+"与"+mainUnitName+"的单位换算关系不存在！", null);
                        }
                        else{
                            if(unitRatio.getUnitNameLeft().equals(mainUnitName)){
                                totalInitialManufacturerCount=totalInitialManufacturerCount.add(initialItemAmount.divide(new BigDecimal(unitRatio.getRatio()),1, RoundingMode.HALF_UP));
                                totalInboundManufacturerCount=totalInboundManufacturerCount.add(inboundItemAmount.divide(new BigDecimal(unitRatio.getRatio()),1, RoundingMode.HALF_UP));
                                totalOutboundManufacturerCount=totalOutboundManufacturerCount.add(outboundItemAmount.divide(new BigDecimal(unitRatio.getRatio()),1, RoundingMode.HALF_UP));
                                totalFinalManufacturerCount=totalFinalManufacturerCount.add(finalItemAmount.divide(new BigDecimal(unitRatio.getRatio()),1, RoundingMode.HALF_UP));
                            }
                            else{
                                totalInitialManufacturerCount=totalInitialManufacturerCount.add(initialItemAmount.multiply(new BigDecimal(unitRatio.getRatio())));
                                totalInboundManufacturerCount=totalInboundManufacturerCount.add(inboundItemAmount.multiply(new BigDecimal(unitRatio.getRatio())));
                                totalOutboundManufacturerCount=totalOutboundManufacturerCount.add(outboundItemAmount.multiply(new BigDecimal(unitRatio.getRatio())));
                                totalFinalManufacturerCount=totalFinalManufacturerCount.add(finalItemAmount.multiply(new BigDecimal(unitRatio.getRatio())));


                            }
                        }
                    }

                }

            }
            totalInitialPrice=totalInitialPrice.add(totalInitialManufacturerPrice);
            totalInboundPrice=totalInboundPrice.add(totalInboundManufacturerPrice);
            totalOutboundPrice=totalOutboundPrice.add(totalOutboundManufacturerPrice);
            totalFinalPrice=totalFinalPrice.add(totalFinalManufacturerPrice);
            totalInitialCount=totalInitialCount.add(totalInitialManufacturerCount);
            totalInboundCount=totalInboundCount.add(totalInboundManufacturerCount);
            totalOutboundCount=totalOutboundCount.add(totalOutboundManufacturerCount);
            totalFinalCount=totalFinalCount.add(totalFinalManufacturerCount);
            Row nextRow = sheet.createRow(rowNum++);
            nextRow.createCell(0).setCellValue("小计（" + manufacturer.getManufacturerName() + "）");
            nextRow.createCell(2).setCellValue(mainUnitName);
            nextRow.createCell(3).setCellValue(totalInitialManufacturerCount.toString());
            nextRow.createCell(5).setCellValue(getFormattedValue(totalInitialManufacturerPrice));
            nextRow.createCell(6).setCellValue(totalInboundManufacturerCount.toString());
            nextRow.createCell(8).setCellValue(getFormattedValue(totalInboundManufacturerPrice));
            nextRow.createCell(9).setCellValue(totalOutboundManufacturerCount.toString());
            nextRow.createCell(11).setCellValue(getFormattedValue(totalOutboundManufacturerPrice));
            nextRow.createCell(12).setCellValue(totalFinalManufacturerCount.toString());
            nextRow.createCell(14).setCellValue(getFormattedValue(totalFinalManufacturerPrice));
        }
        Row nextRow = sheet.createRow(rowNum++);
        nextRow.createCell(0).setCellValue("合计");
        nextRow.createCell(2).setCellValue(mainUnitName);
        nextRow.createCell(3).setCellValue(totalInitialCount.toString());
        nextRow.createCell(5).setCellValue(getFormattedValue(totalInitialPrice));
        nextRow.createCell(6).setCellValue(totalInboundCount.toString());
        nextRow.createCell(8).setCellValue(getFormattedValue(totalInboundPrice));
        nextRow.createCell(9).setCellValue(totalOutboundCount.toString());
        nextRow.createCell(11).setCellValue(getFormattedValue(totalOutboundPrice));
        nextRow.createCell(12).setCellValue(totalFinalCount.toString());
        nextRow.createCell(14).setCellValue(getFormattedValue(totalFinalPrice));

        nextRow = sheet.createRow(rowNum++);
        nextRow.createCell(0).setCellValue("合计（保留小数点后2位)");
        nextRow.createCell(2).setCellValue(mainUnitName);
        nextRow.createCell(3).setCellValue(totalInitialCount.toString());
        nextRow.createCell(5).setCellValue(totalInitialPrice.setScale(2, RoundingMode.HALF_UP).toString());
        nextRow.createCell(6).setCellValue(totalInboundCount.toString());
        nextRow.createCell(8).setCellValue(totalInboundPrice.setScale(2, RoundingMode.HALF_UP).toString());
        nextRow.createCell(9).setCellValue(totalOutboundCount.toString());
        nextRow.createCell(11).setCellValue(totalOutboundPrice.setScale(2, RoundingMode.HALF_UP).toString());
        nextRow.createCell(12).setCellValue(totalFinalCount.toString());
        nextRow.createCell(14).setCellValue(totalFinalPrice.setScale(2, RoundingMode.HALF_UP).toString());



//        // Auto size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Define the file path for saving the Excel file locally
        String filePath = beginDate + "至" + endDate + "收发存汇总表.xlsx";  // Update with your desired file path

        // Write the workbook to a file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            byte[] bytes = IOUtils.toByteArray(fileInputStream);
            String base64 = Base64.getEncoder().encodeToString(bytes);
            FileData fileData = new FileData();
            fileData.setFileName(filePath);
            fileData.setFileContent(base64);
            Result result = new Result();
            result.setCode(200); // Success code
            result.setMessage("导出成功！");
            result.setData(fileData);

            return result;
        } catch (Exception e) {
            // Handle exceptions
            return new Result(400, "生成失败！", null);
        }

    }
}
