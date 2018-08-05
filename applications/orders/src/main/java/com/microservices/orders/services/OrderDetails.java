package com.microservices.orders.services;

import com.microservices.orders.breakers.AddressCircuitBreaker;
import com.microservices.orders.breakers.ProductCircuitBreaker;
import com.microservices.orders.breakers.ShipmentCircuitBreaker;
import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.Order;
import com.microservices.orders.models.display.OrderAddress;
import com.microservices.orders.models.display.OrderLineItem;
import com.microservices.orders.models.display.OrderShipments;
import com.microservices.orders.models.temp.TempProduct;
import com.microservices.orders.models.temp.TempShipment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetails {

    private OrderService orderService;
    private LineItemService lineItemService;
    private AddressCircuitBreaker addressCircuitBreaker;
    private ProductCircuitBreaker productCircuitBreaker;
    private ShipmentCircuitBreaker shipmentCircuitBreaker;

    public OrderDetails(OrderService orderService, LineItemService lineItemService, AddressCircuitBreaker addressCircuitBreaker, ProductCircuitBreaker productCircuitBreaker, ShipmentCircuitBreaker shipmentCircuitBreaker) {
        this.orderService = orderService;
        this.lineItemService = lineItemService;
        this.addressCircuitBreaker = addressCircuitBreaker;
        this.productCircuitBreaker = productCircuitBreaker;
        this.shipmentCircuitBreaker = shipmentCircuitBreaker;
    }

    public com.microservices.orders.models.display.OrderDetails findDetailsByOrderId(Long orderId) {

        Optional<Order> foundOrderList = orderService.findById(orderId);
        Order foundOrder = foundOrderList.get();
        Long shippingAddressId = foundOrder.getShippingAddressId();

        com.microservices.orders.models.display.OrderDetails orderDetails = new com.microservices.orders.models.display.OrderDetails();
        orderDetails.setOrderNumber(foundOrder.getId());

        OrderAddress addressToDisplay = addressCircuitBreaker.makeRestCallToGetOrderAddressToDisplay(foundOrder, shippingAddressId);

        addressToDisplay.setShippingAddressId(shippingAddressId);
        orderDetails.setShippingAddress(addressToDisplay);

        List<LineItem> lineItemsForOrderId = lineItemService.findByOrderId(foundOrder.getId());

        List<OrderLineItem> lineItemsForOrderList = new ArrayList<>();
        List<OrderShipments> shipmentItemsForOrderList = new ArrayList<>();

        for (LineItem lineItem : lineItemsForOrderId) {
            OrderLineItem orderLineItem = new OrderLineItem();
            OrderShipments shipmentLineItemToDisplay = new OrderShipments();

            TempProduct tempProduct = productCircuitBreaker.getTempProductObject(lineItem);
            TempShipment tempShipment = shipmentCircuitBreaker.getShipmentInformation(lineItem);

            shipmentLineItemToDisplay.setDeliveryDate(tempShipment.getDeliveredDate());
            shipmentLineItemToDisplay.setShippedDate(tempShipment.getShippedDate());
            shipmentLineItemToDisplay.setShipmentId(tempShipment.getId());



            shipmentLineItemToDisplay.setOrderId(lineItem.getOrderId());





            orderLineItem.setOrderLineItemId(lineItem.getId());
            orderLineItem.setProductName(tempProduct.getName());
            orderLineItem.setQuantity(lineItem.getQuantity());

            lineItemsForOrderList.add(orderLineItem);
            shipmentItemsForOrderList.add(shipmentLineItemToDisplay);
        }

        orderDetails.setOrderTotalPrice(foundOrder.getTotalPrice());
        orderDetails.setOrderLineItemsList(lineItemsForOrderList);
        orderDetails.setOrderShipmentsList(shipmentItemsForOrderList);

        return orderDetails;
    }
}
