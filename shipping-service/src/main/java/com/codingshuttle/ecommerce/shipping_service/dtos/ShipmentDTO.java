package com.codingshuttle.ecommerce.shipping_service.dtos;

import com.codingshuttle.ecommerce.shipping_service.entities.enums.Carrier;
import com.codingshuttle.ecommerce.shipping_service.entities.enums.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDTO {
    private Long orderId;

    private String trackingNumber;

    private Carrier carrier;

    private ShipmentStatus shipmentStatus;

    private AddressDTO originAddress;

    private AddressDTO destinationAddress;

    private LocalDate shippedOn;

    private LocalDate expectedDeliveryDate;
}
