package com.codingshuttle.ecommerce.order_service.events;

import com.codingshuttle.ecommerce.order_service.dtos.OrderRequestItemDTO;
import com.codingshuttle.ecommerce.order_service.dtos.ShipmentRequestDTO;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@ToString
public class OrderCreatedEvent {
    private Long id;
    private List<OrderRequestItemDTO> items;
    private BigDecimal totalPrice;
    private ShipmentRequestDTO shipmentDetails;
}
