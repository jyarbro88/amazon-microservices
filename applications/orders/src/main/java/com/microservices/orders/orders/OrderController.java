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

        OrderToDisplay orderToDisplay = getOrderToDisplay(foundOrder);

        getLineItemsForOrder(foundOrder, orderToDisplay);

        List<OrderShipmentsToDisplay> orderShipmentsToDisplayList = orderToDisplay.getOrderShipmentsToDisplayList();

        return orderToDisplay;
    }

    private void getLineItemsForOrder(Order foundOrder, OrderToDisplay orderToDisplay) {
        List<LineItem> lineItemsForOrderId = lineItemRepository.findAllByOrderId(foundOrder.getId());

        List<OrderLineItemToDisplay> listOfProductsForOrder = new ArrayList<>();
        List<OrderShipmentsToDisplay> listOfShipmentsForOrder = new ArrayList<>();
        List<OrderShipmentsToDisplay> orderShipmentsToDisplayList = new ArrayList<>();

        for (LineItem lineItem : lineItemsForOrderId) {

            OrderLineItemToDisplay lineItemToDisplay = new OrderLineItemToDisplay();
            OrderShipmentsToDisplay lineItemShipment = new OrderShipmentsToDisplay();

            TempProductObject foundProduct = restTemplate.getForObject(microServiceInstances.getProductName(lineItem.getProductId()), TempProductObject.class);
            TempShipmentObject tempShipmentObject = restTemplate.getForObject(microServiceInstances.getShipmentForLineItemId(lineItem.getShipmentId()), TempShipmentObject.class);

            lineItemShipment.setDeliveryDate(tempShipmentObject.getDeliveredDate());
            lineItemShipment.setShippedDate(tempShipmentObject.getShippedDate());
            lineItemShipment.setShipmentId(tempShipmentObject.getId());


            lineItemToDisplay.setProductName(foundProduct.getName());
            lineItemToDisplay.setQuantity(lineItem.getQuantity());

            orderShipmentsToDisplayList.add(lineItemShipment);
            listOfProductsForOrder.add(lineItemToDisplay);

            listOfShipmentsForOrder.add(lineItemShipment);


        }

        orderToDisplay.setLineItemsToDisplay(listOfProductsForOrder);
        orderToDisplay.setOrderShipmentsToDisplayList(listOfShipmentsForOrder);
    }

    private OrderToDisplay getOrderToDisplay(Order foundOrder) {
        OrderToDisplay orderToDisplay = new OrderToDisplay();
        Long shippingAddressId = foundOrder.getShippingAddressId();

        orderToDisplay.setOrderNumber(foundOrder.getId());
        orderToDisplay.setOrderTotalPrice(foundOrder.getTotalPrice());

        OrderAddressToDisplay orderShippingAddress = restTemplate.getForObject(microServiceInstances.getOrderShippingAddress(foundOrder.getAccountId(), shippingAddressId), OrderAddressToDisplay.class);
        orderShippingAddress.setShippingAddressId(shippingAddressId);
        orderToDisplay.setShippingAddress(orderShippingAddress);
        return orderToDisplay;
    }

    //Todo:  Create operations for new Order which links the address, account id when saving
}
