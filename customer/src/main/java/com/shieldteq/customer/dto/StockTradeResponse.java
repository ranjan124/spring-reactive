package com.shieldteq.customer.dto;

import com.shieldteq.customer.domain.Ticker;
import com.shieldteq.customer.domain.TradeAction;
import lombok.Builder;

@Builder
public record StockTradeResponse(Integer customerId,
                                 Ticker ticker,
                                 Integer price,
                                 Integer quantity,
                                 TradeAction action,
                                 Integer totalPrice,
                                 Integer balance) {
}
