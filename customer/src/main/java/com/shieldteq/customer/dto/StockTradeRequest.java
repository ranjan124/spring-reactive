package com.shieldteq.customer.dto;

import com.shieldteq.customer.domain.Ticker;
import com.shieldteq.customer.domain.TradeAction;
import lombok.Builder;

@Builder
public record StockTradeRequest(Ticker ticker,
                                Integer price,
                                Integer quantity,
                                TradeAction action) {
    public Integer totalPrice() {
        return quantity * price;
    }
}
