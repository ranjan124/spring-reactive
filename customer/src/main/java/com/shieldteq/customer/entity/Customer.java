package com.shieldteq.customer.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder
public record Customer(
        @Id Integer id,
        String name,
        Integer balance
) {
    public Customer deductBalance(Integer price) {
        return Customer.builder().id(id).name(name).balance(balance - price).build();
    }

    public Customer addBalance(Integer price) {
        return Customer.builder().id(id).name(name).balance(balance + price).build();
    }
}
