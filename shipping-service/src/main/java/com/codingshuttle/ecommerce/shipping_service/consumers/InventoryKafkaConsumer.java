package com.codingshuttle.ecommerce.shipping_service.consumers;

import com.codingshuttle.ecommerce.shipping_service.events.OrderShippedEvent;
import com.codingshuttle.ecommerce.inventory_service.events.OrderFulfilledEvent;
import com.codingshuttle.ecommerce.shipping_service.dtos.ShipmentDTO;
import com.codingshuttle.ecommerce.shipping_service.services.ShipmentService;
import com.codingshuttle.ecommerce.shipping_service.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryKafkaConsumer {
    private final ShipmentService shipmentService;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long, OrderShippedEvent> kafkaTemplate;

    @KafkaListener(topics = AppConstants.ORDER_FULFILLED_TOPIC)
    public void handleOrderFulfilledEvent(OrderFulfilledEvent orderFulfilledEvent) {
        log.info("handleOrderFulfilledEvent: {}", orderFulfilledEvent);
        ShipmentDTO shipmentDTO = shipmentService.createShipment(orderFulfilledEvent.getShipmentDetails());
        OrderShippedEvent orderShippedEvent = modelMapper.map(shipmentDTO, OrderShippedEvent.class);
        orderShippedEvent.setOrderId(orderFulfilledEvent.getOrderId());
        // TODO send an ORDER_SHIPPED kafka event
        kafkaTemplate.send(AppConstants.ORDER_SHIPPED_TOPIC, orderShippedEvent.getOrderId(), orderShippedEvent);
        log.info("Successfully created ORDER_SHIPPED_TOPIC");
    }

}
