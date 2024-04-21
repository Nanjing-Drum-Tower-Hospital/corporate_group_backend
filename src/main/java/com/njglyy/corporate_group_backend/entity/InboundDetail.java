package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboundDetail {
    private Inbound inbound;
    private Inbound inboundItem;
    private Supplier supplier;
    private ItemDetail itemDetail;
}
