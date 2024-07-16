package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Supplier supplier;
    private List<InboundDetail> inboundDetailList;

    @Override
    public Inbound clone() {
        try {
            return (Inbound) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }
    }

//    private InboundInfo inboundInfo;
//    private InboundItem inboundItem;
//    private Supplier supplier;
//    private Item item;
//    private BigDecimal totalUnitPriceExcludingTax; // Holds the sum for all similar objects
//    private BigDecimal totalUnitPriceIncludingTax; // Holds the sum for all similar objects
//    private BigDecimal totalTax; // Holds the sum of taxes for all similar objects
//
//    public BigDecimal getUnitPriceExcludingTax() {
//        if (item!=null && item.getItemDetail() != null && item.getItemDetail().getUnitPriceExcludingTax() != null) {
//            return item.getItemDetail().getUnitPriceExcludingTax()
//                    .multiply(BigDecimal.valueOf(inboundItem.getItemAmount()))
//                    .setScale(10, BigDecimal.ROUND_HALF_UP);
//        }
//        return BigDecimal.ZERO;
//    }
//
//
//    // Calculate the total unit price including tax based on itemAmount
//    public BigDecimal getUnitPriceIncludingTax() {
//        if (item!=null && item.getItemDetail() != null && item.getItemDetail().getUnitPriceExcludingTax() != null) {
//            return item.getItemDetail().getUnitPriceExcludingTax()
//                    .multiply(BigDecimal.valueOf(inboundItem.getItemAmount()))
//                    .multiply(BigDecimal.valueOf(1.13))
//                    .setScale(10, BigDecimal.ROUND_HALF_UP);
//        }
//        return BigDecimal.ZERO;
//    }
//
//    // Calculate the total tax for the item based on itemAmount
//    public BigDecimal getTax() {
//        if (item!=null && item.getItemDetail()  != null && item.getItemDetail() .getUnitPriceExcludingTax() != null) {
//            return item.getItemDetail() .getUnitPriceExcludingTax()
//                    .multiply(BigDecimal.valueOf(inboundItem.getItemAmount()))
//                    .multiply(BigDecimal.valueOf(0.13))
//                    .setScale(10, BigDecimal.ROUND_HALF_UP);
//        }
//        return BigDecimal.ZERO;
//    }
//
//    // Calculate and update the total values for the list of Inbound objects
//    public static void updateInboundTotals(List<Inbound> inbounds) {
//        BigDecimal totalUnitPriceExcludingTax = inbounds.stream()
//                .map(Inbound::getUnitPriceExcludingTax)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        BigDecimal totalUnitPriceIncludingTax = inbounds.stream()
//                .map(Inbound::getUnitPriceIncludingTax)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        BigDecimal totalTax = inbounds.stream()
//                .map(Inbound::getTax)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        for (Inbound inbound : inbounds) {
//            inbound.setTotalUnitPriceIncludingTax(totalUnitPriceIncludingTax);
//            inbound.setTotalTax(totalTax);
//        }
//    }
}
