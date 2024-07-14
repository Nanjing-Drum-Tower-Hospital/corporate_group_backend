package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutboundInfo implements Cloneable{
    private String outboundNo;
    private LocalDate outboundDate;
//    private int supplierId;
    private String remark;
    private String accountingReversalOutboundNo;
    private String entryType;
    @Override
    public OutboundInfo clone() {
        try {
            return (OutboundInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }
    }
}
