package com.codingshuttle.ecommerce.order_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderRequestItemDTO {
    private Long id;
    private Long productId;
    private int quantity;
}
