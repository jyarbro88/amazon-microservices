package com.microservices.orders.services.Order;

import com.microservices.orders.breakers.ShipmentCircuitBreaker;
import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.Order;
import com.microservices.orders.models.temp.TempProduct;
import com.microservices.orders.models.temp.TempShipment;
import com.microservices.orders.repositories.OrderRepository;
import com.microservices.orders.services.LineItem.LineItemService;
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

    public List<Order> findAllOrdersByAccountIdOrderByDate(Long id) {
        return orderRepository.findAllByAccountIdOrderByOrderDate(id);
    }

    public Order createNewOrder(Order order) {

        Double orderTotal = 0.00;

        if (order.getLineItems() != null) {
            for (LineItem lineItem: order.getLineItems()) {
                lineItemService.createNewLineItem(lineItem);
            }
            Order savedOrder = persistOrder(order);
            Long savedOrderId = savedOrder.getId();
            TempShipment newShipmentForOrder = buildAndSendShipmentObject(savedOrder);
            Long shipmentId = newShipmentForOrder.getId();

            List<LineItem> savedOrderLineItems = savedOrder.getLineItems();

            for (LineItem savedOrderLineItem : savedOrderLineItems) {
                savedOrderLineItem.setOrderId(savedOrderId);
                savedOrderLineItem.setShipmentId(shipmentId);
                lineItemService.updateLineItem(savedOrderLineItem);

                Double lineItemTotalPrice = savedOrderLineItem.getLineItemTotalPrice();

                //Todo:  don't calculate here, extract to a utility
                orderTotal += lineItemTotalPrice;
            }
            savedOrder.setTotalPrice(orderTotal);
            persistOrder(savedOrder);


            return savedOrder;
        } else {

            return persistOrder(order);
        }
    }

    public List<TempProduct> findProductInfoForOrderId(Long orderId) {

        List<LineItem> lineItemsForOrder = lineItemService.findByOrderId(orderId);
        List<TempProduct> productsToReturnList = new ArrayList<>();
        for (LineItem lineItem : lineItemsForOrder) {

            Long productId = lineItem.getProductId();
            TempProduct foundProduct = restTemplate.getForObject(PRODUCTS_SERVICE_URL + productId, TempProduct.class);

            foundProduct.setId(productId);
            productsToReturnList.add(foundProduct);
        }

        return productsToReturnList;
    }

    private TempShipment buildAndSendShipmentObject(Order savedOrder) {

        TempShipment shipmentObjectToSend = new TempShipment();

        shipmentObjectToSend.setOrderId(savedOrder.getId());
        shipmentObjectToSend.setShippingAddressId(savedOrder.getShippingAddressId());
        shipmentObjectToSend.setAccountId(savedOrder.getAccountId());

        return shipmentCircuitBreaker.postNewShipment(shipmentObjectToSend);
    }

    private Order persistOrder(Order order) {

        Order savedOrder = orderRepository.save(order);
        orderRepository.flush();
        return savedOrder;
    }
}
