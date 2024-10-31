package com.codingshuttle.ecommerce.order_service.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
public class OrderItemDTO {
    private Long id;
    private Long productId;
    private int quantity;
    @JsonIgnore
    private OrderDTO order;
}
