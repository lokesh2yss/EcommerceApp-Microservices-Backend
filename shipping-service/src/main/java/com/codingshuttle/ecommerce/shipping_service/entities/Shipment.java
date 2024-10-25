package com.codingshuttle.ecommerce.shipping_service.entities;

import com.codingshuttle.ecommerce.shipping_service.entities.enums.Carrier;
import com.codingshuttle.ecommerce.shipping_service.entities.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private String trackingNumber;

    private Carrier carrier;

    private ShipmentStatus shipmentStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "origin_address_id", referencedColumnName = "id")
    private Address originAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_address_id", referencedColumnName = "id")
    private Address destinationAddress;

    private LocalDate shippedOn;

    private LocalDate expectedDeliveryDate;
}
