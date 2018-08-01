package com.microservices.orders.orders;

import com.microservices.orders.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByAccountIdOrderByOrderDate(Long accountId);

//    Order findAllById(Long accountId);

}
