package com.codingshuttle.ecommerce.order_service.controllers;

import com.codingshuttle.ecommerce.order_service.dtos.OrderDTO;
import com.codingshuttle.ecommerce.order_service.dtos.OrderRequestDTO;
import com.codingshuttle.ecommerce.order_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/core")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/helloOrder")
    public String helloOrder(@RequestHeader("X-User-Id") String userId) {

        return "Hello from order service, userId is: "+userId;
    }

    @PostMapping(path = "/create-order")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        OrderDTO orderDTO = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.ok(orderDTO);
    }
    @DeleteMapping(path = "/cancel-order/{orderId}")
    public ResponseEntity<OrderDTO> cancelOrder(@PathVariable Long orderId) {
        OrderDTO orderDTO = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(orderDTO);
    }
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        log.info("Fetching all orders via OrderController");
        List<OrderDTO> orders = orderService.getAllOrders();

        return ResponseEntity.ok(orders);
    }

    @GetMapping(path = "/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        log.info("Fetching order for id: {}",orderId);
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
}
