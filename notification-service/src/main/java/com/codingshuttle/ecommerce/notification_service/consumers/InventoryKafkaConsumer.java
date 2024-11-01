package com.codingshuttle.ecommerce.notification_service.consumers;

import com.codingshuttle.ecommerce.inventory_service.events.OrderFulfilledEvent;
import com.codingshuttle.ecommerce.notification_service.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryKafkaConsumer {

    @KafkaListener(topics= AppConstants.ORDER_FULFILLED_TOPIC)
    public void handleOrderFulfilledEvent(OrderFulfilledEvent orderFulfilledEvent) {
        log.info("handleOrderFulfilledEvent: {}", orderFulfilledEvent);
    }

    @KafkaListener(topics= AppConstants.ORDER_OUT_OF_STOCK_TOPIC)
    public void handleOrderOutOfStockEvent(OrderFulfilledEvent orderFulfilledEvent) {
        log.info("handleOrderOutOfStockEvent: {}", orderFulfilledEvent);
    }
}
