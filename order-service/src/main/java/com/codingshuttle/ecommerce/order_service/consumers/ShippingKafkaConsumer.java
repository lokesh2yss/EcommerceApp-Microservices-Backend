package com.codingshuttle.ecommerce.order_service.consumers;

import com.codingshuttle.ecommerce.order_service.entities.enums.OrderStatus;
import com.codingshuttle.ecommerce.order_service.services.OrderService;
import com.codingshuttle.ecommerce.order_service.utils.AppConstants;
import com.codingshuttle.ecommerce.shipping_service.events.OrderShippedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShippingKafkaConsumer {
    private final OrderService orderService;
    @KafkaListener(topics = AppConstants.ORDER_SHIPPED_TOPIC)
    public void handleOrderShippedEvent(OrderShippedEvent orderShippedEvent) {
        log.info("handleOrderShippedEvent: {}", orderShippedEvent);
        orderService.updateOrderStatus(orderShippedEvent.getOrderId(), OrderStatus.SHIPPED);
    }
}
