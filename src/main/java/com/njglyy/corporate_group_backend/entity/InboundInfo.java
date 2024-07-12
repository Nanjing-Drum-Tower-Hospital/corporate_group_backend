package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboundInfo implements Cloneable{
    private String inboundNo;
    private LocalDate inboundDate;
    private int supplierId;
    private String remark;
    private String accountingReversalInboundNo;

    @Override
    public InboundInfo clone() {
        try {
            return (InboundInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }
    }
}
