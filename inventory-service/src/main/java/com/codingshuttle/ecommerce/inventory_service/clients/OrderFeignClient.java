package com.codingshuttle.ecommerce.inventory_service.clients;

import com.codingshuttle.ecommerce.inventory_service.configs.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "order-service", path="/orders", configuration = FeignClientConfig.class)
public interface OrderFeignClient {

    @GetMapping("/core/helloOrder")
    String helloOrder();
}
