package com.shieldteq.reactor.dto;

import lombok.Builder;

@Builder
public record Product(
        String id,
        String name
) {
}
