package com.shieldteq.customer.advice;

import com.shieldteq.customer.exception.CustomerNotFoundException;
import com.shieldteq.customer.exception.InsufficientBalanceException;
import com.shieldteq.customer.exception.InsufficientShareException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.function.Consumer;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ProblemDetail handleException(CustomerNotFoundException ex) {
        return buildDetail(HttpStatus.NOT_FOUND, ex, c -> {
            c.setTitle("Customer not found");
            c.setType(URI.create("http://docs.customer.com/customer-error"));
        });
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ProblemDetail handleException(InsufficientBalanceException ex) {
        return buildDetail(HttpStatus.BAD_REQUEST, ex, c -> {
            c.setTitle("Insufficient balance");
            c.setType(URI.create("http://docs.customer.com/customer-error"));
        });
    }

    @ExceptionHandler(InsufficientShareException.class)
    public ProblemDetail handleException(InsufficientShareException ex) {
        return buildDetail(HttpStatus.BAD_REQUEST, ex, c -> {
            c.setTitle("Insufficient share");
            c.setType(URI.create("http://docs.customer.com/customer-error"));
        });
    }

    private ProblemDetail buildDetail(HttpStatus status, Exception ex, Consumer<ProblemDetail> consumer) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        consumer.accept(detail);
        return detail;
    }
}
