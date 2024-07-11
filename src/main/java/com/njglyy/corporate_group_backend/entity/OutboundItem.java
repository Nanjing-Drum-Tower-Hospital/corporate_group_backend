package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutboundItem {
    private int id;
    private String outboundNo;
    private int itemId;
    private int itemAmount;
    private String remark;
}
