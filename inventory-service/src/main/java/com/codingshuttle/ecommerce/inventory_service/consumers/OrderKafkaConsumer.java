package com.codingshuttle.ecommerce.inventory_service.consumers;

import com.codingshuttle.ecommerce.inventory_service.dtos.OrderRequestDTO;
import com.codingshuttle.ecommerce.inventory_service.events.OrderFulfilledEvent;
import com.codingshuttle.ecommerce.inventory_service.services.ProductService;
import com.codingshuttle.ecommerce.inventory_service.utils.AppConstants;
import com.codingshuttle.ecommerce.order_service.entities.enums.OrderStatus;
import com.codingshuttle.ecommerce.order_service.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderKafkaConsumer {
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long, OrderFulfilledEvent> kafkaTemplate;

    @KafkaListener(topics = AppConstants.ORDER_CREATED_TOPIC)
    public void handleOrderCreateEvent(OrderCreatedEvent orderCreatedEvent) {
        log.info("handleOrderCreateEvent: {}", orderCreatedEvent);
        OrderRequestDTO orderRequestDTO = modelMapper.map(orderCreatedEvent, OrderRequestDTO.class);
        BigDecimal totalPrice = BigDecimal.ZERO;
        OrderFulfilledEvent orderFulfilledEvent = new OrderFulfilledEvent();
        orderFulfilledEvent.setOrderId(orderCreatedEvent.getId());
        orderFulfilledEvent.setItems(orderCreatedEvent.getItems());
        orderFulfilledEvent.setShipmentDetails(orderCreatedEvent.getShipmentDetails());
        try {
            totalPrice = productService.reduceStocks(orderRequestDTO);
            orderFulfilledEvent.setOrderStatus(OrderStatus.FULFILLED);
            orderFulfilledEvent.setTotalPrice(totalPrice);
            log.info("Sending order status update kafka message");
            kafkaTemplate.send(AppConstants.ORDER_FULFILLED_TOPIC, orderFulfilledEvent.getOrderId(), orderFulfilledEvent);
            log.info("Successfully fulfilled the order");
        }catch (Exception e) {
            log.error("Order couldn't be fulfilled because some items are out-of-stock");
            orderFulfilledEvent.setTotalPrice(totalPrice);
            orderFulfilledEvent.setOrderStatus(OrderStatus.OUT_OF_STOCK);
            kafkaTemplate.send(AppConstants.ORDER_OUT_OF_STOCK_TOPIC, orderFulfilledEvent.getOrderId(), orderFulfilledEvent);
        }
    }
}
