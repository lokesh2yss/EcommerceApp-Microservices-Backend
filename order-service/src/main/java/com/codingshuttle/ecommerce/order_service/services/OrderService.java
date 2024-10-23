package com.codingshuttle.ecommerce.order_service.services;

import com.codingshuttle.ecommerce.order_service.dtos.OrderDTO;
import com.codingshuttle.ecommerce.order_service.entities.Order;
import com.codingshuttle.ecommerce.order_service.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;

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
}
