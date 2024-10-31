package com.codingshuttle.ecommerce.notification_service.consumers;

import com.codingshuttle.ecommerce.notification_service.utils.AppConstants;
import com.codingshuttle.ecommerce.order_service.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderKafkaConsumer {

    @KafkaListener(topics = AppConstants.ORDER_CREATED_TOPIC)
    public void handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        log.info("Order Creation Notification Received: {}", orderCreatedEvent);

    }


}
