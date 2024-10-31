package com.codingshuttle.ecommerce.order_service.events;

import com.codingshuttle.ecommerce.order_service.dtos.OrderRequestItemDTO;
import com.codingshuttle.ecommerce.order_service.dtos.ShipmentRequestDTO;
import com.codingshuttle.ecommerce.order_service.dtos.enums.OrderStatus;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@ToString
public class OrderFulfilledEvent {
    private Long orderId;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private List<OrderRequestItemDTO> items;
    private ShipmentRequestDTO shipmentDetails;
}
