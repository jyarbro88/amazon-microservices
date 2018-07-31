package com.microservices.orders.orders;

import com.microservices.orders.MicroServiceInstances;
import com.microservices.orders.displayObjects.OrderShipmentsToDisplay;
import com.microservices.orders.lineItems.LineItemRepository;
import com.microservices.orders.displayObjects.OrderLineItemToDisplay;
import com.microservices.orders.tempObjects.TempProductObject;
import com.microservices.orders.displayObjects.OrderToDisplay;
import com.microservices.orders.lineItems.LineItem;
import com.microservices.orders.displayObjects.OrderAddressToDisplay;
import com.microservices.orders.tempObjects.TempShipmentObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Todo:  Create operations for new Order which links the address, account id when saving
//Todo:  Calculate Total Prices with utility class and not manually
//Todo:  Order Total Price is null
//Todo:  OrderShipmentToDisplay needs order line item id and order id
@RestController
public class OrderController {

    private final OrderRepository orderRepository;
    private final LineItemRepository lineItemRepository;
    private final RestTemplate restTemplate;
    private final MicroServiceInstances microServiceInstances;

    public OrderController(OrderRepository orderRepository, LineItemRepository lineItemRepository, RestTemplate restTemplate, MicroServiceInstances microServiceInstances) {
        this.orderRepository = orderRepository;
        this.lineItemRepository = lineItemRepository;
        this.restTemplate = restTemplate;
        this.microServiceInstances = microServiceInstances;
    }

    @GetMapping(value = "orders/accountLookup")
    public List<Order> getAllOrdersForAccountId(
            @RequestParam(value = "accountId") Long accountId
    ) {
        return orderRepository.findAllByAccountIdOrderByOrderDate(accountId);
    }

    @GetMapping(value = "orders/details/{id}")
    public OrderToDisplay getOrderDetailsForId(
            @PathVariable(value = "id") Long orderIdToSearch
    ) {
        Optional<Order> foundOrderList = orderRepository.findById(orderIdToSearch);
        Order foundOrder = foundOrderList.get();
        Long shippingAddressId = foundOrder.getShippingAddressId();

        OrderToDisplay orderToDisplay = new OrderToDisplay();
        orderToDisplay.setOrderNumber(foundOrder.getId());
        orderToDisplay.setOrderTotalPrice(foundOrder.getTotalPrice());

        OrderAddressToDisplay orderShippingAddress = restTemplate.getForObject(microServiceInstances.getOrderShippingAddress(foundOrder.getAccountId(), shippingAddressId), OrderAddressToDisplay.class);

        orderShippingAddress.setShippingAddressId(shippingAddressId);
        orderToDisplay.setShippingAddress(orderShippingAddress);

        List<LineItem> lineItemsForOrderId = lineItemRepository.findAllByOrderId(foundOrder.getId());




        List<OrderLineItemToDisplay> lineItemsForOrderList = new ArrayList<>();
        List<OrderShipmentsToDisplay> shipmentItemsForOrderList = new ArrayList<>();

        for (LineItem lineItem : lineItemsForOrderId) {
            OrderLineItemToDisplay orderLineItemToDisplay = new OrderLineItemToDisplay();
            OrderShipmentsToDisplay shipmentLineItemToDisplay = new OrderShipmentsToDisplay();

            TempProductObject tempProduct = restTemplate.getForObject(microServiceInstances.getProductName(lineItem.getProductId()), TempProductObject.class);
            TempShipmentObject tempShipment = restTemplate.getForObject(microServiceInstances.getShipmentForLineItemId(lineItem.getShipmentId()), TempShipmentObject.class);

            shipmentLineItemToDisplay.setDeliveryDate(tempShipment.getDeliveredDate());
            shipmentLineItemToDisplay.setShippedDate(tempShipment.getShippedDate());
            shipmentLineItemToDisplay.setShipmentId(tempShipment.getId());
            shipmentLineItemToDisplay.setOrderLineItemId(lineItem.getId());


            orderLineItemToDisplay.setOrderLineItemId(lineItem.getId());
            orderLineItemToDisplay.setProductName(tempProduct.getName());
            orderLineItemToDisplay.setQuantity(lineItem.getQuantity());

            lineItem.getOrderId();

            lineItemsForOrderList.add(orderLineItemToDisplay);
            shipmentItemsForOrderList.add(shipmentLineItemToDisplay);
        }





        orderToDisplay.setOrderLineItemsList(lineItemsForOrderList);
        orderToDisplay.setOrderShipmentsList(shipmentItemsForOrderList);

        return orderToDisplay;
    }
}
