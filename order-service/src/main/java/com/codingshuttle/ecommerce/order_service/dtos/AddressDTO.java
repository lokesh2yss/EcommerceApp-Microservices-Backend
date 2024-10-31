package com.codingshuttle.ecommerce.order_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDTO {
    private Long id;

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
