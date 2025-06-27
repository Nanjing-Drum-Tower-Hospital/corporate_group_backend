package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitRatio {
    private int id;
    private String unitNameLeft;
    private String unitNameRight;
    private int ratio;
}
