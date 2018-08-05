package com.microservices.orders.services.Order;

import com.microservices.orders.models.Order;
import com.microservices.orders.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Iterable<Order> getAll() {
        return orderRepository.findAll();
    }

    Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findAllOrdersByAccountIdOrderByDate(Long id) {
        return orderRepository.findAllByAccountIdOrderByOrderDate(id);
    }

    Order saveOrder(Order order) {

        Order savedOrder = orderRepository.save(order);
        orderRepository.flush();
        return savedOrder;
    }
}
