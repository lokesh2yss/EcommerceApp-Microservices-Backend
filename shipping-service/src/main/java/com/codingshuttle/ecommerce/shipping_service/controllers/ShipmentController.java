package com.codingshuttle.ecommerce.shipping_service.controllers;

import com.codingshuttle.ecommerce.shipping_service.dtos.ShipmentDTO;
import com.codingshuttle.ecommerce.shipping_service.dtos.ShipmentRequestDTO;
import com.codingshuttle.ecommerce.shipping_service.services.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/shipments")
public class ShipmentController {
    private final ShipmentService shipmentService;

    @PostMapping(path = "/create-shipment")
    public ResponseEntity<ShipmentDTO> createShipment(@RequestBody ShipmentRequestDTO shipmentRequestDTO) {
        ShipmentDTO shipmentDTO = shipmentService.createShipment(shipmentRequestDTO);
        return ResponseEntity.ok(shipmentDTO);
    }
}
