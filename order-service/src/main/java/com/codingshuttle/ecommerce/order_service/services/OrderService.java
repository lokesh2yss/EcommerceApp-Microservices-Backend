package com.codingshuttle.ecommerce.order_service.services;

import com.codingshuttle.ecommerce.order_service.clients.InventoryOpenFeignClient;
import com.codingshuttle.ecommerce.order_service.clients.ShipmentOpenFeignClient;
import com.codingshuttle.ecommerce.order_service.dtos.OrderDTO;
import com.codingshuttle.ecommerce.order_service.dtos.OrderRequestDTO;
import com.codingshuttle.ecommerce.order_service.dtos.ShipmentDTO;
import com.codingshuttle.ecommerce.order_service.entities.Order;
import com.codingshuttle.ecommerce.order_service.entities.OrderItem;
import com.codingshuttle.ecommerce.order_service.entities.enums.OrderStatus;
import com.codingshuttle.ecommerce.order_service.repositories.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;
    private final ShipmentOpenFeignClient shipmentOpenFeignClient;

    public List<OrderDTO> getAllOrders() {
        log.info("Fetching all orders");
        List<Order> orders = orderRepository.findAll();
        return orders
                .stream()
                .map((order) -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }
    public OrderDTO getOrderById(Long orderId) {
        log.info("Fetching order with id: {}", orderId);
        Optional<Order> order = orderRepository.findById(orderId);

        return order.map((element) -> modelMapper.map(element, OrderDTO.class))
                .orElseThrow(() ->
                        new RuntimeException("Order not found with id: "+orderId));
    }

    @Transactional
    //@CircuitBreaker(name="inventoryCircuitBreaker", fallbackMethod = "createOrderCircuitBreakerFallback")
//    @Retry(name="inventoryRetry", fallbackMethod = "createOrderFallback")
//    @RateLimiter(name="inventoryRateLimiter", fallbackMethod = "createOrderRateLimiterFallback")
    public OrderDTO createOrder(OrderRequestDTO orderRequestDTO) {
        log.info("Trying to create an order while reducing the stock in inventory service");
        BigDecimal totalPrice = inventoryOpenFeignClient.reduceStocks(orderRequestDTO);
        Order newOrder = modelMapper.map(orderRequestDTO, Order.class);
        for(OrderItem orderItem: newOrder.getItems()) {
            orderItem.setOrder(newOrder);
        }
        newOrder.setOrderStatus(OrderStatus.CONFIRMED);
        newOrder.setTotalPrice(totalPrice);
        Order savedOrder = orderRepository.save(newOrder);
        orderRequestDTO.getShipmentDetails().setOrderId(savedOrder.getId());
        log.info("Trying to create a shipment for the order with details: {}", orderRequestDTO.getShipmentDetails());
        ShipmentDTO shipmentDTO = shipmentOpenFeignClient.createShipment(orderRequestDTO.getShipmentDetails());

        OrderDTO orderDTO =  modelMapper.map(savedOrder, OrderDTO.class);
        orderDTO.setShipment(shipmentDTO);

        return orderDTO;
    }
    public OrderDTO createOrderCircuitBreakerFallback(OrderRequestDTO orderRequestDTO, Throwable throwable) {
        log.error("createOrder method failed due to: {} and createOrderCircuitBreakerFallback was called", throwable.getMessage());
        return new OrderDTO();
    }
    public OrderDTO createOrderRateLimiterFallback(OrderRequestDTO orderRequestDTO, Throwable throwable) {
        log.error("createOrder method failed due to: {} and createOrderRateLimiterFallback was called", throwable.getMessage());
        return new OrderDTO();
    }
    public OrderDTO createOrderFallback(OrderRequestDTO orderRequestDTO, Throwable throwable) {
        log.error("createOrder method failed due to: {} and createOrderFallback was called", throwable.getMessage());
        return new OrderDTO();
    }


    @Transactional
    @CircuitBreaker(name="inventoryCircuitBreaker", fallbackMethod = "cancelOrder")
//    @Retry(name="inventoryRetry", fallbackMethod = "cancelOrder")
//    @RateLimiter(name="inventoryRateLimiter", fallbackMethod = "cancelOrder")
    public OrderDTO cancelOrder(Long orderId) {
        log.info("Trying to cancel an order with id: {}, while restocking the stock in inventory service", orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new RuntimeException("Order not found with id: "+orderId));
        if(order.getOrderStatus().equals(OrderStatus.CANCELLED)) {
            throw new RuntimeException("Order already cancelled. Cannot re-cancel");
        }
        OrderRequestDTO orderRequestDTO = modelMapper.map(order, OrderRequestDTO.class);
        boolean restockSuccessful = inventoryOpenFeignClient.restockForCancelledOrder(orderRequestDTO);
        order.getItems().clear();
        order.setOrderStatus(OrderStatus.CANCELLED);
        Order savedOrder = orderRepository.save(order);

        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    public OrderDTO cancelOrder(Long orderId, Throwable throwable) {
        log.error("Inside cancelOrderFallback due to: "+throwable.getMessage());
        return new OrderDTO();
    }
}
