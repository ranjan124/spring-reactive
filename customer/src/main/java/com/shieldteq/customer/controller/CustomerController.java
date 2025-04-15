package com.shieldteq.customer.controller;

import com.shieldteq.customer.dto.CustomerInfo;
import com.shieldteq.customer.dto.StockTradeRequest;
import com.shieldteq.customer.dto.StockTradeResponse;
import com.shieldteq.customer.service.CustomerService;
import com.shieldteq.customer.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final TradeService tradeService;

    @GetMapping
    public Flux<CustomerInfo> getCustomers() {
        return customerService.getCustomerInfoAll();
    }

    @GetMapping("/{customerId}")
    public Mono<CustomerInfo> getCustomerInfo(@PathVariable Integer customerId) {
        return customerService.getCustomerInfo(customerId);
    }

    @PostMapping("/{customerId}/trade")
    public Mono<StockTradeResponse> trade(@PathVariable Integer customerId, @RequestBody Mono<StockTradeRequest> mono) {
        return mono.flatMap(request -> tradeService.trade(customerId, request));
    }
}
