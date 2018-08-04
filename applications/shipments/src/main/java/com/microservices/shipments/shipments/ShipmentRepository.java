package com.microservices.shipments.shipments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    List<Shipment> findAllByAccountId(Long accountId);

    Optional<Shipment> findById(Long shipmentId);

    List<Shipment> findAllByAccountIdOrderByDeliveredDate(Long accountId);

    List<Shipment> findAllByOrderId(Long orderId);
}
