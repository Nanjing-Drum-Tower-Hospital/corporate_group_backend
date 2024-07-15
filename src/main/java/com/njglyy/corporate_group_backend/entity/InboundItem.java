package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboundItem {
    private int id;
    private String inboundNo;
    private int itemId;
    private int itemAmount;
    private String remark;



}
