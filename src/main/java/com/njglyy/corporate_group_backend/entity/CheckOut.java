package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckOut {
    private int id;
    private String type;
    private LocalDate checkOutBeginDate;
    private LocalDate checkOutEndDate;
    private LocalDate createDate;
    private String remark;
    private String validity;
}
