package com.microservices.orders.services.Order;

import com.microservices.orders.models.Order;
import com.microservices.orders.repositories.OrderRepository;
import com.microservices.orders.services.CalculateUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateOrder {

    private OrderRepository orderRepository;
    private CalculateUtil calculateUtil;

    public UpdateOrder(OrderRepository orderRepository, CalculateUtil calculateUtil) {
        this.orderRepository = orderRepository;
        this.calculateUtil = calculateUtil;
    }

    public Order updateOrder(Order passedInOrder) {

        Optional<Order> orderById = orderRepository.findById(passedInOrder.getId());

        Order foundOrderToUpdate = orderById.get();
        Double orderTotal = calculateUtil.orderTotalPrice(passedInOrder);
        passedInOrder.setTotalPrice(orderTotal);
        checkForValues(passedInOrder, foundOrderToUpdate);

        return orderRepository.save(foundOrderToUpdate);
    }



    private void checkForValues(Order passedInOrder, Order orderToUpdate) {
        if (passedInOrder.getAccountId() != null) {
            orderToUpdate.setAccountId(passedInOrder.getAccountId());
        }

        if (passedInOrder.getBillingAddressId() != null) {
            orderToUpdate.setBillingAddressId(passedInOrder.getBillingAddressId());
        }

        if (passedInOrder.getLineItems() != null) {
            orderToUpdate.setLineItems(passedInOrder.getLineItems());
        }

        if (passedInOrder.getOrderDate() != null) {
            orderToUpdate.setOrderDate(passedInOrder.getOrderDate());
        }

        if (passedInOrder.getShippingAddressId() != null){
            orderToUpdate.setShippingAddressId(passedInOrder.getShippingAddressId());
        }
    }
}
