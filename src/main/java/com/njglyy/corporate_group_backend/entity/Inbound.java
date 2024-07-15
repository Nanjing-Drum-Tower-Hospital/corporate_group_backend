package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inbound {
    private InboundInfo inboundInfo;
    private InboundItem inboundItem;
    private Supplier supplier;
    private Item item;

    public BigDecimal getUnitPriceExcludingTax() {
        if (item!=null && item.getItemDetail() != null && item.getItemDetail().getUnitPriceExcludingTax() != null) {
            return item.getItemDetail().getUnitPriceExcludingTax()
                    .multiply(BigDecimal.valueOf(inboundItem.getItemAmount()))
                    .setScale(10, BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }


    // Calculate the total unit price including tax based on itemAmount
    public BigDecimal getUnitPriceIncludingTax() {
        if (item!=null && item.getItemDetail() != null && item.getItemDetail().getUnitPriceExcludingTax() != null) {
            return item.getItemDetail().getUnitPriceExcludingTax()
                    .multiply(BigDecimal.valueOf(inboundItem.getItemAmount()))
                    .multiply(BigDecimal.valueOf(1.13))
                    .setScale(10, BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    // Calculate the total tax for the item based on itemAmount
    public BigDecimal getTax() {
        if (item!=null && item.getItemDetail()  != null && item.getItemDetail() .getUnitPriceExcludingTax() != null) {
            return item.getItemDetail() .getUnitPriceExcludingTax()
                    .multiply(BigDecimal.valueOf(inboundItem.getItemAmount()))
                    .multiply(BigDecimal.valueOf(0.13))
                    .setScale(10, BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}
