package com.microservices.orders.services.Order;

import com.microservices.orders.circuits.AddressCircuitBreaker;
import com.microservices.orders.circuits.ProductCircuitBreaker;
import com.microservices.orders.circuits.ShipmentCircuitBreaker;
import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.Order;
import com.microservices.orders.models.display.DisplayOrderAddress;
import com.microservices.orders.models.display.DisplayOrderDetails;
import com.microservices.orders.models.display.DisplayOrderLineItem;
import com.microservices.orders.models.display.DisplayOrderShipments;
import com.microservices.orders.models.temp.TempProduct;
import com.microservices.orders.models.temp.TempShipment;
import com.microservices.orders.services.LineItem.LineItemService;
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

    //Todo:  look at breaking up

    public DisplayOrderDetails findDetailsByOrderId(Long orderId) {

        Optional<Order> foundOrderList = orderService.findById(orderId);
        Order foundOrder = foundOrderList.get();
        Long shippingAddressId = foundOrder.getShippingAddressId();

        DisplayOrderDetails displayOrderDetails = new DisplayOrderDetails();
        displayOrderDetails.setOrderNumber(foundOrder.getId());

        DisplayOrderAddress addressToDisplay = addressCircuitBreaker.makeRestCallToGetOrderAddressToDisplay(foundOrder, shippingAddressId);

        addressToDisplay.setShippingAddressId(shippingAddressId);
        displayOrderDetails.setShippingAddress(addressToDisplay);

        List<LineItem> lineItemsForOrderId = lineItemService.findByOrderId(foundOrder.getId());

        List<DisplayOrderLineItem> lineItemsForOrderList = new ArrayList<>();
        List<DisplayOrderShipments> shipmentItemsForOrderList = new ArrayList<>();

        for (LineItem lineItem : lineItemsForOrderId) {
            DisplayOrderLineItem displayOrderLineItem = new DisplayOrderLineItem();
            DisplayOrderShipments shipmentLineItemToDisplay = new DisplayOrderShipments();

            TempProduct tempProduct = productCircuitBreaker.getTempProductWithId(lineItem.getProductId());

            TempShipment tempShipment = shipmentCircuitBreaker.getShipmentInformation(lineItem);

            shipmentLineItemToDisplay.setDeliveryDate(tempShipment.getDeliveredDate());
            shipmentLineItemToDisplay.setShippedDate(tempShipment.getShippedDate());
            shipmentLineItemToDisplay.setShipmentId(tempShipment.getId());



            shipmentLineItemToDisplay.setOrderId(lineItem.getOrderId());





            displayOrderLineItem.setOrderLineItemId(lineItem.getId());
            displayOrderLineItem.setProductName(tempProduct.getName());
            displayOrderLineItem.setQuantity(lineItem.getQuantity());

            lineItemsForOrderList.add(displayOrderLineItem);
            shipmentItemsForOrderList.add(shipmentLineItemToDisplay);
        }

        displayOrderDetails.setOrderTotalPrice(foundOrder.getTotalPrice());
        displayOrderDetails.setDisplayOrderLineItemsList(lineItemsForOrderList);
        displayOrderDetails.setDisplayOrderShipmentsList(shipmentItemsForOrderList);

        return displayOrderDetails;
    }
}
