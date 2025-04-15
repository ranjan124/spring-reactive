package com.shieldteq.customer.dto;

import com.shieldteq.customer.domain.Ticker;
import lombok.Builder;

@Builder
public record Holding(Ticker ticker, int quantity) {
}
