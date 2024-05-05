package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inbound {
    private InboundInfo inboundInfo;
    private InboundItem inboundItem;
    private Supplier supplier;
    private Item item;
}
