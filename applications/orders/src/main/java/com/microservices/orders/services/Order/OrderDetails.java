package com.microservices.orders.services.Order;

import com.microservices.orders.circuits.AddressCircuitBreaker;
import com.microservices.orders.circuits.ProductCircuitBreaker;
import com.microservices.orders.circuits.ShipmentCircuitBreaker;
import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.Order;
import com.microservices.orders.models.display.DisplayOrderAddress;
import com.microservices.orders.models.display.DisplayOrderDetails;
import com.microservices.orders.models.display.DisplayOrderLineItem;
import com.microservices.orders.models.display.DisplayOrderShipment;
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

    public DisplayOrderDetails findDetailsByOrderId(Long orderId) {

        List<DisplayOrderShipment> shipmentItemsForOrder = new ArrayList<>();
        List<DisplayOrderLineItem> lineItemsForOrderList = new ArrayList<>();

        Optional<Order> foundOrderList = orderService.findById(orderId);
        Order foundOrder = foundOrderList.get();
        Long shippingAddressId = foundOrder.getShippingAddressId();

        DisplayOrderDetails displayOrderDetails = new DisplayOrderDetails();
        displayOrderDetails.setOrderNumber(foundOrder.getId());

        DisplayOrderAddress addressToDisplay = addressCircuitBreaker.getOrderAddress(foundOrder.getAccountId(), shippingAddressId);

        addressToDisplay.setShippingAddressId(shippingAddressId);
        displayOrderDetails.setShippingAddress(addressToDisplay);


        List<LineItem> lineItemsForOrderId = lineItemService.findByOrderId(foundOrder.getId());

        for (LineItem lineItem : lineItemsForOrderId) {
            DisplayOrderShipment displayOrderShipment = buildShipmentToDisplay(lineItem);
            DisplayOrderLineItem displayOrderLineItem = buildLineItemToDisplay(lineItem);

            lineItemsForOrderList.add(displayOrderLineItem);
            shipmentItemsForOrder.add(displayOrderShipment);
        }

        displayOrderDetails.setOrderTotalPrice(foundOrder.getTotalPrice());
        displayOrderDetails.setDisplayOrderLineItemsList(lineItemsForOrderList);

        displayOrderDetails.setDisplayOrderShipmentsList(shipmentItemsForOrder);

        return displayOrderDetails;
    }

    private DisplayOrderLineItem buildLineItemToDisplay(LineItem lineItem) {

        DisplayOrderLineItem displayOrderLineItem = new DisplayOrderLineItem();

        displayOrderLineItem.setOrderLineItemId(lineItem.getId());

        TempProduct tempProduct = productCircuitBreaker.getTempProductWithId(lineItem.getProductId());
        displayOrderLineItem.setProductName(tempProduct.getName());
        displayOrderLineItem.setQuantity(lineItem.getQuantity());

        return displayOrderLineItem;
    }

    private DisplayOrderShipment buildShipmentToDisplay(LineItem lineItem) {

        TempShipment tempShipment = shipmentCircuitBreaker.getShipmentInformation(lineItem);

        DisplayOrderShipment shipmentLineItemToDisplay = new DisplayOrderShipment();

        shipmentLineItemToDisplay.setDeliveryDate(tempShipment.getDeliveredDate());
        shipmentLineItemToDisplay.setShippedDate(tempShipment.getShippedDate());
        shipmentLineItemToDisplay.setShipmentId(tempShipment.getId());
        shipmentLineItemToDisplay.setOrderId(lineItem.getOrderId());

        return shipmentLineItemToDisplay;
    }
}
