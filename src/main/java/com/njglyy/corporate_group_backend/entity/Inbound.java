package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inbound {
    private int id;
    private String orderNo;
    private String supplierId;
    private String remark;
    private String supplierName;
    private String pinyinCode;
}
