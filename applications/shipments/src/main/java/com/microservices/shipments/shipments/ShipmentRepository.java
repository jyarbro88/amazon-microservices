package com.microservices.shipments.shipments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {


    List<Shipment> findAllByAccountId(Long accountId);
//
//    Shipment findAllById(Long accountId);

//    Shipment findAllById(Long id);

    Optional<Shipment> findById(Long shipmentId);

    Shipment findByLineItemId(Long lineItemId);
}
