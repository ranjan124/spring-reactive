package com.shieldteq.customer.entity;

import com.shieldteq.customer.domain.Ticker;
import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder
public record PortfolioItem(
        @Id Integer id,
        Integer customerId,
        Ticker ticker,
        Integer quantity
) {
    public PortfolioItem increaseQuantity(Integer quantity) {
        return PortfolioItem.builder().id(id).customerId(customerId).ticker(ticker).quantity(this.quantity + quantity).build();
    }

    public PortfolioItem reduceQuantity(Integer quantity) {
        return PortfolioItem.builder().id(id).customerId(customerId).ticker(ticker).quantity(this.quantity - quantity).build();
    }
}
