package com.codingshuttle.ecommerce.shipping_service.repositories;

import com.codingshuttle.ecommerce.shipping_service.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
