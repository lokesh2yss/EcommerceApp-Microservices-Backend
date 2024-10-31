package com.codingshuttle.ecommerce.order_service.consumers;

import com.codingshuttle.ecommerce.inventory_service.events.OrderFulfilledEvent;
import com.codingshuttle.ecommerce.order_service.services.OrderService;
import com.codingshuttle.ecommerce.order_service.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryKafkaConsumer {
    private final OrderService orderService;
    @KafkaListener(topics = AppConstants.ORDER_FULFILLED_TOPIC)
    public void handleOrderFulfilledTopic(OrderFulfilledEvent orderFulfilledEvent) {
        log.info("handleOrderFulfilledTopic: {}", orderFulfilledEvent);
        orderService.updateOrderStatus(orderFulfilledEvent);
    }

    @KafkaListener(topics = AppConstants.ORDER_OUT_OF_STOCK_TOPIC)
    public void handleOrderOutOfStockTopic(OrderFulfilledEvent orderFulfilledEvent) {
        log.info("handleOrderFulfilledTopic: {}", orderFulfilledEvent);
        orderService.updateOrderStatus(orderFulfilledEvent);
    }
}
