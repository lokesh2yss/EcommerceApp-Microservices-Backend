package com.codingshuttle.ecommerce.order_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderRequestDTO {
    private Long id;
    private List<OrderRequestItemDTO> items;
    private BigDecimal totalPrice;
    private ShipmentRequestDTO shipmentDetails;
}
