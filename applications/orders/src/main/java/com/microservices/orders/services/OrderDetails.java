package com.microservices.orders.services;

import com.microservices.orders.breakers.AddressCircuitBreaker;
import com.microservices.orders.breakers.ProductCircuitBreaker;
import com.microservices.orders.breakers.ShipmentCircuitBreaker;
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
public class OrderDetails {

    private OrderRepository orderRepository;
    private LineItemService lineItemService;
    private AddressCircuitBreaker addressCircuitBreaker;
    private ProductCircuitBreaker productCircuitBreaker;
    private ShipmentCircuitBreaker shipmentCircuitBreaker;

    public OrderDetails(OrderRepository orderRepository, LineItemService lineItemService, AddressCircuitBreaker addressCircuitBreaker, ProductCircuitBreaker productCircuitBreaker, ShipmentCircuitBreaker shipmentCircuitBreaker) {
        this.orderRepository = orderRepository;
        this.lineItemService = lineItemService;
        this.addressCircuitBreaker = addressCircuitBreaker;
        this.productCircuitBreaker = productCircuitBreaker;
        this.shipmentCircuitBreaker = shipmentCircuitBreaker;
    }

    public OrderToDisplay getOrderDetailsById(Long orderId) {

        Optional<Order> foundOrderList = orderRepository.findById(orderId);
        Order foundOrder = foundOrderList.get();
        Long shippingAddressId = foundOrder.getShippingAddressId();

        OrderToDisplay orderToDisplay = new OrderToDisplay();
        orderToDisplay.setOrderNumber(foundOrder.getId());

        OrderAddressToDisplay addressToDisplay = addressCircuitBreaker.makeRestCallToGetOrderAddressToDisplay(foundOrder, shippingAddressId);

        addressToDisplay.setShippingAddressId(shippingAddressId);
        orderToDisplay.setShippingAddress(addressToDisplay);

        List<LineItem> lineItemsForOrderId = lineItemService.findByOrderId(foundOrder.getId());

        List<OrderLineItemToDisplay> lineItemsForOrderList = new ArrayList<>();
        List<OrderShipmentsToDisplay> shipmentItemsForOrderList = new ArrayList<>();

        for (LineItem lineItem : lineItemsForOrderId) {
            OrderLineItemToDisplay orderLineItemToDisplay = new OrderLineItemToDisplay();
            OrderShipmentsToDisplay shipmentLineItemToDisplay = new OrderShipmentsToDisplay();

            TempProductObject tempProduct = productCircuitBreaker.getTempProductObject(lineItem);
            TempShipmentObject tempShipment = shipmentCircuitBreaker.getShipmentInformation(lineItem);

            shipmentLineItemToDisplay.setDeliveryDate(tempShipment.getDeliveredDate());
            shipmentLineItemToDisplay.setShippedDate(tempShipment.getShippedDate());
            shipmentLineItemToDisplay.setShipmentId(tempShipment.getId());
            shipmentLineItemToDisplay.setOrderLineItemId(lineItem.getId());

            orderLineItemToDisplay.setOrderLineItemId(lineItem.getId());
            orderLineItemToDisplay.setProductName(tempProduct.getName());
            orderLineItemToDisplay.setQuantity(lineItem.getQuantity());

            lineItemsForOrderList.add(orderLineItemToDisplay);
            shipmentItemsForOrderList.add(shipmentLineItemToDisplay);
        }

        orderToDisplay.setOrderTotalPrice(foundOrder.getTotalPrice());
        orderToDisplay.setOrderLineItemsList(lineItemsForOrderList);
        orderToDisplay.setOrderShipmentsList(shipmentItemsForOrderList);

        return orderToDisplay;
    }
}
