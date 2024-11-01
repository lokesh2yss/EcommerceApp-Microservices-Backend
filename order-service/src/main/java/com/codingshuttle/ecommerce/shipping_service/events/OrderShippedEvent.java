package com.codingshuttle.ecommerce.shipping_service.events;


import com.codingshuttle.ecommerce.order_service.dtos.AddressDTO;
import com.codingshuttle.ecommerce.order_service.entities.enums.Carrier;
import com.codingshuttle.ecommerce.order_service.entities.enums.ShipmentStatus;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class OrderShippedEvent {
    private Long orderId;
    private String trackingNumber;
    private Carrier carrier;
    private ShipmentStatus shipmentStatus;
    private AddressDTO originAddress;
    private AddressDTO destinationAddress;
    private LocalDate shippedOn;
    private LocalDate expectedDeliveryDate;
}
