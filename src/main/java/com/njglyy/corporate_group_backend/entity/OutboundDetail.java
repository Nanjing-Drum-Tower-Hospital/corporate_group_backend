package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutboundDetail {
    private int id;
    private String outboundNo;
    private int itemId;
    private int itemAmount;
    private String remark;
    private Item item;
    public BigDecimal getOutboundDetailPriceExcludingTax() {
        if (item!=null  && item.getUnitPriceExcludingTax() != null) {
            return item.getUnitPriceExcludingTax()
                    .multiply(BigDecimal.valueOf(itemAmount))
                    .setScale(10, BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }
    public BigDecimal getOutboundDetailPriceIncludingTax() {
        if (item!=null  && item.getUnitPriceExcludingTax() != null) {
            return item.getUnitPriceExcludingTax()
                    .multiply(BigDecimal.valueOf(itemAmount))
                    .multiply(BigDecimal.valueOf(1.13))
                    .setScale(10, BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }
    public BigDecimal getOutboundDetailTax() {
        if (item!=null &&  item.getUnitPriceExcludingTax() != null) {
            return item.getUnitPriceExcludingTax()
                    .multiply(BigDecimal.valueOf(itemAmount))
                    .multiply(BigDecimal.valueOf(0.13))
                    .setScale(10, BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }

}
