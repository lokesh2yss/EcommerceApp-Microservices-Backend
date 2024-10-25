package com.codingshuttle.ecommerce.order_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentRequestDTO {
    private Long orderId;
    private AddressRequestDTO originAddress;
    private AddressRequestDTO destinationAddress;
}
