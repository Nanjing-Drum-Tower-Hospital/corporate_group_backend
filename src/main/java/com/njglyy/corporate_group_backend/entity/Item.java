package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private int id;
    private String code;
    private String name;
    private String model;
    private String unitName;
    private double sellingPrice;
    private int manufacturerId;
    private String billItem;
    private String standards;
    private String approvalNo;
    private String type;
    private LocalDate expireDate;
    private LocalDate createDate;
    private String certificationUrl;
    private String pinyinCode;
}
