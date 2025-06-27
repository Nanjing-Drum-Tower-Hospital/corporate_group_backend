package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inbound implements Cloneable{
    private String inboundNo;
    private LocalDate inboundDate;
    private int supplierId;
    private String remark;
    private String accountingReversalInboundNo;
    private String entryType;
    private String fapiaoNo;
    private Supplier supplier;
    private List<InboundDetail> inboundDetailList;
    private CheckOut checkOut;

    @Override
    public Inbound clone() {
        try {
            return (Inbound) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }
    }

    // Calculate the sum of unit prices excluding tax for all details
    public BigDecimal getInboundPriceExcludingTax() {
        return inboundDetailList.stream()
                .map(InboundDetail::getInboundDetailPriceExcludingTax)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Calculate the sum of unit prices including tax for all details
    public BigDecimal getInboundPriceIncludingTax() {
        return inboundDetailList.stream()
                .map(InboundDetail::getInboundDetailPriceIncludingTax)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Calculate the total tax for all details
    public BigDecimal getInboundTax() {
        return inboundDetailList.stream()
                .map(InboundDetail::getInboundDetailTax)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
