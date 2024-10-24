package com.codingshuttle.ecommerce.order_service.clients;

import com.codingshuttle.ecommerce.order_service.dtos.OrderRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@FeignClient(name = "inventory-service", path="/inventory")
public interface InventoryOpenFeignClient {

    @PutMapping(path = "/products/reduce-stocks")
    BigDecimal reduceStocks(@RequestBody OrderRequestDTO orderRequestDTO);
}
