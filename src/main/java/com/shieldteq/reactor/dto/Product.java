package com.shieldteq.reactor.dto;

import lombok.Builder;

@Builder
public record Product(
        Integer id,
        String description,
        Integer price
) {
}
