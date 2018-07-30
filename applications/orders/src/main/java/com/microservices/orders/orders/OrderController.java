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
        OrderToDisplay orderToDisplay = new OrderToDisplay();

        Optional<Order> foundOrderList = orderRepository.findById(orderIdToSearch);
        Order foundOrder = foundOrderList.get();
        Long shippingAddressId = foundOrder.getShippingAddressId();

        orderToDisplay.setOrderNumber(foundOrder.getId());
        orderToDisplay.setOrderTotalPrice(foundOrder.getTotalPrice());

        OrderAddressToDisplay orderShippingAddress = restTemplate.getForObject(microServiceInstances.getOrderShippingAddress(foundOrder.getAccountId(), shippingAddressId), OrderAddressToDisplay.class);
        orderShippingAddress.setShippingAddressId(shippingAddressId);
        orderToDisplay.setShippingAddress(orderShippingAddress);


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


//        List<LineItem> foundLineItemList = foundOrder.getLineItemList();
//
//        for (LineItem lineItem : foundLineItemList) {
//            OrderProductsToDisplay productModels = restTemplate.getForObject(microServiceInstances.getProductName(lineItem.getProductId()), OrderProductsToDisplay.class);
//            productModels.setQuantity(lineItem.getQuantity());
//            listOfProductsForOrder.add(productModels);
//        }

//        orderToDisplay.setOrderLineItems(listOfProductsForOrder);




//        List<LineItem> lineItemList = foundOrder.getLineItemList();


//        for (LineItem lineItem : lineItemList) {
//            Long productId = lineItem.getProductId();
//            Integer quantity = lineItem.getQuantity();
//
//            OrderProductsToDisplay foundProductModel = restTemplate.getForObject(microServiceInstances.getProductName(productId), OrderProductsToDisplay.class);
//
//            String productName = foundProductModel.getName();
//
//            OrderLineItemsToDisplay lineItemsToDisplay = new OrderLineItemsToDisplay();
//
//            lineItemsToDisplay.setQuantity(quantity);
//            lineItemsToDisplay.setProductName(productName);
//        }
//
//        OrderShipmentToDisplay[] shipmentsForOrder = restTemplate.getForObject(microServiceInstances.getAllShipmentsForOrderId(foundOrder.getShippingAddressId()), OrderShipmentToDisplay[].class);
//
//        List<OrderShipmentToDisplay> shipmentModels = new ArrayList<>();
//        Collections.addAll(shipmentModels, shipmentsForOrder);



//        OrderAddressToDisplay orderShipment = restTemplate.getForObject(microServiceInstances.getAllShipmentsForOrderId(orderIdToSearch), OrderAddressToDisplay.class);



//        return orderToDisplay;

//        return orderShippingAddress;

        return orderToDisplay;
    }
}
