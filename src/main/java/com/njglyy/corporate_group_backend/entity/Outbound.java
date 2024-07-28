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
public class Outbound implements Cloneable{
    private String outboundNo;
    private LocalDate outboundDate;
    private String remark;
    private String accountingReversalOutboundNo;
    private String entryType;
    private List<OutboundDetail> outboundDetailList;
    private CheckOut checkOut;
    @Override
    public Outbound clone() {
        try {
            return (Outbound) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }
    }

    public BigDecimal getOutboundPriceExcludingTax() {
        return outboundDetailList.stream()
                .map(OutboundDetail::getOutboundDetailPriceExcludingTax)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getOutboundPriceIncludingTax() {
        return outboundDetailList.stream()
                .map(OutboundDetail::getOutboundDetailPriceIncludingTax)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getOutboundTax() {
        return outboundDetailList.stream()
                .map(OutboundDetail::getOutboundDetailTax)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
