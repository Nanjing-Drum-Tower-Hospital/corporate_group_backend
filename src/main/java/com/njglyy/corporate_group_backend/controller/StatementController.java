package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.*;
import com.njglyy.corporate_group_backend.mapper.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.poi.poifs.crypt.HashAlgorithm;
import org.apache.poi.ss.usermodel.*;

import java.awt.Color;
import java.awt.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openxmlformats.schemas.spreadsheetml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import javax.imageio.ImageIO;
import org.apache.poi.openxml4j.opc.*;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorksheet;
import org.apache.poi.xssf.usermodel.XSSFSimpleShape;

import static com.njglyy.corporate_group_backend.utils.WaterMark.addWatermarkToSheet;
import static com.njglyy.corporate_group_backend.utils.WaterMark.generateImageWithText;


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
    @Autowired
    private UserMapper userMapper;


    @RequestMapping(value = "/inboundStatement", method = RequestMethod.POST)
    public Result inboundStatement
            (@RequestBody Inbound inbound,
             HttpServletRequest request
            ) {
        Inbound inboundDB = inboundMapper.queryInboundByInboundNo(inbound.getInboundNo());
        if(inboundDB.getInboundDetailList().size()==0){
            return new Result(400, "入库单无明细！", null);
        }
        User user=userMapper.queryUserByUsername(request.getHeader("username"));
        System.out.println(user);

        String inputFile = "采购入库单模板.xlsx";

        try (FileInputStream fileIn = new FileInputStream(inputFile);
             XSSFWorkbook workbook = new XSSFWorkbook(fileIn)) {
            // Define border style
            CellStyle borderedStyle = workbook.createCellStyle();
            borderedStyle.setBorderBottom(BorderStyle.THIN);
            borderedStyle.setBorderTop(BorderStyle.THIN);
            borderedStyle.setBorderLeft(BorderStyle.THIN);
            borderedStyle.setBorderRight(BorderStyle.THIN);
            borderedStyle.setWrapText(true);
            // Access the first sheet
            Sheet sheet = workbook.getSheetAt(0);

            // Check for merged regions that might contain the cell A2/B2
            for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
                CellRangeAddress region = sheet.getMergedRegion(i);
                if (region.isInRange(1, 0)) {  // Row 2, Column A (index is 0-based)
                    // Get the first cell of the merged region
                    Row row = sheet.getRow(region.getFirstRow());
                    Cell cell = row.getCell(region.getFirstColumn(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellValue("业务类型：普通采购");  // Change the value
                }
                if (region.isInRange(1, 3)) {  // Row 2, Column A (index is 0-based)
                    // Get the first cell of the merged region
                    Row row = sheet.getRow(region.getFirstRow());
                    Cell cell = row.getCell(region.getFirstColumn(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellValue("发票号："+inboundDB.getFapiaoNo());  // Change the value
                }
                if (region.isInRange(1, 6)) {  // Row 2, Column A (index is 0-based)
                    // Get the first cell of the merged region
                    Row row = sheet.getRow(region.getFirstRow());
                    Cell cell = row.getCell(region.getFirstColumn(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellValue("入库日期："+inboundDB.getInboundDate());  // Change the value
                }
                if (region.isInRange(2, 0)) {  // Row 2, Column A (index is 0-based)
                    // Get the first cell of the merged region
                    Row row = sheet.getRow(region.getFirstRow());
                    Cell cell = row.getCell(region.getFirstColumn(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellValue("入库日期："+inboundDB.getSupplier().getSupplierName());  // Change the value
                }
                if (region.isInRange(2, 3)) {  // Row 2, Column A (index is 0-based)
                    // Get the first cell of the merged region
                    Row row = sheet.getRow(region.getFirstRow());
                    Cell cell = row.getCell(region.getFirstColumn(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellValue("入库单号："+inboundDB.getInboundNo());  // Change the value
                }
                if (region.isInRange(2, 6)) {  // Row 2, Column A (index is 0-based)
                    // Get the first cell of the merged region
                    Row row = sheet.getRow(region.getFirstRow());
                    Cell cell = row.getCell(region.getFirstColumn(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellValue("           制单："+user.getFullName());  // Change the value
                }
            }
            int rowNum = 4;
            BigDecimal totalCount = new BigDecimal(0);
            for (InboundDetail detail : inboundDB.getInboundDetailList()) {
                Row row = sheet.getRow(rowNum++);
                row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(detail.getItem().getCode());
                row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(detail.getItem().getName());
                row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(detail.getItem().getUnitName());
                row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(String.valueOf(detail.getItemAmount()));
                row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(String.valueOf(detail.getItem().getUnitPriceExcludingTax().setScale(10, RoundingMode.HALF_UP)));
                row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(String.valueOf(detail.getInboundDetailTax().setScale(2, RoundingMode.HALF_UP)));
                row.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(String.valueOf(detail.getInboundDetailPriceExcludingTax().setScale(2, RoundingMode.HALF_UP)));
                row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(String.valueOf(detail.getItem().getUnitPriceIncludingTax().setScale(2, RoundingMode.HALF_UP)));
                row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(String.valueOf(detail.getInboundDetailPriceIncludingTax().setScale(2, RoundingMode.HALF_UP)));
                row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue("13%");
                row.getCell(10, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(detail.getRemark());
                totalCount=totalCount.add(detail.getItemAmount());
            }

// Insert blank row
            rowNum++;

// Continue with more data after the blank row
            Row row = sheet.getRow(rowNum++);
            row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(String.valueOf("合计"));
            row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(String.valueOf(totalCount));
            row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(String.valueOf(inboundDB.getInboundTax().setScale(2, RoundingMode.HALF_UP)));
            row.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(String.valueOf(inboundDB.getInboundPriceExcludingTax().setScale(2, RoundingMode.HALF_UP)));
            row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(String.valueOf(inboundDB.getInboundPriceIncludingTax().setScale(2, RoundingMode.HALF_UP)));
            // Auto size columns
            for (int rowIndex = 4; rowIndex < rowNum; rowIndex++) {
                row = sheet.getRow(rowIndex);
                if (row == null) continue;  // Skip if the row does not exist

                for (int colIndex = 0; colIndex < 11; colIndex++) {
                    Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellStyle(borderedStyle);
                }
            }
            // Save the workbook as a new file
            for (int colIndex = 0; colIndex < 15; colIndex++) {
                sheet.autoSizeColumn(colIndex);
            }
        // Define the file path for saving the Excel file locally
        String filePath = inboundDB.getInboundNo() + "-采购入库单.xlsx";  // Update with your desired file path

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


    } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @RequestMapping(value = "/outboundStatement", method = RequestMethod.POST)
    public Result outboundStatement
            (@RequestBody Outbound outbound,
             HttpServletRequest request
            ) {
        Outbound outboundDB = outboundMapper.queryOutboundByOutboundNo(outbound.getOutboundNo());
        if(outboundDB.getOutboundDetailList().size()==0){
            return new Result(400, "出库单无明细！", null);
        }
        User user=userMapper.queryUserByUsername(request.getHeader("username"));
        System.out.println(user);


        String inputFile = "销售出库单模板.xlsx";

        try (FileInputStream fileIn = new FileInputStream(inputFile);
             XSSFWorkbook workbook = new XSSFWorkbook(fileIn)) {
            // Define border style
            CellStyle borderedStyle = workbook.createCellStyle();
            borderedStyle.setBorderBottom(BorderStyle.THIN);
            borderedStyle.setBorderTop(BorderStyle.THIN);
            borderedStyle.setBorderLeft(BorderStyle.THIN);
            borderedStyle.setBorderRight(BorderStyle.THIN);
            borderedStyle.setWrapText(true);
            // Access the first sheet
            Sheet sheet = workbook.getSheetAt(0);

            // Check for merged regions that might contain the cell A2/B2
            for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
                CellRangeAddress region = sheet.getMergedRegion(i);
                if (region.isInRange(1, 0)) {  // Row 2, Column A (index is 0-based)
                    // Get the first cell of the merged region
                    Row row = sheet.getRow(region.getFirstRow());
                    Cell cell = row.getCell(region.getFirstColumn(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellValue("业务类型：销售出库");  // Change the value
                }
                if (region.isInRange(1, 2)) {  // Row 2, Column A (index is 0-based)
                    // Get the first cell of the merged region
                    Row row = sheet.getRow(region.getFirstRow());
                    Cell cell = row.getCell(region.getFirstColumn(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    if(outboundDB.getPurchaseRecord()!=null){
                        cell.setCellValue("客户："+outboundDB.getPurchaseRecord().getCustomer().getName());  // Change the value
                    }
                    else{
                        cell.setCellValue("客户：");
                    }
                }
                if (region.isInRange(1, 5)) {  // Row 2, Column A (index is 0-based)
                    // Get the first cell of the merged region
                    Row row = sheet.getRow(region.getFirstRow());
                    Cell cell = row.getCell(region.getFirstColumn(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellValue("出库日期："+outboundDB.getOutboundDate());  // Change the value
                }
                if (region.isInRange(2, 0)) {  // Row 2, Column A (index is 0-based)
                    // Get the first cell of the merged region
                    Row row = sheet.getRow(region.getFirstRow());
                    Cell cell = row.getCell(region.getFirstColumn(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellValue("供货单位："+outboundDB.getOutboundDetailList().get(0).getItem().getManufacturer().getManufacturerName());  // Change the value
                }
                if (region.isInRange(2, 2)) {  // Row 2, Column A (index is 0-based)
                    // Get the first cell of the merged region
                    Row row = sheet.getRow(region.getFirstRow());
                    Cell cell = row.getCell(region.getFirstColumn(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellValue("出库单号："+outboundDB.getOutboundNo());  // Change the value
                }
                if (region.isInRange(2, 5)) {  // Row 2, Column A (index is 0-based)
                    // Get the first cell of the merged region
                    Row row = sheet.getRow(region.getFirstRow());
                    Cell cell = row.getCell(region.getFirstColumn(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellValue("           制单："+user.getFullName());  // Change the value
                }
            }
            int rowNum = 4;
            for (OutboundDetail detail : outboundDB.getOutboundDetailList()) {
                Row row = sheet.getRow(rowNum++);
                row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(detail.getItem().getCode());
                row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(detail.getItem().getName());
                row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(detail.getItem().getUnitName());
                row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(String.valueOf(detail.getItemAmount()));
                row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(detail.getRemark());
            }


            Row row = sheet.getRow(rowNum);

            // Auto size columns
            for (int rowIndex = 4; rowIndex < rowNum; rowIndex++) {
                row = sheet.getRow(rowIndex);
                if (row == null) continue;  // Skip if the row does not exist

                for (int colIndex = 0; colIndex < 8; colIndex++) {
                    Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellStyle(borderedStyle);
                }
            }
            // Save the workbook as a new file
            // Define the file path for saving the Excel file locally
            for (int colIndex = 0; colIndex < 15; colIndex++) {
                sheet.autoSizeColumn(colIndex);
            }
            String filePath = outboundDB.getOutboundNo() + "-销售出库单.xlsx";  // Update with your desired file path

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


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
             @RequestParam(value = "mainUnitName", required = false) String mainUnitName,
             @RequestParam(value = "adjustmentAmount", required = false) BigDecimal adjustmentAmount,
             HttpServletRequest request
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
        User user=userMapper.queryUserByUsername(request.getHeader("username"));
        System.out.println(user);

        String inputFile = "收发存汇总表模板.xlsx";

        // Define the formatter with the desired pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        // Format the LocalDate using the formatter
        String beginDateDot = beginDate.format(formatter);
        String endDateDot = endDate.format(formatter);

        try (FileInputStream fileIn = new FileInputStream(inputFile);
             XSSFWorkbook workbook = new XSSFWorkbook(fileIn)) {
            // Define border style
            CellStyle borderedStyle = workbook.createCellStyle();
            borderedStyle.setBorderBottom(BorderStyle.THIN);
            borderedStyle.setBorderTop(BorderStyle.THIN);
            borderedStyle.setBorderLeft(BorderStyle.THIN);
            borderedStyle.setBorderRight(BorderStyle.THIN);
            borderedStyle.setWrapText(true);
            // Access the first sheet
            XSSFSheet  sheet = workbook.getSheetAt(0);

            for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
                CellRangeAddress region = sheet.getMergedRegion(i);
                if (region.isInRange(0, 7)) {

                    Row row = sheet.getRow(region.getFirstRow());
                    Cell cell = row.getCell(region.getFirstColumn(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellValue("管理公司助听器收发存汇总表（"+beginDateDot +" - "+ endDateDot+"）");  // Change the value

                    // Create or get the existing cell style
                    CellStyle style = cell.getCellStyle();
                    if (style == null || style == workbook.getCellStyleAt((short)0)) {
                        style = workbook.createCellStyle();
                    }

                    // Set horizontal alignment to center
                    style.setAlignment(HorizontalAlignment.CENTER);

                    // Apply the style to the cell
                    cell.setCellStyle(style);
                }
            }

            List<Inbound> inboundList = inboundMapper.queryInboundList(0, Integer.MAX_VALUE);
            List<Outbound> outboundList = outboundMapper.queryOutboundList(0, Integer.MAX_VALUE);
            List<Manufacturer> manufacturerList = manufacturerMapper.queryManufacturerList(0, Integer.MAX_VALUE);
            List<Item> itemList = itemMapper.queryItemListByCondition(null,null,null,0, Integer.MAX_VALUE);
            int rowNum = 2;
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
                        BigDecimal totalFinalItemPrice = item.getUnitPriceExcludingTax().multiply(finalItemAmount);
                        //todo 如果是尾差调整，则数量mock为0
                        if (manufacturer.getManufacturerName().contains("尾差调整")) {
                            finalItemAmount = BigDecimal.ZERO;
                        }
                        row.createCell(12).setCellValue(String.valueOf(finalItemAmount));
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

//            nextRow = sheet.createRow(rowNum++);
//            nextRow.createCell(0).setCellValue("尾差调整");
//            nextRow.createCell(5).setCellValue(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP).toString());
//            nextRow.createCell(8).setCellValue(adjustmentAmount.setScale(2, RoundingMode.HALF_UP).toString());
//            nextRow.createCell(11).setCellValue(adjustmentAmount.setScale(2, RoundingMode.HALF_UP).toString());
//            nextRow.createCell(14).setCellValue(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP).toString());

//            nextRow = sheet.createRow(rowNum++);
//            nextRow.createCell(0).setCellValue("合计");
//            nextRow.createCell(5).setCellValue(totalInitialPrice.setScale(2, RoundingMode.HALF_UP).toString());
//            nextRow.createCell(8).setCellValue(totalInboundPrice.add(adjustmentAmount).setScale(2, RoundingMode.HALF_UP).toString());
//            nextRow.createCell(11).setCellValue(totalOutboundPrice.add(adjustmentAmount).setScale(2, RoundingMode.HALF_UP).toString());

//            nextRow.createCell(8).setCellValue(totalInboundPrice.setScale(2, RoundingMode.HALF_UP).toString());
//            nextRow.createCell(11).setCellValue(totalOutboundPrice.setScale(2, RoundingMode.HALF_UP).toString());
//            nextRow.createCell(14).setCellValue(totalFinalPrice.setScale(2, RoundingMode.HALF_UP).toString());



            Row row = sheet.getRow(rowNum);

// Original style10 setup
            XSSFFont font10 = workbook.createFont();
            font10.setFontHeightInPoints((short) 10);
            font10.setFontName("华文楷体"); // Setting font to STKaiti
            CellStyle style10 = workbook.createCellStyle();
            style10.cloneStyleFrom(borderedStyle); // Cloning borderedStyle
            style10.setFont(font10);
            style10.setAlignment(HorizontalAlignment.CENTER); // Horizontal alignment
            style10.setVerticalAlignment(VerticalAlignment.CENTER); // Vertical alignment
// Original style8 setup
            XSSFFont font8 = workbook.createFont();
            font8.setFontHeightInPoints((short) 8);
            font8.setFontName("宋体"); // Setting font to SimSun
            CellStyle style8 = workbook.createCellStyle();
            style8.cloneStyleFrom(borderedStyle); // Cloning borderedStyle
            style8.setFont(font8);
            style8.setVerticalAlignment(VerticalAlignment.CENTER); // Vertical alignment


            CellStyle style8Left = workbook.createCellStyle();
            style8Left.cloneStyleFrom(style8); // Cloning borderedStyle
            style8Left.setAlignment(HorizontalAlignment.LEFT); // Horizontal alignment

            CellStyle style8Center = workbook.createCellStyle();
            style8Center.cloneStyleFrom(style8); // Cloning borderedStyle
            style8Center.setAlignment(HorizontalAlignment.CENTER); // Horizontal alignment


            CellStyle style8Right = workbook.createCellStyle();
            style8Right.cloneStyleFrom(style8); // Cloning borderedStyle
            style8Right.setAlignment(HorizontalAlignment.RIGHT); // Horizontal alignment


            // Auto size columns
            for (int rowIndex = 1; rowIndex < rowNum; rowIndex++) {
                row = sheet.getRow(rowIndex);


                if (row == null) continue;  // Skip if the row does not exist
                CellStyle selectedStyle=workbook.createCellStyle();


                for (int colIndex = 0; colIndex < 15; colIndex++) {
                    if(rowIndex==1){
                        selectedStyle=style10;
                    }
                    else{
                       if(colIndex==0||colIndex==1||colIndex==2){
                           selectedStyle=style8Left;
                       }
                       else if (colIndex==3||colIndex==6||colIndex==9||colIndex==12){
                           selectedStyle=style8Center;
                       }
                       else{
                           selectedStyle=style8Right;
                       }

                    }
                    Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellStyle(selectedStyle);

                }
            }

            // Create a new row
            nextRow = sheet.createRow(rowNum++);
// Get the row index
            int nextRowIndex = nextRow.getRowNum();

// Define the range to merge (columns N and O are indices 13 and 14)
            CellRangeAddress cellRangeAddress = new CellRangeAddress(nextRowIndex, nextRowIndex, 13, 14);

// Merge the cells in the specified range
            sheet.addMergedRegion(cellRangeAddress);

// Create a cell in the merged region and set its value to "test"
            Cell mergeCell = nextRow.createCell(13); // Column N (index 13)
            mergeCell.setCellValue("制表：  "+user.getFullName());





            // Auto-size columns after populating data and styles
//            for (int colIndex = 0; colIndex < 15; colIndex++) {
//                sheet.autoSizeColumn(colIndex);
//            }

            // Save the workbook as a new file
            // Define the file path for saving the Excel file locally
            String filePath = beginDate + "至" + endDate + "收发存汇总表.xlsx";  // Update with your desired file path





            // Generate the image with the text "G375"
            byte[] imageBytes = generateImageWithText("G375");

            // Add the image to the workbook
            int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
            addWatermarkToSheet(sheet, pictureIdx);






            // Protect the sheet with a password
            String sheetPassword = "Glyy123!"; // Replace with your desired password
            sheet.protectSheet(sheetPassword);
            sheet.lockSelectLockedCells(true);
            sheet.lockSelectUnlockedCells(false);
            sheet.lockObjects(true);
            sheet.lockScenarios(true);
            sheet.lockFormatCells(true);
            sheet.lockFormatColumns(true);
            sheet.lockFormatRows(true);
            sheet.lockInsertColumns(true);
            sheet.lockInsertRows(true);
            sheet.lockDeleteColumns(true);
            sheet.lockDeleteRows(true);
            sheet.lockSort(true);
            sheet.lockAutoFilter(true);
            sheet.lockPivotTables(true);
            String workbookPassword = "Glyy123!";
            workbook.lockStructure();
            workbook.setWorkbookPassword(workbookPassword, HashAlgorithm.sha512);








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



//            File file = new File(filePath);
//            boolean success = file.setReadOnly();
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


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    @RequestMapping(value = "/inboundSummaryStatement", method = RequestMethod.GET)
    public Result inboundSummaryStatement
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

        boolean isNotInsideAnyInboundCheckOut = checkOutInboundList.stream()
                .noneMatch(checkOut ->
                        !beginDate.isBefore(checkOut.getCheckOutBeginDate()) &&
                                !endDate.isAfter(checkOut.getCheckOutEndDate())
                );


        if (isNotInsideAnyInboundCheckOut ) {
            return new Result(400, "该时间区间内有未结算的账单！", null);
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("收发存汇总");


        String[] headers = {"入库单号","产品编码", "产品名称", "单位", "数量", "单价", "税额", "金额", "含税单价", "价税合计", "税率","备注"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        List<Inbound> inboundList = inboundMapper.queryInboundList(0, Integer.MAX_VALUE);

        int rowNum = 1;
        BigDecimal totalInboundTax = new BigDecimal(0);
        BigDecimal totalInboundPriceExcludingTax = new BigDecimal(0);
        BigDecimal totalInboundPriceIncludingTax = new BigDecimal(0);


        for(Inbound inbound : inboundList){
            if((inbound.getInboundDate().isAfter(beginDate) || inbound.getInboundDate().isEqual(beginDate))
                    && (inbound.getInboundDate().isBefore(endDate) || inbound.getInboundDate().isEqual(endDate))){
                for (InboundDetail detail : inbound.getInboundDetailList()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(detail.getInboundNo());
                    row.createCell(1).setCellValue(detail.getItem().getCode());
                    row.createCell(2).setCellValue(detail.getItem().getName());
                    row.createCell(3).setCellValue(detail.getItem().getUnitName());
                    row.createCell(4).setCellValue(String.valueOf(detail.getItemAmount()));
                    row.createCell(5).setCellValue(String.valueOf(detail.getItem().getUnitPriceExcludingTax().setScale(2, RoundingMode.HALF_UP)));
                    row.createCell(6).setCellValue(String.valueOf(detail.getInboundDetailTax().setScale(2, RoundingMode.HALF_UP)));
                    row.createCell(7).setCellValue(String.valueOf(detail.getInboundDetailPriceExcludingTax().setScale(2, RoundingMode.HALF_UP)));
                    row.createCell(8).setCellValue(String.valueOf(detail.getItem().getUnitPriceIncludingTax().setScale(2, RoundingMode.HALF_UP)));
                    row.createCell(9).setCellValue(String.valueOf(detail.getInboundDetailPriceIncludingTax().setScale(2, RoundingMode.HALF_UP)));
                    row.createCell(10).setCellValue("13%");
                    row.createCell(11).setCellValue(detail.getRemark());
                }
                totalInboundTax=totalInboundTax.add(inbound.getInboundTax());
                totalInboundPriceExcludingTax=totalInboundPriceExcludingTax.add(inbound.getInboundPriceExcludingTax());
                totalInboundPriceIncludingTax=totalInboundPriceIncludingTax.add(inbound.getInboundPriceIncludingTax());

            }
        }

        Row nextRow = sheet.createRow(rowNum++);
        nextRow.createCell(0).setCellValue(String.valueOf("合计"));
        nextRow.createCell(6).setCellValue(String.valueOf(totalInboundTax.setScale(2, RoundingMode.HALF_UP)));
        nextRow.createCell(7).setCellValue(String.valueOf(totalInboundPriceExcludingTax.setScale(2, RoundingMode.HALF_UP)));
        nextRow.createCell(9).setCellValue(String.valueOf(totalInboundPriceIncludingTax.setScale(2, RoundingMode.HALF_UP)));


//        // Auto size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Define the file path for saving the Excel file locally
        String filePath = beginDate + "至" + endDate + "入库汇总表.xlsx";  // Update with your desired file path

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

    @RequestMapping(value = "/outboundSummaryStatement", method = RequestMethod.GET)
    public Result outboundSummaryStatement
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
        List<CheckOut> checkOutOutboundList = checkOutMapper.queryCheckOutList("outbound",0, Integer.MAX_VALUE);

        boolean isNotInsideAnyOutboundCheckOut = checkOutOutboundList.stream()
                .noneMatch(checkOut ->
                        !beginDate.isBefore(checkOut.getCheckOutBeginDate()) &&
                                !endDate.isAfter(checkOut.getCheckOutEndDate())
                );


        if (isNotInsideAnyOutboundCheckOut ) {
            return new Result(400, "该时间区间内有未结算的账单！", null);
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("收发存汇总");


        String[] headers = {"出库单号","产品编码", "产品名称", "单位", "数量", "单价", "税额", "金额", "含税单价", "价税合计", "税率","备注"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        List<Outbound> outboundList = outboundMapper.queryOutboundList(0, Integer.MAX_VALUE);

        int rowNum = 1;
        BigDecimal totalOutboundTax = new BigDecimal(0);
        BigDecimal totalOutboundPriceExcludingTax = new BigDecimal(0);
        BigDecimal totalOutboundPriceIncludingTax = new BigDecimal(0);


        for(Outbound outbound : outboundList){
            if((outbound.getOutboundDate().isAfter(beginDate) || outbound.getOutboundDate().isEqual(beginDate))
                    && (outbound.getOutboundDate().isBefore(endDate) || outbound.getOutboundDate().isEqual(endDate))){
                for (OutboundDetail detail : outbound.getOutboundDetailList()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(detail.getOutboundNo());
                    row.createCell(1).setCellValue(detail.getItem().getCode());
                    row.createCell(2).setCellValue(detail.getItem().getName());
                    row.createCell(3).setCellValue(detail.getItem().getUnitName());
                    row.createCell(4).setCellValue(String.valueOf(detail.getItemAmount()));
                    row.createCell(5).setCellValue(String.valueOf(detail.getItem().getUnitPriceExcludingTax().setScale(2, RoundingMode.HALF_UP)));
                    row.createCell(6).setCellValue(String.valueOf(detail.getOutboundDetailTax().setScale(2, RoundingMode.HALF_UP)));
                    row.createCell(7).setCellValue(String.valueOf(detail.getOutboundDetailPriceExcludingTax().setScale(2, RoundingMode.HALF_UP)));
                    row.createCell(8).setCellValue(String.valueOf(detail.getItem().getUnitPriceIncludingTax().setScale(2, RoundingMode.HALF_UP)));
                    row.createCell(9).setCellValue(String.valueOf(detail.getOutboundDetailPriceIncludingTax().setScale(2, RoundingMode.HALF_UP)));
                    row.createCell(10).setCellValue("13%");
                    row.createCell(11).setCellValue(detail.getRemark());
                }
                totalOutboundTax=totalOutboundTax.add(outbound.getOutboundTax());
                totalOutboundPriceExcludingTax=totalOutboundPriceExcludingTax.add(outbound.getOutboundPriceExcludingTax());
                totalOutboundPriceIncludingTax=totalOutboundPriceIncludingTax.add(outbound.getOutboundPriceIncludingTax());

            }
        }

        Row nextRow = sheet.createRow(rowNum++);
        nextRow.createCell(0).setCellValue(String.valueOf("合计"));
        nextRow.createCell(6).setCellValue(String.valueOf(totalOutboundTax.setScale(2, RoundingMode.HALF_UP)));
        nextRow.createCell(7).setCellValue(String.valueOf(totalOutboundPriceExcludingTax.setScale(2, RoundingMode.HALF_UP)));
        nextRow.createCell(9).setCellValue(String.valueOf(totalOutboundPriceIncludingTax.setScale(2, RoundingMode.HALF_UP)));


//        // Auto size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Define the file path for saving the Excel file locally
        String filePath = beginDate + "至" + endDate + "出库汇总表.xlsx";  // Update with your desired file path

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
