package com.shieldteq.customer.mapper;

import com.shieldteq.customer.domain.Ticker;
import com.shieldteq.customer.dto.CustomerInfo;
import com.shieldteq.customer.dto.Holding;
import com.shieldteq.customer.dto.StockTradeRequest;
import com.shieldteq.customer.dto.StockTradeResponse;
import com.shieldteq.customer.entity.Customer;
import com.shieldteq.customer.entity.PortfolioItem;

import java.util.List;

public final class EntityDtoMapper {
    private EntityDtoMapper() {
    }

    public static CustomerInfo toCustomerInfo(Customer customer, List<PortfolioItem> portfolioItems) {
        List<Holding> holdings = portfolioItems.stream().map(p -> Holding.builder().ticker(p.ticker()).quantity(p.quantity()).build()).toList();
        return CustomerInfo.builder()
                .id(customer.id())
                .name(customer.name())
                .balance(customer.balance())
                .holdings(holdings)
                .build();
    }

    public static PortfolioItem toPortfolioItem(Integer customerId, Ticker ticker) {
        return PortfolioItem.builder().customerId(customerId).ticker(ticker).quantity(0).build();
    }

    public static StockTradeResponse stockTradeResponse(StockTradeRequest request, Integer customerId, Integer balance) {
        return StockTradeResponse.builder()
                .customerId(customerId)
                .ticker(request.ticker())
                .price(request.price())
                .quantity(request.quantity())
                .action(request.action())
                .totalPrice(request.totalPrice())
                .balance(balance)
                .build();
    }
}
