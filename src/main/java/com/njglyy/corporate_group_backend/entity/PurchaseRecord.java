package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRecord {
    private int id;
    private LocalDate purchaseDate;
    private int customerId;
    private String itemBrand;
    private String itemModel;
    private BigDecimal itemAmount;
    private String machineNo;
    private LocalDate machineManufactureDate;
    private BigDecimal chargerAmount;
    private BigDecimal receiverAmount;
    private String payerName;
    private String payerPhoneNumber;
    private String paymentMethod;
    private LocalDate paymentDate;
    private BigDecimal paymentAmount;
    private String recommendationStaffId;
    private String recommendationStaffName;
    private String remark;
    private Customer customer;

}
