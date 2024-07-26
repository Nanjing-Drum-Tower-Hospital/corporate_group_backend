package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboundDetail {
    private int id;
    private String inboundNo;
    private int itemId;
    private BigDecimal itemAmount;
    private String remark;
    private Item item;
    public BigDecimal getInboundDetailPriceExcludingTax() {
        if (item!=null  && item.getUnitPriceExcludingTax() != null) {
            return item.getUnitPriceExcludingTax()
                    .multiply(itemAmount)
                    .setScale(20, BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getInboundDetailPriceIncludingTax() {
        if (item!=null  && item.getUnitPriceExcludingTax() != null) {
            return item.getUnitPriceExcludingTax()
                    .multiply(itemAmount)
                    .multiply(BigDecimal.valueOf(1.13))
                    .setScale(20, BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getInboundDetailTax() {
        if (item!=null &&  item.getUnitPriceExcludingTax() != null) {
            return item.getUnitPriceExcludingTax()
                    .multiply(itemAmount)
                    .multiply(BigDecimal.valueOf(0.13))
                    .setScale(20, BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }



}
