package com.shieldteq.customer.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CustomerInfo(Integer id,
                           String name,
                           Integer balance,
                           List<Holding> holdings) {
}
