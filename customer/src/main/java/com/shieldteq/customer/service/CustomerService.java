package com.shieldteq.customer.service;

import com.shieldteq.customer.dto.CustomerInfo;
import com.shieldteq.customer.entity.Customer;
import com.shieldteq.customer.exception.ApplicationExceptions;
import com.shieldteq.customer.mapper.EntityDtoMapper;
import com.shieldteq.customer.repository.CustomerRepository;
import com.shieldteq.customer.repository.PortfolioItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public Mono<CustomerInfo> getCustomerInfo(Integer customerId) {
        return customerRepository.findById(customerId)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(customerId))
                .flatMap(this::buildCustomerInfo);

    }

    private Mono<CustomerInfo> buildCustomerInfo(Customer customer) {
        return portfolioItemRepository.findAllByCustomerId(customer.id())
                .collectList()
                .map(list -> EntityDtoMapper.toCustomerInfo(customer, list));
    }

    public Flux<CustomerInfo> getCustomerInfoAll() {
        return customerRepository.findAll().map(c -> EntityDtoMapper.toCustomerInfo(c, List.of()));
    }
}
