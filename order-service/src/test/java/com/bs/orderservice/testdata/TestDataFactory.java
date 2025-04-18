package com.bs.orderservice.testdata;

import static org.instancio.Select.field;

import com.bs.orderservice.domain.records.Address;
import com.bs.orderservice.domain.records.Customer;
import com.bs.orderservice.domain.records.OrderItem;
import com.bs.orderservice.domain.records.OrderRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.instancio.Instancio;

public class TestDataFactory {
    static final List<String> VALID_COUNTRIES = List.of("Nigeria", "UK");
    static final Set<OrderItem> VALID_ORDER_ITEMS =
            Set.of(new OrderItem("P100", "Product 1", new BigDecimal("25.50"), 1));

    public static OrderRequest createValidCreateOrderRequest() {
        return Instancio.of(OrderRequest.class)
                .generate(
                        field(Customer::email), generators -> generators.text().pattern("#a#a#a#a#a#a@mail.com"))
                .set(field(OrderRequest::items), VALID_ORDER_ITEMS)
                .generate(field(Address::country), generators -> generators.oneOf(VALID_COUNTRIES))
                .create();
    }

    public static OrderRequest createOrderRequestWithInvalidCustomer() {
        return Instancio.of(OrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c#c#d#d@mail.com"))
                .set(field(Customer::phone), "")
                .generate(field(Address::country), gen -> gen.oneOf(VALID_COUNTRIES))
                .set(field(OrderRequest::items), VALID_ORDER_ITEMS)
                .create();
    }

    public static OrderRequest createOrderRequestWithInvalidDeliveryAddress() {
        return Instancio.of(OrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c#c#d#d@mail.com"))
                .set(field(Address::country), "")
                .set(field(OrderRequest::items), VALID_ORDER_ITEMS)
                .create();
    }

    public static OrderRequest createOrderRequestWithNoItems() {
        return Instancio.of(OrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c#c#d#d@mail.com"))
                .generate(field(Address::country), gen -> gen.oneOf(VALID_COUNTRIES))
                .set(field(OrderRequest::items), Set.of())
                .create();
    }
}
