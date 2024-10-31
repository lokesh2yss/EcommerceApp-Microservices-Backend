package com.codingshuttle.ecommerce.consumers;

import com.codingshuttle.ecommerce.inventory_service.events.OrderFulfilledEvent;
import com.codingshuttle.ecommerce.shipping_service.services.ShipmentService;
import com.codingshuttle.ecommerce.shipping_service.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryKafkaConsumer {
    private final ShipmentService shipmentService;

    @KafkaListener(topics = AppConstants.ORDER_FULFILLED_TOPIC)
    public void handleOrderFulfilledEvent(OrderFulfilledEvent orderFulfilledEvent) {
        shipmentService.createShipment(orderFulfilledEvent.getShipmentDetails());

        // TODO send an ORDER_SHIPPED kafka event
    }

}
