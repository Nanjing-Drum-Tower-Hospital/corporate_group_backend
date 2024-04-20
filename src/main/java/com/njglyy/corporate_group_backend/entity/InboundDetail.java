package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboundDetail {
    private int id;
    private int orderNo;
    private String remark;
    private int itemId;
    private String machine_no;
    private String code;
    private String name;
    private String model;
    private String unitName;
    private double sellingPrice;
    private String manufacturer;
    private String supplier;
    private String billItem;
    private String standards;
    private String approvalNo;
    private String type;
    private LocalDate expireDate;
    private LocalDate createDate;
    private String certificationUrl;
    private String pinyinCode;
}
