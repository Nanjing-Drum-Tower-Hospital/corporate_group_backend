package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.Inbound;
import com.njglyy.corporate_group_backend.entity.InboundDetail;
import com.njglyy.corporate_group_backend.entity.Outbound;
import com.njglyy.corporate_group_backend.entity.Result;
import com.njglyy.corporate_group_backend.mapper.InboundMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        for (InboundDetail detail : inbound.getInboundDetailList()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(detail.getItem().getCode());
            row.createCell(1).setCellValue(detail.getItem().getName());
            row.createCell(2).setCellValue(detail.getItem().getUnitName());
            row.createCell(3).setCellValue(detail.getItemAmount().doubleValue());
            row.createCell(4).setCellValue(String.valueOf(detail.getItem().getUnitPriceExcludingTax().setScale(2, RoundingMode.HALF_UP)));
            row.createCell(5).setCellValue(String.valueOf(detail.getInboundDetailTax().setScale(2, RoundingMode.HALF_UP)));
            row.createCell(6).setCellValue(String.valueOf(detail.getInboundDetailPriceExcludingTax().setScale(2, RoundingMode.HALF_UP)));
            row.createCell(7).setCellValue(String.valueOf(detail.getItem().getUnitPriceIncludingTax().setScale(2, RoundingMode.HALF_UP)));
            row.createCell(8).setCellValue(String.valueOf(detail.getInboundDetailPriceIncludingTax().setScale(2, RoundingMode.HALF_UP)));
            row.createCell(9).setCellValue("13%");
        }

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



        return new Result(200, "导出成功！", null);

    }

}
