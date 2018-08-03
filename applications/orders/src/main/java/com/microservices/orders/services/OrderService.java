package com.microservices.orders.services;

import com.microservices.orders.breakers.ShipmentCircuitBreaker;
import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.Order;
import com.microservices.orders.models.temp.TempProductObject;
import com.microservices.orders.models.temp.TempShipmentObject;
import com.microservices.orders.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class OrderService {

    private OrderRepository orderRepository;
    private LineItemService lineItemService;
    private RestTemplate restTemplate;
    private ShipmentCircuitBreaker shipmentCircuitBreaker;

    private String PRODUCTS_SERVICE_URL = "http://products-service/products/";
    private String SHIPMENTS_SERVICE_URL = "http://shipments-service/shipments";

    public OrderService(OrderRepository orderRepository, LineItemService lineItemService, RestTemplate restTemplate, ShipmentCircuitBreaker shipmentCircuitBreaker) {
        this.orderRepository = orderRepository;
        this.lineItemService = lineItemService;
        this.restTemplate = restTemplate;
        this.shipmentCircuitBreaker = shipmentCircuitBreaker;
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
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
        if (order.getLineItems() != null) {
            for (LineItem lineItem: order.getLineItems()) {
                lineItemService.createNewLineItem(lineItem);
            }
            Order savedOrder = persistOrder(order);
            Long savedOrderId = savedOrder.getId();
            List<LineItem> savedOrderLineItems = savedOrder.getLineItems();

            for (LineItem savedOrderLineItem : savedOrderLineItems) {
                savedOrderLineItem.setOrderId(savedOrderId);
                lineItemService.updateLineItem(savedOrderLineItem);
            }
            buildAndSendShipmentObject(savedOrder);

            return savedOrder;
        } else {
            Order savedOrder = persistOrder(order);
            buildAndSendShipmentObject(savedOrder);

            return savedOrder;
        }
    }

    public List<TempProductObject> getProductInformation(Long orderId) {

        List<LineItem> lineItemsForOrder = lineItemService.findByOrderId(orderId);
        List<TempProductObject> productsToReturnList = new ArrayList<>();
        for (LineItem lineItem : lineItemsForOrder) {

            Long productId = lineItem.getProductId();
            TempProductObject foundProduct = restTemplate.getForObject(PRODUCTS_SERVICE_URL + productId, TempProductObject.class);

            foundProduct.setId(productId);
            productsToReturnList.add(foundProduct);
        }

        return productsToReturnList;
    }

    private TempShipmentObject buildAndSendShipmentObject(Order savedOrder) {

        TempShipmentObject shipmentObjectToSend = new TempShipmentObject();

        shipmentObjectToSend.setOrderId(savedOrder.getId());
        shipmentObjectToSend.setShippingAddressId(savedOrder.getShippingAddressId());
        shipmentObjectToSend.setAccountId(savedOrder.getAccountId());

        TempShipmentObject returnedShipmentObject = shipmentCircuitBreaker.postNewShipment(shipmentObjectToSend);

        return returnedShipmentObject;
    }

    private Order persistOrder(Order order) {

        Order savedOrder = orderRepository.save(order);
        orderRepository.flush();
        return savedOrder;
    }
}
