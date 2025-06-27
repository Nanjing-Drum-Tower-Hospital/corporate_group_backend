package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private int id;
    private String code;
    private String name;
    private String model;
    private String unitName;

    private BigDecimal unitPriceExcludingTax;
    private int manufacturerId;
    private String billItem;
    private String standards;
    private String approvalNo;
    private String type;
    private LocalDate expireDate;
    private LocalDate createDate;
    private String certificationUrl;
    private String pinyinCode;
    private Manufacturer manufacturer;

    public BigDecimal getUnitPriceIncludingTax() {
        BigDecimal sellingPriceBD = unitPriceExcludingTax;
        return sellingPriceBD.multiply(BigDecimal.valueOf(1.13)).setScale(10, BigDecimal.ROUND_HALF_UP);
    }


    public BigDecimal getUnitTax() {
        BigDecimal sellingPriceBD = unitPriceExcludingTax;
        return sellingPriceBD.multiply(BigDecimal.valueOf(0.13)).setScale(10, BigDecimal.ROUND_HALF_UP);
    }

}
