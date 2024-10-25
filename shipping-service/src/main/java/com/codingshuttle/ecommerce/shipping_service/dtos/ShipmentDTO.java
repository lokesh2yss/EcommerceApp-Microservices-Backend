package com.codingshuttle.ecommerce.shipping_service.dtos;

import com.codingshuttle.ecommerce.shipping_service.entities.enums.Carrier;
import com.codingshuttle.ecommerce.shipping_service.entities.enums.ShipmentStatus;

import java.time.LocalDate;

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
