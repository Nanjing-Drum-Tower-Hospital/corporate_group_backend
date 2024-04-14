package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String code;
    private String name;
    private String model;
    private String unitName;
    private double sellingPrice;
    private String manufacturer;
    private String company;
    private String billItem;
    private String standards;
    private String approvalNo;
    private String type;
    private LocalDateTime expireDate;
    private LocalDateTime createDate;
    private String certificationUrl;
    private String pinyinCode;
}
