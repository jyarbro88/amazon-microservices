package com.microservices.orders.services.Order;

import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.Order;
import com.microservices.orders.repositories.OrderRepository;
import com.microservices.orders.services.LineItem.LineItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UpdateOrder {

    private OrderRepository orderRepository;
    private LineItemService lineItemService;

    public UpdateOrder(OrderRepository orderRepository, LineItemService lineItemService) {
        this.orderRepository = orderRepository;
        this.lineItemService = lineItemService;
    }

    public Order updateOrder(Order passedInOrder) {

        Optional<Order> orderById = orderRepository.findById(passedInOrder.getId());
        Order orderToUpdate = orderById.get();

        calculateTotalPrice(passedInOrder, orderToUpdate);
        checkForValues(passedInOrder, orderToUpdate);

        return orderRepository.save(orderToUpdate);
    }

    private void calculateTotalPrice(Order passedInOrder, Order orderToUpdate) {

        Double orderTotal = 0.00;
        List<LineItem> lineItemsToUpdate = passedInOrder.getLineItems();

        for (LineItem lineItem : lineItemsToUpdate) {
            Long lineItemId = lineItem.getId();

            Optional<LineItem> foundLineItemList = lineItemService.findLineItemById(lineItemId);
            LineItem foundLineItem = foundLineItemList.get();

            Double lineItemTotalPrice = foundLineItem.getLineItemTotalPrice();

            orderTotal += lineItemTotalPrice;
        }

        orderToUpdate.setTotalPrice(orderTotal);
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
