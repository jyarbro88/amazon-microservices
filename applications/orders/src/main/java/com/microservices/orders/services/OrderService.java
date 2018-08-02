package com.microservices.orders.services;

import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.Order;
import com.microservices.orders.models.display.OrderAddressToDisplay;
import com.microservices.orders.models.display.OrderLineItemToDisplay;
import com.microservices.orders.models.display.OrderShipmentsToDisplay;
import com.microservices.orders.models.display.OrderToDisplay;
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
    private LineItemService lineItemService;
    private RestTemplate restTemplate;

    public OrderService(OrderRepository orderRepository, LineItemRepository lineItemRepository, LineItemService lineItemService, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.lineItemRepository = lineItemRepository;
        this.lineItemService = lineItemService;
        this.restTemplate = restTemplate;
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
        return orderRepository.save(order);
    }

    public Order updateOrder(Order passedInOrder) {
        Optional<Order> orderById = orderRepository.findById(passedInOrder.getId());
        Order orderToUpdate = orderById.get();

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

        if (passedInOrder.getTotalPrice() != null){
            orderToUpdate.setTotalPrice(passedInOrder.getTotalPrice());
        }

        return orderRepository.save(orderToUpdate);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }


    public OrderToDisplay getOrderDetailsById(Long orderId) {

        Optional<Order> foundOrderList = orderRepository.findById(orderId);
        Order foundOrder = foundOrderList.get();
        Long shippingAddressId = foundOrder.getShippingAddressId();

        OrderToDisplay orderToDisplay = new OrderToDisplay();
        orderToDisplay.setOrderNumber(foundOrder.getId());
        orderToDisplay.setOrderTotalPrice(foundOrder.getTotalPrice());

        OrderAddressToDisplay orderShippingAddress = restTemplate.getForObject("http://accounts-service/accounts/" + foundOrder.getAccountId() + "/address/" + shippingAddressId, OrderAddressToDisplay.class);

        orderShippingAddress.setShippingAddressId(shippingAddressId);
        orderToDisplay.setShippingAddress(orderShippingAddress);

//        List<LineItem> lineItemsForOrderId = lineItemRepository.findAllByOrderId(foundOrder.getId());

        List<LineItem> lineItemsForOrderId = lineItemService.findByOrderId(foundOrder.getId());

        List<OrderLineItemToDisplay> lineItemsForOrderList = new ArrayList<>();
        List<OrderShipmentsToDisplay> shipmentItemsForOrderList = new ArrayList<>();

        for (LineItem lineItem : lineItemsForOrderId) {
            OrderLineItemToDisplay orderLineItemToDisplay = new OrderLineItemToDisplay();
            OrderShipmentsToDisplay shipmentLineItemToDisplay = new OrderShipmentsToDisplay();

            TempProductObject tempProduct = restTemplate.getForObject("http://products-service/products/" + lineItem.getProductId(), TempProductObject.class);
            TempShipmentObject tempShipment = restTemplate.getForObject("http://shipments-service/shipments/" + lineItem.getShipmentId(), TempShipmentObject.class);

            shipmentLineItemToDisplay.setDeliveryDate(tempShipment.getDeliveredDate());
            shipmentLineItemToDisplay.setShippedDate(tempShipment.getShippedDate());
            shipmentLineItemToDisplay.setShipmentId(tempShipment.getId());
            shipmentLineItemToDisplay.setOrderLineItemId(lineItem.getId());

            orderLineItemToDisplay.setOrderLineItemId(lineItem.getId());
            orderLineItemToDisplay.setProductName(tempProduct.getName());
            orderLineItemToDisplay.setQuantity(lineItem.getQuantity());

//            lineItem.getOrder();
            lineItemsForOrderList.add(orderLineItemToDisplay);
            shipmentItemsForOrderList.add(shipmentLineItemToDisplay);
        }

        orderToDisplay.setOrderLineItemsList(lineItemsForOrderList);
        orderToDisplay.setOrderShipmentsList(shipmentItemsForOrderList);

        return orderToDisplay;
    }

    public TempProductObject getProductInformation(Long lineItemId) {
        Optional<LineItem> byId = lineItemRepository.findById(lineItemId);
        LineItem foundLineItem = byId.get();
        Long productId = foundLineItem.getProductId();
        return restTemplate.getForObject("http://products-service/products/" + productId, TempProductObject.class);
    }
}
