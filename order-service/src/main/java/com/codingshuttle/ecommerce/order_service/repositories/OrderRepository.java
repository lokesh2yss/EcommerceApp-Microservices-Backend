package com.codingshuttle.ecommerce.order_service.repositories;

import com.codingshuttle.ecommerce.order_service.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
