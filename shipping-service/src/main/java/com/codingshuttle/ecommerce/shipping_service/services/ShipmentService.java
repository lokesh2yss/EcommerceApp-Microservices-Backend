package com.codingshuttle.ecommerce.shipping_service.services;

import com.codingshuttle.ecommerce.shipping_service.dtos.ShipmentDTO;
import com.codingshuttle.ecommerce.shipping_service.dtos.ShipmentRequestDTO;
import com.codingshuttle.ecommerce.shipping_service.entities.Shipment;
import com.codingshuttle.ecommerce.shipping_service.entities.enums.Carrier;
import com.codingshuttle.ecommerce.shipping_service.entities.enums.ShipmentStatus;
import com.codingshuttle.ecommerce.shipping_service.repositories.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final ModelMapper modelMapper;

    public ShipmentDTO createShipment(ShipmentRequestDTO shipmentRequestDTO) {
        log.info("Trying to create the shipment for the given order: {}", shipmentRequestDTO);
        Shipment shipment = modelMapper.map(shipmentRequestDTO, Shipment.class);
        shipment.setShipmentStatus(ShipmentStatus.SHIPPED);
        shipment.setCarrier(Carrier.UPS);
        shipment.setShippedOn(LocalDate.now());
        shipment.setExpectedDeliveryDate(LocalDate.now().plusDays(5));
        shipment.setTrackingNumber(createTrackingNumber());

        Shipment savedShipment = shipmentRepository.save(shipment);
        log.info("Successfully created shipment: {}", savedShipment);
        return modelMapper.map(savedShipment, ShipmentDTO.class);
    }

    private String createTrackingNumber() {
        Random random = new Random();
        Long nextLong = random.nextLong(1000000000000L);
        return String.format("%013d", nextLong);
    }
}
