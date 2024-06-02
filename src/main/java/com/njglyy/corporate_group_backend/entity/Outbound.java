package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Outbound {
    private OutboundInfo outboundInfo;
    private InboundItem inboundItem;
    private Item item;

}
