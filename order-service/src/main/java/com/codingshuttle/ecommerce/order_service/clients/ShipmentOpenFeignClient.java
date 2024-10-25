package com.codingshuttle.ecommerce.order_service.clients;

import com.codingshuttle.ecommerce.order_service.dtos.ShipmentDTO;
import com.codingshuttle.ecommerce.order_service.dtos.ShipmentRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "shipping-service", path="/shipping")
public interface ShipmentOpenFeignClient {
    @PostMapping(path = "/shipments/create-shipment")
    ShipmentDTO createShipment(@RequestBody ShipmentRequestDTO shipmentRequestDTO);
}


