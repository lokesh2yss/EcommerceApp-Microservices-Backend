package com.codingshuttle.ecommerce.inventory_service.events;

import com.codingshuttle.ecommerce.inventory_service.dtos.OrderRequestItemDTO;
import com.codingshuttle.ecommerce.order_service.entities.enums.OrderStatus;
import com.codingshuttle.ecommerce.shipping_service.dtos.ShipmentRequestDTO;
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
