package com.microservices.orders.repositories;

import com.microservices.orders.models.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Long> {

    List<LineItem> findAllByOrderId(Long orderId);
}
