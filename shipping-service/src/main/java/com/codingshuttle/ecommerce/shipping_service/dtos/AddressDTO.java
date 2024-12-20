package com.codingshuttle.ecommerce.shipping_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long id;

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
