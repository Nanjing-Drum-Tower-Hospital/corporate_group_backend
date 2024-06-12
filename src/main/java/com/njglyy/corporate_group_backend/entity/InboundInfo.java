package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboundInfo {
    private String inboundNo;
    private LocalDate inboundDate;
    private int supplierId;
    private String remark;
    private int accountingReversal;
}
