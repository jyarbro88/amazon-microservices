package com.microservices.orders.services;

import com.microservices.orders.models.Order;
import com.microservices.orders.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Iterable<Order> getAll(){
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id){
        return orderRepository.findById(id);
    }

    public Order createNewOrder(Order order){
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order order){
        Optional<Order> passedInOrder = orderRepository.findById(id);
        Order foundOrderToUpdate = passedInOrder.get();
        foundOrderToUpdate.setAccountId(order.getAccountId());
        foundOrderToUpdate.setBillingAddressId(order.getBillingAddressId());
        foundOrderToUpdate.setLineItems(order.getLineItems());
        foundOrderToUpdate.setOrderDate(order.getOrderDate());
        foundOrderToUpdate.setShippingAddressId(order.getShippingAddressId());
        foundOrderToUpdate.setTotalPrice(order.getTotalPrice());

        return orderRepository.save(foundOrderToUpdate);
    }

}
