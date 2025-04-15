package com.shieldteq.customer.exception;

public class InsufficientBalanceException extends RuntimeException {
    public static final String MESSAGE = "Customer {id: %d} does not have enough balance";

    public InsufficientBalanceException(Integer customerId) {
        super(MESSAGE.formatted(customerId));
    }
}
