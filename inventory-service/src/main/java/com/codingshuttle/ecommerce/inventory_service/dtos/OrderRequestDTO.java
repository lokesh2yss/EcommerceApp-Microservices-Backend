package com.codingshuttle.ecommerce.inventory_service.dtos;

import com.codingshuttle.ecommerce.order_service.dtos.ShipmentRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private Long id;
    private List<OrderRequestItemDTO> items;
    private BigDecimal totalPrice;
    private ShipmentRequestDTO shipmentDetails;
}
