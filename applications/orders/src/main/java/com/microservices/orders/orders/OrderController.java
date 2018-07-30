package com.microservices.orders.orders;

import com.microservices.orders.MicroServiceInstances;
import com.microservices.orders.OrderToDisplay;
import com.microservices.orders.tempModels.OrderAddressModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = {"/orders"})
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MicroServiceInstances microServiceInstances;

    @GetMapping
    public List<Order> getAllOrdersForAccountId(
            @RequestParam(value = "accountId") Long accountId
    ) {
        return orderRepository.findAllByAccountIdOrderByOrderDate(accountId);
    }

    @GetMapping(value = "/orderDetails/{id}")
    public OrderAddressModel getOrderDetailsForId(
            @PathVariable(value = "id") Long orderIdToSearch
    ) {

        OrderToDisplay orderToDisplay = new OrderToDisplay();

        Optional<Order> foundOrderList = orderRepository.findById(orderIdToSearch);
        Order foundOrder = foundOrderList.get();
        orderToDisplay.setOrderNumber(foundOrder.getId());
        orderToDisplay.setOrderTotalPrice(foundOrder.getTotalPrice());
        Long shippingAddressId = foundOrder.getShippingAddressId();

        OrderAddressModel orderShippingAddress = restTemplate.getForObject(microServiceInstances.getOrderShippingAddress(foundOrder.getAccountId(), shippingAddressId), OrderAddressModel.class);

        orderShippingAddress.setShippingAddressId(shippingAddressId);
//        orderToDisplay.setShippingAddress(orderShippingAddress);
//
//        Long shippingAddressId = foundOrder.getShippingAddressId();
//        List<LineItem> lineItemList = foundOrder.getLineItemList();
//
//        for (LineItem lineItem : lineItemList) {
//            Long productId = lineItem.getProductId();
//            Integer quantity = lineItem.getQuantity();
//
//        }
//
//        OrderShipmentModel[] shipmentsForOrder = restTemplate.getForObject(microServiceInstances.getAllShipmentsForOrderId(foundOrder.getShippingAddressId()), OrderShipmentModel[].class);
//
//        List<OrderShipmentModel> shipmentModels = new ArrayList<>();
//        Collections.addAll(shipmentModels, shipmentsForOrder);



//        OrderAddressModel orderShipment = restTemplate.getForObject(microServiceInstances.getAllShipmentsForOrderId(orderIdToSearch), OrderAddressModel.class);



//        return orderToDisplay;

        return orderShippingAddress;
    }
}
