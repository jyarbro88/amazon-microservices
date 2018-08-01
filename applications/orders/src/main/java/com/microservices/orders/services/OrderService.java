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

    public Order updateOrder(Long id, Order passedInOrder){
        Optional<Order> orderById = orderRepository.findById(id);
        Order orderToUpdate = orderById.get();
        orderToUpdate.setAccountId(passedInOrder.getAccountId());
        orderToUpdate.setBillingAddressId(passedInOrder.getBillingAddressId());
        orderToUpdate.setLineItems(passedInOrder.getLineItems());
        orderToUpdate.setOrderDate(passedInOrder.getOrderDate());
        orderToUpdate.setShippingAddressId(passedInOrder.getShippingAddressId());
        orderToUpdate.setTotalPrice(passedInOrder.getTotalPrice());

        return orderRepository.save(orderToUpdate);
    }

    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }
}
