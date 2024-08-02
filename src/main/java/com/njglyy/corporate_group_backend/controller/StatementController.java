package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.*;
import com.njglyy.corporate_group_backend.mapper.InboundMapper;
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
    @RequestMapping(value = "/exportInboundStatement", method = RequestMethod.POST)
    public Result exportInboundStatement
            (@RequestBody Inbound inbound
            ) {
        System.out.println(inbound);
        Inbound inboundDB = inboundMapper.queryInboundByInboundNo(inbound.getInboundNo());

        // Create a workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Inbound Statement");

        // Create headers
        String[] headers = { "产品编码","产品名称","单位","数量","单价","税额", "金额","含税单价", "价税合计","税率"};
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
        String filePath = "inbound_statement.xlsx";  // Update with your desired file path

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
