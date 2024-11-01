package com.codingshuttle.ecommerce.order_service.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    PENDING, CANCELLED, FULFILLED, CONFIRMED, OUT_OF_STOCK, SHIPPED, DELIVERED;

    @JsonCreator
    public static OrderStatus fromValue(String value) {
        return OrderStatus.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
