package com.shieldteq.customer.service;

import com.shieldteq.customer.dto.StockTradeRequest;
import com.shieldteq.customer.dto.StockTradeResponse;
import com.shieldteq.customer.entity.Customer;
import com.shieldteq.customer.entity.PortfolioItem;
import com.shieldteq.customer.exception.ApplicationExceptions;
import com.shieldteq.customer.mapper.EntityDtoMapper;
import com.shieldteq.customer.repository.CustomerRepository;
import com.shieldteq.customer.repository.PortfolioItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeService {
    private final CustomerRepository customerRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    @Transactional
    public Mono<StockTradeResponse> trade(Integer customerId, StockTradeRequest request) {
        return switch (request.action()) {
            case BUY -> buyStock(customerId, request);
            case SELL -> sellStock(customerId, request);
        };
    }

    private Mono<StockTradeResponse> buyStock(Integer customerId, StockTradeRequest request) {
        Mono<Customer> customer = customerRepository.findById(customerId)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(customerId))
                .filter(c -> c.balance() >= request.totalPrice())
                .switchIfEmpty(ApplicationExceptions.insufficientBalance(customerId));

        Mono<PortfolioItem> portfolioItem = portfolioItemRepository.findByCustomerIdAndTicker(customerId, request.ticker())
                .defaultIfEmpty(EntityDtoMapper.toPortfolioItem(customerId, request.ticker()));

        return customer.zipWhen(c -> portfolioItem)
                .flatMap(t -> this.executeBuy(t.getT1(), t.getT2(), request));
    }

    private Mono<StockTradeResponse> executeBuy(Customer customer, PortfolioItem item, StockTradeRequest request) {
        Customer updatedCustomer = customer.deductBalance(request.totalPrice());
        PortfolioItem updatedPortfolio = item.increaseQuantity(request.quantity());
        return saveAndGetStockTradeResponseMono(request, updatedCustomer, updatedPortfolio);

    }

    private Mono<StockTradeResponse> sellStock(Integer customerId, StockTradeRequest request) {
        Mono<Customer> customer = customerRepository.findById(customerId)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(customerId));

        Mono<PortfolioItem> portfolioItem = portfolioItemRepository.findByCustomerIdAndTicker(customerId, request.ticker())
                .filter(p -> p.quantity() >= request.quantity())
                .switchIfEmpty(ApplicationExceptions.insufficientShare(customerId));

        return customer.zipWhen(c -> portfolioItem)
                .flatMap(t -> this.executeSell(t.getT1(), t.getT2(), request));
    }

    private Mono<? extends StockTradeResponse> executeSell(Customer customer, PortfolioItem item, StockTradeRequest request) {
        Customer updatedCustomer = customer.addBalance(request.totalPrice());
        PortfolioItem updatedPortfolio = item.reduceQuantity(request.quantity());
        return saveAndGetStockTradeResponseMono(request, updatedCustomer, updatedPortfolio);
    }

    private Mono<StockTradeResponse> saveAndGetStockTradeResponseMono(StockTradeRequest request, Customer updatedCustomer, PortfolioItem updatedPortfolio) {
        StockTradeResponse stockTradeResponse = EntityDtoMapper.stockTradeResponse(request, updatedCustomer.id(), updatedCustomer.balance());
        return Mono.zip(customerRepository.save(updatedCustomer), portfolioItemRepository.save(updatedPortfolio))
                .thenReturn(stockTradeResponse);
    }
}
