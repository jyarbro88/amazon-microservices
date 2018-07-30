package com.microservices.orders.orders;

import com.microservices.orders.MicroServiceInstances;
import com.microservices.orders.lineItems.LineItemRepository;
import com.microservices.orders.displayObjects.OrderLineItemToDisplay;
import com.microservices.orders.displayObjects.OrderProductsToDisplay;
import com.microservices.orders.displayObjects.OrderToDisplay;
import com.microservices.orders.lineItems.LineItem;
import com.microservices.orders.displayObjects.OrderAddressToDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private LineItemRepository lineItemRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MicroServiceInstances microServiceInstances;

    @GetMapping(value = "orders/accountLookup")
    public List<Order> getAllOrdersForAccountId(
            @RequestParam(value = "accountId") Long accountId
    ) {
        return orderRepository.findAllByAccountIdOrderByOrderDate(accountId);
    }

    @GetMapping(value = "/details/{id}")
    public OrderToDisplay getOrderDetailsForId(
            @PathVariable(value = "id") Long orderIdToSearch
    ) {
        Optional<Order> foundOrderList = orderRepository.findById(orderIdToSearch);
        Order foundOrder = foundOrderList.get();

        OrderToDisplay orderToDisplay = getOrderToDisplay(foundOrder);

        getLineItemsForOrder(foundOrder, orderToDisplay);

//        return orderShippingAddress;

        return orderToDisplay;
    }

    private void getLineItemsForOrder(Order foundOrder, OrderToDisplay orderToDisplay) {
        List<LineItem> lineItemsForOrderId = lineItemRepository.findAllByOrderId(foundOrder.getId());

        List<OrderLineItemToDisplay> listOfProductsForOrder = new ArrayList<>();

        for (LineItem lineItem : lineItemsForOrderId) {
            OrderLineItemToDisplay lineItemToDisplay = new OrderLineItemToDisplay();
            OrderProductsToDisplay foundProduct = restTemplate.getForObject(microServiceInstances.getProductName(lineItem.getProductId()), OrderProductsToDisplay.class);
            lineItemToDisplay.setProductName(foundProduct.getName());
            lineItemToDisplay.setQuantity(lineItem.getQuantity());
            listOfProductsForOrder.add(lineItemToDisplay);
        }

        orderToDisplay.setLineItemsToDisplay(listOfProductsForOrder);
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
}
