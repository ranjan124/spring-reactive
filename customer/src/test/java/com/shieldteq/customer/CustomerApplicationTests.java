package com.shieldteq.customer;

import com.shieldteq.customer.domain.Ticker;
import com.shieldteq.customer.domain.TradeAction;
import com.shieldteq.customer.dto.StockTradeRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

@SpringBootTest
@AutoConfigureWebTestClient
@Import(TestcontainersConfiguration.class)
class CustomerApplicationTests {
    public static final Logger log = LoggerFactory.getLogger(CustomerApplicationTests.class);
    @Autowired
    private WebTestClient client;

    @Test
    void customerInformationTest() {
        customerRequest(1, HttpStatus.OK)
                .jsonPath("$.name").isEqualTo("Sam")
                .jsonPath("$.balance").isEqualTo(10000)
                .jsonPath("$.holdings").isEmpty();
    }

    @Test
    void customerNotFoundInformationTest() {
        customerRequest(15, HttpStatus.NOT_FOUND)
                .jsonPath("$.detail").isEqualTo("Customer {id: 15} not found");
    }

    @Test
    void stockTradeTest() {
        StockTradeRequest request = StockTradeRequest.builder().ticker(Ticker.GOOGLE).price(100).quantity(5).action(TradeAction.BUY).build();
        tradeRequest(2, request, HttpStatus.OK)
                .jsonPath("$.balance").isEqualTo(9500)
                .jsonPath("$.totalPrice").isEqualTo(500);

        StockTradeRequest request2 = StockTradeRequest.builder().ticker(Ticker.GOOGLE).price(100).quantity(10).action(TradeAction.BUY).build();
        tradeRequest(2, request2, HttpStatus.OK)
                .jsonPath("$.balance").isEqualTo(8500)
                .jsonPath("$.totalPrice").isEqualTo(1000);

        customerRequest(2, HttpStatus.OK)
                .jsonPath("$.balance").isEqualTo(8500)
                .jsonPath("$.name").isEqualTo("Mike")
                .jsonPath("$.holdings").isNotEmpty()
                .jsonPath("$.holdings[0].ticker").isEqualTo("GOOGLE")
                .jsonPath("$.holdings[0].quantity").isEqualTo(15);


        StockTradeRequest sellRequest = StockTradeRequest.builder().ticker(Ticker.GOOGLE).price(300).quantity(5).action(TradeAction.SELL).build();
        tradeRequest(2, sellRequest, HttpStatus.OK)
                .jsonPath("$.balance").isEqualTo(10000)
                .jsonPath("$.totalPrice").isEqualTo(1500);

        StockTradeRequest sellRequest2 = StockTradeRequest.builder().ticker(Ticker.GOOGLE).price(200).quantity(10).action(TradeAction.SELL).build();
        tradeRequest(2, sellRequest2, HttpStatus.OK)
                .jsonPath("$.balance").isEqualTo(12000)
                .jsonPath("$.totalPrice").isEqualTo(2000);


        customerRequest(2, HttpStatus.OK)
                .jsonPath("$.balance").isEqualTo(12000)
                .jsonPath("$.name").isEqualTo("Mike")
                .jsonPath("$.holdings[0].ticker").isEqualTo("GOOGLE")
                .jsonPath("$.holdings[0].quantity").isEqualTo(0);
    }
    @Test
    void stockTradeCustomerNotFoundTest() {
        StockTradeRequest request = StockTradeRequest.builder().ticker(Ticker.GOOGLE).price(100).quantity(5).action(TradeAction.BUY).build();
        tradeRequest(15, request, HttpStatus.NOT_FOUND)
                .jsonPath("$.detail").isEqualTo("Customer {id: 15} not found");

    }

    private WebTestClient.BodyContentSpec customerRequest(Integer customerId, HttpStatus status) {
        return client.get()
                .uri("/customer/{customerId}", customerId)
                .exchange()
                .expectStatus().isEqualTo(status)
                .expectBody()
                .consumeWith(e -> log.info("response {}", new String(Objects.requireNonNull(e.getResponseBody()))));
    }

    private WebTestClient.BodyContentSpec tradeRequest(Integer customerId, StockTradeRequest request, HttpStatus status) {
        return client.post()
                .uri("/customer/{customerId}/trade", customerId)
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(status)
                .expectBody()
                .consumeWith(e -> log.info("response {}", new String(Objects.requireNonNull(e.getResponseBody()))));
    }

}
