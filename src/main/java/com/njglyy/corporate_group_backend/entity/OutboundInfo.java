package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutboundInfo {
    private int id;
    private String orderNo;
    private LocalDate outboundDate;
    private String remark;

}
