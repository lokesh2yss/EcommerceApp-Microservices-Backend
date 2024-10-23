package com.codingshuttle.ecommerce.inventory_service.controllers;

import com.codingshuttle.ecommerce.inventory_service.dtos.ProductDTO;
import com.codingshuttle.ecommerce.inventory_service.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    @GetMapping("/fetchOrders")
    public String fetchOrders() {
        ServiceInstance serviceInstance = discoveryClient.getInstances("order-service").getFirst();
        log.info(serviceInstance.getUri().toString());
        return restClient.get()
                .uri(serviceInstance.getUri()+"/orders/core/helloOrder")
                .retrieve()
                .body(String.class);

    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllInventory() {
        List<ProductDTO> inventories = productService.getAllInventory();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping(path="/{productId}")
    public ResponseEntity<ProductDTO> getInventoryById(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }
}
