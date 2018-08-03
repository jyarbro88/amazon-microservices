package com.microservices.orders.services;

import com.microservices.orders.breakers.ShipmentCircuitBreaker;
import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.Order;
import com.microservices.orders.models.temp.TempProductObject;
import com.microservices.orders.models.temp.TempShipmentObject;
import com.microservices.orders.repositories.LineItemRepository;
import com.microservices.orders.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class OrderService {

    private OrderRepository orderRepository;
    private LineItemRepository lineItemRepository;
    private RestTemplate restTemplate;
    private ShipmentCircuitBreaker shipmentCircuitBreaker;

    public OrderService(OrderRepository orderRepository, LineItemRepository lineItemRepository, RestTemplate restTemplate, ShipmentCircuitBreaker shipmentCircuitBreaker) {
        this.orderRepository = orderRepository;
        this.lineItemRepository = lineItemRepository;
        this.restTemplate = restTemplate;
        this.shipmentCircuitBreaker = shipmentCircuitBreaker;
    }

    public Iterable<Order> getAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrdersForIdOrderByDate(Long id) {
        return orderRepository.findAllByAccountIdOrderByOrderDate(id);
    }

    public Order createNewOrder(Order order) {

        List<TempShipmentObject> shipmentObjectsToSend = new ArrayList<>();

        Order savedOrder = orderRepository.save(order);
        orderRepository.flush();
        savedOrder.getId();

        List<LineItem> lineItems = savedOrder.getLineItems();

        if (lineItems == null){
            TempShipmentObject tempShipmentObject = new TempShipmentObject();

            tempShipmentObject.setAccountId(savedOrder.getAccountId());
            tempShipmentObject.setShippingAddressId(savedOrder.getShippingAddressId());
            tempShipmentObject.setOrderId(savedOrder.getId());

            shipmentObjectsToSend.add(tempShipmentObject);
        } else {
            for (LineItem lineItem : lineItems) {
                TempShipmentObject tempShipmentObject = new TempShipmentObject();

                tempShipmentObject.setAccountId(savedOrder.getAccountId());
                tempShipmentObject.setShippingAddressId(savedOrder.getShippingAddressId());
                tempShipmentObject.setOrderId(savedOrder.getId());
                tempShipmentObject.setLineItemId(lineItem.getId());

                shipmentObjectsToSend.add(tempShipmentObject);
            }
        }
        shipmentCircuitBreaker.postNewShipment(shipmentObjectsToSend);

        return savedOrder;
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public TempProductObject getProductInformation(Long lineItemId) {
        Optional<LineItem> byId = lineItemRepository.findById(lineItemId);
        LineItem foundLineItem = byId.get();
        Long productId = foundLineItem.getProductId();
        return restTemplate.getForObject("http://products-service/products/" + productId, TempProductObject.class);
    }
}
