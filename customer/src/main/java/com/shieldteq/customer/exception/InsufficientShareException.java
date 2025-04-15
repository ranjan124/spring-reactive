package com.shieldteq.customer.exception;

public class InsufficientShareException extends RuntimeException {
    public static final String MESSAGE = "Customer {id: %d} does not have enough share";

    public InsufficientShareException(Integer customerId) {
        super(MESSAGE.formatted(customerId));
    }
}
