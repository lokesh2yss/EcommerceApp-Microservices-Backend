package com.codingshuttle.ecommerce.order_service.dtos;

import com.codingshuttle.ecommerce.order_service.entities.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private OrderStatus orderStatus;
    private BigDecimal totalPrice;
    private List<OrderItemDTO> items;
    private ShipmentDTO shipment;
}
